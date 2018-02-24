package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import data.Employer;
import data.Student;
import database.MySQLDriver;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		MySQLDriver sqldriver = (MySQLDriver)session.getAttribute("sqldriver");
		String userType = (String)session.getAttribute("user_type");
		String username = (String)session.getAttribute("logged_in_user");
		
		Vector<Employer> employers = sqldriver.getAllEmployers();
		Vector<Student> students = sqldriver.getAllStudents();
		
		System.out.println("Number of Employers: " + employers.size());
		System.out.println("Number of Students: " + students.size());

		
		Student student = null;
		Employer employer = null;
		
		if(userType.equals("Student")) {
			student = sqldriver.getStudent(username);
		}
		else {
			employer = sqldriver.getEmployer(username);
		}
		
		
		String action = request.getParameter("action");
		System.out.println("action: " + action);
		if(action.equals("initial")) {
			System.out.println("In initial action");
			if(userType.equals("Student")) {
				
				String sponsorship = request.getParameter("sponsorship").trim();
				String industry = request.getParameter("industry").trim();
				String position = request.getParameter("position").trim();
				
				
				System.out.println("sponsorship: " + sponsorship);
				System.out.println("industry: " + industry);
				System.out.println("position: " + position);


				
				List<Employer> candidates = new ArrayList<Employer>();
				List<Employer> candidates1 = new ArrayList<Employer>();
				List<Employer> candidates2 = new ArrayList<Employer>();
				

				for(Employer e : employers) { // first pass, filter by sponsorship
					if(sponsorship.equals("true")) {
						if(e.getSponsorship()) {
							candidates.add(e);
							System.out.println("candidates: " + e.getCompany());
						}
					}
					else if(sponsorship.equals("false")) {
						if(!e.getSponsorship()) {
							candidates.add(e);
							System.out.println("candidates false: " + e.getCompany());
						}
					}
				}
				
				for(Employer e : candidates) { // second pass, filter by position
					if(!position.equals("none")) {
						if(e.getPosition().equals(position)) {
							candidates1.add(e);
							System.out.println("candidates1: " + e.getCompany());

						}
					}
					else {
						candidates1.add(e);
						System.out.println("candidates1 none: " + e.getCompany());

					}
				}
				
				for(Employer e : candidates1) { // third pass, filter by industry
					if(!industry.equals("none")) {
						System.out.println("getIndustry: " + e.getIndustry());
						if(e.getIndustry().equals(industry)) {
							candidates2.add(e);
							System.out.println("candidates2: " + e.getCompany());

						}
					}
					else {
						candidates2.add(e);
						System.out.println("candidates2 none: " + e.getCompany());

					}
				}
				
				System.out.println("Number of total candidates: " + candidates2.size());
				
				System.out.println("Initial Action for student");
				// we want to only show the employers that our logged_in_user has not liked or disliked
				boolean result = false;
				for(Employer e : candidates2) {
					if(!sqldriver.getStudentDislikedList(username).contains(e.getUsername()) &&
							!sqldriver.getStudentLikedList(username).contains(e.getUsername())) {
						// if the student has neither liked nor disliked this employer, send back a response
						System.out.println("Found potential employee");
						String message;
						JSONObject json = new JSONObject();
						result = true;
						try {
							json.put("username", e.getUsername());
							json.put("image", e.getImage());
							json.put("email", e.getEmail());
							json.put("city", e.getCity());
							json.put("company", e.getCompany());
							json.put("message", "success : retrieved employer for initial load");
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
						System.out.println("Sending JSON response of employee");
						message = json.toString();
						response.setContentType("application/json");
						PrintWriter out = response.getWriter();
						out.print(message);
						out.flush();
						break;
					}
				}
				if(result == false) {
					System.out.println("Did not find any potential employees. Sending empty JSON response");
					// you did not find any potential matches, send back a response
					String message;
					JSONObject jsonError = new JSONObject();
					try {
						jsonError.put("message", "error : no more potential matches");
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					message = jsonError.toString();
					response.setContentType("application/json");
					PrintWriter out = response.getWriter();
					out.print(message);
					out.flush();
				}
			} //for employee now
			else {
				
				String sponsorship = request.getParameter("sponsorship");
				String major = request.getParameter("major");
				String gradYear = request.getParameter("gradYear");
				
				List<Student> candidates = new ArrayList<Student>();
				List<Student> candidates1 = new ArrayList<Student>();
				List<Student> candidates2 = new ArrayList<Student>();

				

				for(Student s : students) { // first pass, filter by sponsorship
					if(sponsorship.equals("true")) {
						if(s.getSponsorship()) candidates.add(s);
					}
					else if(sponsorship.equals("false")) {
						if(!s.getSponsorship()) candidates.add(s);
					}
				}
				for(Student s : candidates) { // second pass, filter by major
					if(!major.equals("none")) {
						if(s.getMajor().equals(major)) {
							candidates1.add(s);
						}
					}
					else {
						candidates1.add(s);
					}
				}
				
				for(Student s : candidates1) { // third pass, filter by industry
					if(!gradYear.equals("none")) {
						if(s.getGradYear().equals(gradYear)) {
							candidates2.add(s);
						}
					}
					else {
						candidates2.add(s);
					}
				}
				
				// we want to only show the students that our logged in user has not liked or disliked
				boolean result = false;
				for(Student s : candidates2) {
					if(!sqldriver.getEmployerDislikedList(username).contains(s.getUsername()) 
							&& !sqldriver.getEmployerLikedList(username).contains((s.getUsername()))) {
						// send back a response of this student
						result = true;
						String message;
						JSONObject json = new JSONObject();
						try {
							json.put("username", s.getUsername());
							json.put("image", s.getImage());
							json.put("phone", s.getPhone());
							json.put("email", s.getEmail());
							json.put("gradYear", s.getGradYear());
							json.put("school", s.getSchool());
							json.put("gpa", s.getGPA());
							json.put("resume", s.getResume());
							json.put("message", "success : retrieved student for initial load");
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
						message = json.toString();
						response.setContentType("application/json");
						PrintWriter out = response.getWriter();
						out.print(message);
						out.flush();
						break;
					}
				}
				if(result == false) {
					// you did not find any potential matches, send back a response
					String message;
					JSONObject jsonError = new JSONObject();
					try {
						jsonError.put("message", "error : no more potential matches");
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					message = jsonError.toString();
					response.setContentType("application/json");
					PrintWriter out = response.getWriter();
					out.print(message);
					out.flush();
				}
			} //end of else
				
		} //end of action = initial
		else if(action.equals("liked")) {
			boolean match = false;
			// add this user to the currentUser's liked Vector
			if(userType.equals("Student")) {
				String sponsorship = request.getParameter("sponsorship");
				String industry = request.getParameter("industry");
				String position = request.getParameter("position");
				
				List<Employer> candidates = new ArrayList<Employer>();
				List<Employer> candidates1 = new ArrayList<Employer>();
				List<Employer> candidates2 = new ArrayList<Employer>();
				

				for(Employer e : employers) { // first pass, filter by sponsorship
					if(sponsorship.equals("true")) {
						if(e.getSponsorship()) {
							candidates.add(e);
							System.out.println("candidates: " + e.getCompany());
						}
					}
					else if(sponsorship.equals("false")) {
						if(!e.getSponsorship()) {
							candidates.add(e);
							System.out.println("candidates false: " + e.getCompany());
						}
					}
				}
				
				for(Employer e : candidates) { // second pass, filter by position
					if(!position.equals("none")) {
						if(e.getPosition().equals(position)) {
							candidates1.add(e);
							System.out.println("candidates1: " + e.getCompany());

						}
					}
					else {
						candidates1.add(e);
						System.out.println("candidates1 none: " + e.getCompany());

					}
				}
				
				for(Employer e : candidates1) { // third pass, filter by industry
					if(!industry.equals("none")) {
						System.out.println("getIndustry: " + e.getIndustry());
						if(e.getIndustry().equals(industry)) {
							candidates2.add(e);
							System.out.println("candidates2: " + e.getCompany());

						}
					}
					else {
						candidates2.add(e);
						System.out.println("candidates2 none: " + e.getCompany());

					}
				}
				
				System.out.println("Number of total candidates: " + candidates2.size());
				
				boolean result = false;
				String likedUser = request.getParameter("username");
				for(Employer e : employers) {
					if(e.getUsername().equals(likedUser)) {
						sqldriver.addStudentLiked(e.getUsername(), username);
						if(sqldriver.getStudentLikedList(username).contains(e.getUsername()) 
						&& sqldriver.getEmployerLikedList(e.getUsername()).contains(username)) {
							sqldriver.addMatch(student.getUsername(), e.getUsername());
							System.out.println(student.getUsername() + " adding match " + e.getUsername());
							match = true;
						}
						break;
					}
				}
				// now toggle through and find the next potential Employer
				// we want to only show the employers that our logged_in_user has not liked or disliked
				for(Employer e : candidates2) {
					if(!sqldriver.getStudentDislikedList(username).contains(e.getUsername()) &&
							!sqldriver.getStudentLikedList(username).contains(e.getUsername())) {
						result = true;
						// if the student has neither liked nor disliked this employer, send back a response
						String message;
						JSONObject json = new JSONObject();
						try {
							json.put("username", e.getUsername());
							json.put("image", e.getImage());
							json.put("email", e.getEmail());
							json.put("city", e.getCity());
							json.put("company", e.getCompany());
							json.put("message", "success : retrieved employer for initial load");
							json.put("match", match);
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
						message = json.toString();
						response.setContentType("application/json");
						PrintWriter out = response.getWriter();
						out.print(message);
						out.flush();
						break;
					}
				}
				if(result == false) {
					// you did not find any potential matches, send back a response
					String message;
					JSONObject jsonError = new JSONObject();
					try {
						jsonError.put("message", "error : no more potential matches");
						jsonError.put("match", match);
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					message = jsonError.toString();
					response.setContentType("application/json");
					PrintWriter out = response.getWriter();
					out.print(message);
					out.flush();
				}
				
			}
			else { // employer case
				String sponsorship = request.getParameter("sponsorship");
				String major = request.getParameter("major");
				String gradYear = request.getParameter("gradYear");
				
				
				List<Student> candidates = new ArrayList<Student>();
				List<Student> candidates1 = new ArrayList<Student>();
				List<Student> candidates2 = new ArrayList<Student>();

				

				for(Student s : students) { // first pass, filter by sponsorship
					if(sponsorship.equals("true")) {
						if(s.getSponsorship()) candidates.add(s);
					}
					else if(sponsorship.equals("false")) {
						if(!s.getSponsorship()) candidates.add(s);
					}
				}
				for(Student s : candidates) { // second pass, filter by major
					if(!major.equals("none")) {
						if(s.getMajor().equals(major)) {
							candidates1.add(s);
						}
					}
					else {
						candidates1.add(s);
					}
				}
				
				for(Student s : candidates1) { // third pass, filter by industry
					if(!gradYear.equals("none")) {
						if(s.getGradYear().equals(gradYear)) {
							candidates2.add(s);
						}
					}
					else {
						candidates2.add(s);
					}
				}
				boolean result = false;
				boolean m = false;
				String likedUser = request.getParameter("username");
				for(Student s : students) { // add the student to the liked list
					if(s.getUsername().equals(likedUser)) {
						sqldriver.addEmployerLiked(s.getUsername(), username);
						if(sqldriver.getEmployerLikedList(username).contains(s.getUsername()) 
								&& sqldriver.getStudentLikedList(s.getUsername()).contains(username)) {
									//String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
									sqldriver.addMatch(s.getUsername(), username);
									m = true;
								}
						break;
					}
				}
				// now toggle through and find the next potential student
				// we want to only show the employers that our logged in user has not liked or disliked
				for(Student s : candidates2) {
					if(!sqldriver.getEmployerDislikedList(username).contains(s.getUsername()) 
							&& !sqldriver.getEmployerLikedList(username).contains((s.getUsername()))) {
						// send back a response of this student
						result = true;
						String message;
						JSONObject json = new JSONObject();
						try {
							json.put("username", s.getUsername());
							json.put("image", s.getImage());
							json.put("phone", s.getPhone());
							json.put("email", s.getEmail());
							json.put("gradYear", s.getGradYear());
							json.put("school", s.getSchool());
							json.put("gpa", s.getGPA());
							json.put("resume", s.getResume());
							json.put("message", "success : retrieved student for initial load");
							json.put("match", m);
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
						message = json.toString();
						response.setContentType("application/json");
						PrintWriter out = response.getWriter();
						out.print(message);
						out.flush();
						break;
					}
				}
				if(result == false) {
					// you did not find any potential matches, send back a response
					String message;
					JSONObject jsonError = new JSONObject();
					try {
						jsonError.put("match", m);
						jsonError.put("message", "error : no more potential matches");
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					message = jsonError.toString();
					response.setContentType("application/json");
					PrintWriter out = response.getWriter();
					out.print(message);
					out.flush();
				}
			}
		}
		else if(action.equals("disliked")) {
			if(userType.equals("Student")) {
				String sponsorship = request.getParameter("sponsorship");
				String industry = request.getParameter("industry");
				String position = request.getParameter("position");
				
				List<Employer> candidates = new ArrayList<Employer>();
				List<Employer> candidates1 = new ArrayList<Employer>();
				List<Employer> candidates2 = new ArrayList<Employer>();
				

				for(Employer e : employers) { // first pass, filter by sponsorship
					if(sponsorship.equals("true")) {
						if(e.getSponsorship()) {
							candidates.add(e);
							System.out.println("candidates: " + e.getCompany());
						}
					}
					else if(sponsorship.equals("false")) {
						if(!e.getSponsorship()) {
							candidates.add(e);
							System.out.println("candidates false: " + e.getCompany());
						}
					}
				}
				
				for(Employer e : candidates) { // second pass, filter by position
					if(!position.equals("none")) {
						if(e.getPosition().equals(position)) {
							candidates1.add(e);
							System.out.println("candidates1: " + e.getCompany());

						}
					}
					else {
						candidates1.add(e);
						System.out.println("candidates1 none: " + e.getCompany());

					}
				}
				
				for(Employer e : candidates1) { // third pass, filter by industry
					if(!industry.equals("none")) {
						System.out.println("getIndustry: " + e.getIndustry());
						if(e.getIndustry().equals(industry)) {
							candidates2.add(e);
							System.out.println("candidates2: " + e.getCompany());

						}
					}
					else {
						candidates2.add(e);
						System.out.println("candidates2 none: " + e.getCompany());

					}
				}
				
				System.out.println("Number of total candidates: " + candidates2.size());
				
				
				
				boolean result = false;
				String dislikedUser = request.getParameter("username");
				for(Employer e : employers) {
					if(e.getUsername().equals(dislikedUser)) {
						sqldriver.addStudentDisliked(e.getUsername(), username);
						break;
					}
				}
				// now toggle through and find the next potential Employer
				// we want to only show the employers that our logged_in_user has not liked or disliked
				for(Employer e : candidates2) {
					if(!sqldriver.getStudentDislikedList(username).contains(e.getUsername()) &&
							!sqldriver.getStudentLikedList(username).contains(e.getUsername())) {
						result = true;
						// if the student has neither liked nor disliked this employer, send back a response
						String message;
						JSONObject json = new JSONObject();
						try {
							json.put("username", e.getUsername());
							json.put("image", e.getImage());
							json.put("email", e.getEmail());
							json.put("city", e.getCity());
							json.put("company", e.getCompany());
							json.put("message", "success : retrieved employer for initial load");
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
						message = json.toString();
						response.setContentType("application/json");
						PrintWriter out = response.getWriter();
						out.print(message);
						out.flush();
						break;
					}
				}
				if(result == false) {
					// you did not find any potential matches, send back a response
					String message;
					JSONObject jsonError = new JSONObject();
					try {
						jsonError.put("message", "error : no more potential matches");
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					message = jsonError.toString();
					response.setContentType("application/json");
					PrintWriter out = response.getWriter();
					out.print(message);
					out.flush();
				}
			}
			else {
				String sponsorship = request.getParameter("sponsorship");
				String major = request.getParameter("major");
				String gradYear = request.getParameter("gradYear");
				
				List<Student> candidates = new ArrayList<Student>();
				List<Student> candidates1 = new ArrayList<Student>();
				List<Student> candidates2 = new ArrayList<Student>();

				

				for(Student s : students) { // first pass, filter by sponsorship
					if(sponsorship.equals("true")) {
						if(s.getSponsorship()) candidates.add(s);
					}
					else if(sponsorship.equals("false")) {
						if(!s.getSponsorship()) candidates.add(s);
					}
				}
				for(Student s : candidates) { // second pass, filter by major
					if(!major.equals("none")) {
						if(s.getMajor().equals(major)) {
							candidates1.add(s);
						}
					}
					else {
						candidates1.add(s);
					}
				}
				
				for(Student s : candidates1) { // third pass, filter by industry
					if(!gradYear.equals("none")) {
						if(s.getGradYear().equals(gradYear)) {
							candidates2.add(s);
						}
					}
					else {
						candidates2.add(s);
					}
				}
				boolean r = false;
				String dislikedUser = request.getParameter("username");
				for(Student s : students) {
					if(s.getUsername().equals(dislikedUser)) {
						sqldriver.addEmployerDisliked(s.getUsername(), username);
						break;
					}
				}
				// now toggle through and find the next potential student
				// we want to only show the employers that our logged in user has not liked or disliked
				for(Student s : candidates2) {
					if(!sqldriver.getEmployerDislikedList(username).contains(s.getUsername()) 
							&& !sqldriver.getEmployerLikedList(username).contains((s.getUsername()))) {
						r = true;
						// send back a response of this student
						String message;
						JSONObject json = new JSONObject();
						try {
							json.put("username", s.getUsername());
							json.put("image", s.getImage());
							json.put("phone", s.getPhone());
							json.put("email", s.getEmail());
							json.put("gradYear", s.getGradYear());
							json.put("school", s.getSchool());
							json.put("gpa", s.getGPA());
							json.put("resume", s.getResume());
							json.put("message", "success : retrieved student for initial load");
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
						message = json.toString();
						response.setContentType("application/json");
						PrintWriter out = response.getWriter();
						out.print(message);
						out.flush();
						break;
					}
				}
				if(r = false) {
					// you did not find any potential matches, send back a response
					String message;
					JSONObject jsonError = new JSONObject();
					try {
						jsonError.put("message", "error : no more potential matches");
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					message = jsonError.toString();
					response.setContentType("application/json");
					PrintWriter out = response.getWriter();
					out.print(message);
					out.flush();
				}
			}
		}
	}
}