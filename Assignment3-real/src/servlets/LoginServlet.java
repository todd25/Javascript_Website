package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Employer;
import data.StringConstants;
import data.Student;
import database.MySQLDriver;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);	 
		ServletContext context = request.getServletContext();
		FileReader configFile = new FileReader(context.getResource("/WEB-INF/rsrc/config.txt").getPath());
		BufferedReader br = new BufferedReader(configFile);
		String sCurrentLine="";
		String ipaddress = "";
		String db="";
		String username ="";
		String password = "";
		while((sCurrentLine=br.readLine())!=null){
			String parameter = sCurrentLine.split(":")[0];
			switch(parameter){
			case "ipaddress":
				ipaddress=sCurrentLine.split(":")[1];
				System.out.println(ipaddress);
				break;
			case "db":
				db=sCurrentLine.split(":")[1];
				System.out.println(db);
				break;
			case "username":
				username=sCurrentLine.split(":")[1];
				System.out.println(username);
				break;
			case "password":
				password=sCurrentLine.split(":")[1];
				System.out.println(password);
				break;
			}
		}
		
		MySQLDriver mysql = new MySQLDriver(ipaddress, db, username, password);
		mysql.connect();
		mysql.getAllStudents();
		
//		mysql.addStudentLiked("google", "tester");
//		mysql.addMessage("tester", "intel", true, "Testing Message");
		
		session.setAttribute("sqldriver", mysql);
		
		System.out.println("In Log In Servlet");
	
		String act = request.getParameter("act");
		if(act == null) {
			System.out.println("no button has been pressed");
		}
		else if(act.equals("Sign Up as Student")) {
			request.getRequestDispatcher("jsp/"+StringConstants.signupPageStudent).forward(request, response);
		}
		else if(act.equals("Sign Up as Employer")) {
			request.getRequestDispatcher("jsp/"+StringConstants.signupPageEmployer).forward(request, response);
		}
		else if(act.equals("Log in as Guest")){
			request.getRequestDispatcher("jsp/"+StringConstants.guestPage).forward(request, response);
		}
		else if(act.equals("Log In")){
			System.out.println("attempting to log in");
			
			String usernameFromLogin = (String)request.getParameter("username");
			String passwordFromLogin = (String)request.getParameter("password");
	
			System.out.println("username: " + usernameFromLogin);
			System.out.println("student username " + mysql.studentUsernameExist(usernameFromLogin));
			
			if(mysql.studentUsernameExist(usernameFromLogin)){
				if(mysql.authenticateStudent(usernameFromLogin, passwordFromLogin)){
					session.setAttribute("logged_in_user", usernameFromLogin);
					session.setAttribute("user_type", "Student");
					
					
					String username1 = (String)session.getAttribute("logged_in_user");
					Vector<String> matches = mysql.getStudentMatchUsernames(username1); 
					Vector<Employer> matchesObj = new Vector<Employer>();
					for(String s : matches) {
						matchesObj.add(mysql.getEmployer(s));
					}
					request.setAttribute("matchedList", matchesObj);
					request.getRequestDispatcher("jsp/"+ "profile.jsp").forward(request, response);
					
				}
				else{
					request.setAttribute("error", "Incorrect Student Password");
					request.getRequestDispatcher("jsp/" + StringConstants.loginPage).forward(request, response);
				}
			}
			
			else if(mysql.employerUsernameExist(usernameFromLogin)){
				if(mysql.authenticateEmployer(usernameFromLogin, passwordFromLogin)){
					session.setAttribute("logged_in_user", usernameFromLogin );
					session.setAttribute("user_type", "Employer");
					
					
					String username1 = (String)session.getAttribute("logged_in_user");
					Vector<String> matches = mysql.getStudentMatchUsernames(username1); 
					Vector<Student> matchesObj = new Vector<Student>();
					for(String s : matches) {
						matchesObj.add(mysql.getStudent(s));
					}
					request.setAttribute("matchedList", matchesObj);
					request.getRequestDispatcher("jsp/"+ StringConstants.profilePage).forward(request, response);

				}
				else{
					request.setAttribute("error", "Incorrect Employer Password");
					request.getRequestDispatcher("jsp/" + StringConstants.loginPage).forward(request, response);				}
			}
			
			else{
				request.setAttribute("error", "Incorrect Username");
				request.getRequestDispatcher("jsp/" + StringConstants.loginPage).forward(request, response);
			}
		}	
	}

}
