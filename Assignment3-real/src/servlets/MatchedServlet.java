package servlets;

import java.io.IOException;
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


@WebServlet("/MatchedServlet")
public class MatchedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public MatchedServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		MySQLDriver sqldriver = (MySQLDriver)session.getAttribute("sqldriver");
		
		Student student = null;
		Employer employer = null;
		String userType = (String)session.getAttribute("user_type");
		String username = (String)session.getAttribute("logged_in_user");
		if(userType.equals("Student")) {
			student = sqldriver.getStudent(username);
			request.setAttribute("matchedList", student.getMatchedList());
		}
		else {
			employer = sqldriver.getEmployer(username);
			request.setAttribute("matchedList", employer.getMatchedList());
		}
		
		System.out.println("In MatchedServlet. Redirecting to matchedPage...");
		request.getRequestDispatcher("jsp/"+StringConstants.matchedPage).forward(request, response);
		
	}

}
