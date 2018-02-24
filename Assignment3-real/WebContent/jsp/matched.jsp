<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.Vector" %>
<%@ page import="data.Student" %>
<%@ page import="data.Employer" %>
<%@page import = "data.StringConstants" %>
<html>
<%	
String userType = (String)session.getAttribute("user_type");
Vector<Student> studentList = new Vector<Student>();
Vector<Employer> employerList = new Vector<Employer>();
if(userType!=null && userType.equals("Student")){
	employerList = (Vector<Employer>)request.getAttribute("matchedList");
	
}
else if(userType!=null&&userType.equals("Employer")){
	studentList = (Vector<Student>)request.getAttribute("matchedList");
}

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Matched List</title>
</head>
<body>

	<nav class="navbar navbar-default">
	  	<div class="container-fluid">
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav">
	      	<li title="Go To Profile">
	      		<a href="<%=StringConstants.profilePage%>">
	      			<img id="profile-button" src="../img/profile.png">
      			</a>
     			</li>
	      	<li title="Go To Matched">
	      		<form action="${pageContext.request.contextPath}<%=StringConstants.MatchedServlet%>">
	      			<input type="image" src="../img/matched.png" alt="Submit Form">
	      		</form>
	      	</li>
	      </ul>
	      <h1 class="navbar-brand">[TITLE STUB]</h1>
	      <ul class="nav navbar-nav navbar-right">
	        <li title="Logout">
	        	<a href="<%=StringConstants.loginPage%>">
	        		<img id="logout-button" src="../img/logout.png">
        		</a>
       		</li>
			<li title="Search">
				<form action="${pageContext.request.contextPath}<%=StringConstants.SearchServlet%>">
					<input type="image" src="../img/search.png" alt="Submit Form">
				</form>
			</li>
	      </ul>
	    </div>
	  </div>
	</nav>

	<table id="matched">
	
	<%if(userType!=null&&userType.equals("Employer")){
		if(!studentList.isEmpty()){
			for(Student student : studentList){
				String studentname = student.getFullname();
			%>
			<tr id = "matched_row">
				<td>
					<h1><%= studentname %></h1>
				
				</td>
			</tr>
			<%}
		}
		else{%>
			<tr id = "matched_row">
			<td>
				<h1>You do not have any matches</h1>
			
			</td>
		</tr>
		<%}
	} 
	else if(userType!=null && userType.equals("Student")){
		if(!employerList.isEmpty()){
			for(Employer employer : employerList){
				String employername = employer.getCompany();
				%>
				<tr id = "matched_row">
					<td>
						<h1><%= employername %></h1>
					
					</td>
				</tr>
				
			<%}
		}else{%>
		<tr id = "matched_row">
			<td>
				<h1>You do not have any matches</h1>
			</td>
		</tr>
	<%}
	}%>
		
	
	</table>
</body>
</html>