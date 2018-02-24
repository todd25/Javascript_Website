<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="data.StringConstants" %>
 <%@page import="data.Employer" %>
 <%@page import="data.Student" %>
 <%@page import="database.MySQLDriver" %>
 <%@page import="java.util.Vector" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<%
		MySQLDriver sqldriver = (MySQLDriver)session.getAttribute("sqldriver");
		Vector<Employer> employers = sqldriver.getAllEmployers();
		Vector<Student> students = sqldriver.getAllStudents();
	
	%>
	<head>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/guest.css">
		<!-- jQuery library -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<!-- Latest compiled JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<link href="https://fonts.googleapis.com/css?family=Lato:700i" rel="stylesheet">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
		
	<title>Guest Page</title>
	</head>
	<body>
		<nav class="navbar navbar-default"> <!--can also do inverse -->
			<div class-"container-fluid">
		
			<!-- Logo position to left -->
			<div class="navbar-header">
			<!--Add button code here -->
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#mainNavBar">
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			</button>
		
			<a href="#" class="navbar-brand"><span><img id = "icon" src="${pageContext.request.contextPath}/img/icon.png" alt="Brand"></span>JOOB</a>
			</div>
		
		    <div class="collapse navbar-collapse" id="mainNavBar">
		    <ul class="nav navbar-nav"> <!-- nav nav-pills or nav navbar-nav -->
		    <li class="active"><a href="#">Guest</a></li>
		    <li><a href="jsp/<%=StringConstants.guestExplorePage %>">Explore</a></li>
		    </ul>
		    <ul class="nav navbar-nav navbar-right">
		      <li><a href="jsp/<%=StringConstants.loginPage%>"><span class="glyphicon glyphicon-log-in"></span> Exit</a></li>
		    </ul>
		    </div>
		
		    <!--right align -->
		   <!--  <ul class="nav navbar-nav navbar-right"><li><a href="#">Logout</a></li></ul> -->
  		</nav>
		
		<div id="users">
				<%for(Student student : students){ 
					
				%>
				<div id = "user_container">
					<img id="profile-picture" src="<%=student.getImage()%>">
					<p id="username">@<%= student.getUsername() %></p>
				</div>
				<div id="content">
					<p id="fullname">Full Name: <%= student.getFullname() %></p>
					<p id="major">Major: <%=student.getMajor() %>
					<p id="school">School: <%=student.getSchool()%></p>
					<p id="city">City: <%=student.getCity() %></p>
					<p id="gradYear">Graduation Year: <%=student.getGradYear() %>
					<p id="gpa">GPA: <%=student.getGPA()%></p>
					<p id="resume">Resume: <%=student.getResume() %>
					<p id="email">Email: <%=student.getEmail() %></p>
					<p id="phone">Phone: <%=student.getPhone() %>
				</div>
				<%}
					for(Employer employer:employers){
				%>
				
					<div id = "user_container">
						<img id="profile-picture" src="<%=employer.getImage()%>">
						<p id="username">@<%= employer.getUsername() %></p>
					</div>
						<div id="content">
						<p id="fullname">Company Name: <%= employer.getCompany() %></p>
						<p id="recruiter">Recruiter: <%= employer.getRecruiter() %></p>
						<p id="position">Position: <%=employer.getPosition() %></p>
						<p id="city">City: <%=employer.getCity() %></p>
						<p id="industry">Industry: <%=employer.getIndustry()%></p>
						<p id="email">Email: <%=employer.getEmail() %></p>
						<p id="phone">Phone: <%=employer.getPhone() %></p>
				  	</div>
				<%} %>
		</div>

	</body>
	
</html>