package servlets;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.StringConstants;
import data.Student;
import database.MySQLDriver;

/**
 * Servlet implementation class StudentSignupServlet
 */
@WebServlet("/StudentSignupServlet")
public class StudentSignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentSignupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		MySQLDriver sqldriver = (MySQLDriver)session.getAttribute("sqldriver");
		
		String name, username, password, school, major, gradYear, city, email, resume, gpa, phoneNum, image;
		String sponsorship;
		name = (String)request.getParameter("name");
		username = (String)request.getParameter("username");
		password = (String)request.getParameter("password");
		school = (String)request.getParameter("school");
		major = (String)request.getParameter("major");
		gradYear = (String)request.getParameter("gradYear");
		city = (String)request.getParameter("city");
		email = (String)request.getParameter("email");
		resume = (String)request.getParameter("resume");
		gpa = (String)request.getParameter("GPA");
		phoneNum = (String)request.getParameter("phone");
		image = (String)request.getParameter("image");
		sponsorship = (String)request.getParameter("sponsorship");
		
		List<String> params = new ArrayList<String>();
		params.add(name); params.add(username); params.add(password); params.add(school);
		params.add(major); params.add(gradYear); params.add(city); params.add(email);
		params.add(resume); params.add(gpa); params.add(phoneNum); params.add(image);
		
		for(String s : params) {
			if(s.length() == 0 || s == null) {
				System.out.println("Error in signup student page");
				request.setAttribute("error", "One or more of the fields was left blank. Please try again");
				request.getRequestDispatcher("jsp/" + StringConstants.signupPageStudent).forward(request, response);;
				break;
			}
		}
		
		
		if(sqldriver.studentUsernameExist(username)) {
			request.setAttribute("error", "The username you chose has already been taken. Please try another one.");
			request.getRequestDispatcher("jsp/" + StringConstants.signupPageStudent).forward(request, response);
		}
		else {
			Student s = new Student(name, username, password, school, major, gradYear, city, email, 
					resume, image, gpa, phoneNum, sponsorship);
			sqldriver.addStudent(s);
			request.getRequestDispatcher("jsp/" + StringConstants.loginPage).forward(request, response);
		}
	}
}
