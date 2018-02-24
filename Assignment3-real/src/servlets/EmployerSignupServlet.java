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

import data.Employer;
import data.StringConstants;
import database.MySQLDriver;

/**
 * Servlet implementation class EmployerSignupServlet
 */
@WebServlet("/EmployerSignupServlet")
public class EmployerSignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployerSignupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		MySQLDriver sqldriver = (MySQLDriver)session.getAttribute("sqldriver");
		
		String company, username, password, city, industry, email, phone, recruiter, position, image, sponsorship;
		company = (String)request.getParameter("name");
		username = (String)request.getParameter("username");
		password = (String)request.getParameter("password");
		industry = (String)request.getParameter("industry");
		city = (String)request.getParameter("city");
		email = (String)request.getParameter("email");
		phone = (String)request.getParameter("phone");
		recruiter = (String)request.getParameter("recruiter");
		position = (String)request.getParameter("position");
		image = (String)request.getParameter("image");
		sponsorship = (String)request.getParameter("sponsorship");
		
		
		List<String> params = new ArrayList<String>();
		params.add(company); params.add(username); params.add(password); 
		params.add(city); params.add(industry);params.add(email); params.add(phone); params.add(position);
		params.add(image); params.add(recruiter);
		
		
		for(String s : params) {
			if(s.length() == 0 || s == null) {
				System.out.println("Error in signup employer page");
				request.setAttribute("error", "One or more of the fields was left blank. Please try again");
				request.getRequestDispatcher("jsp/" + StringConstants.signupPageEmployer).forward(request, response);
				break;
			}
		}
		
		if(sqldriver.employerUsernameExist(username)) {
			System.out.println("Error in signup employer page: username taken");
			request.setAttribute("error", "The username you chose has already been taken. Please try another");
			request.getRequestDispatcher("jsp/" + StringConstants.signupPageEmployer).forward(request, response);
		}
		else {
			Employer e = new Employer(username, password, company, email, phone, image, city, industry, sponsorship, recruiter, position);
			sqldriver.addEmployer(e);
			request.getRequestDispatcher("jsp/" + StringConstants.loginPage).forward(request, response);
		}
	}

}
