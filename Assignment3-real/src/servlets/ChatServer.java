package servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.chat;
import database.MySQLDriver;

/**
 * Servlet implementation class ChatServer
 */
@WebServlet("/ChatServer")
public class ChatServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		String username = request.getParameter("name");
		String message = request.getParameter("message");
		
		
		HttpSession session = request.getSession(true);
		MySQLDriver sqldriver = (MySQLDriver)session.getAttribute("sqldriver");
		String type = (String) session.getAttribute("user_type");
		String myName = (String) session.getAttribute("logged_in_user");
		
		if (message!= null)
		{
			Boolean direct =false;
			if (type.equals("Employer"))
			{
				direct = true;
			}
			else
			{
				direct = false;
			}
			if (type.equals("Student"))
			{
				sqldriver.addMessage(myName,chat.getCurrentUser(),direct,message);
				
			}
			else
			{
				sqldriver.addMessage(chat.getCurrentUser(),myName,direct,message);
			}
			
		}
		if (request.getParameter("savedName") != null)
		{
			chat.setCurrentUser(request.getParameter("savedName"));
			System.out.println(request.getParameter("savedName"));
		}
		if (chat.getCurrentUser()!=null && chat.getCurrentUser().equals(username))
		{
			Vector< String> temp;
			if (type.equals("Student"))
			{
				temp = sqldriver.getChatHistory(myName,username);
			}
			else
			{
				temp = sqldriver.getChatHistory(username,myName);
			}
			String st ="";
			for (String s : temp)
			{
				st+= s+"/";
			}
			response.getWriter().write(st);
		}
		else
		{
			response.getWriter().write("false");
		}
		
		
		
	}

}
