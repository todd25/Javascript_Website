<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@page import="data.Employer" %>
 <%@page import = "data.Student" %>
 <%@page import = "data.StringConstants" %>
 <%@page import = "database.MySQLDriver" %>
 <%@page import="java.util.Vector" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%
	Student student = null;
	Employer employer = null;
	
	MySQLDriver sqldriver = (MySQLDriver)session.getAttribute("sqldriver");
	
	Vector<Student> studentList = new Vector<Student>();
	Vector<Employer> employerList = new Vector<Employer>();
	
	String userType = (String)session.getAttribute("user_type");
	String username = (String)session.getAttribute("logged_in_user");
	if(userType.equals("Student")) {
		student = sqldriver.getStudent(username);
		employerList = (Vector<Employer>)request.getAttribute("matchedList");

	}
	else {
		employer = sqldriver.getEmployer(username);
		studentList = (Vector<Student>)request.getAttribute("matchedList");
	}
%>

<head>

<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/test_profile.css">

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
    <li class="active"><a href="#">Profile</a></li>
    <li><a href="#">Search</a></li>
    <li><a id="sponsor" href="#">Sponsorship: <input type = "text"  name="sponsorship" > <img id="search_navbar" src ="${pageContext.request.contextPath}/img/search.png"></a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
      <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
    </ul>
    </div>

    <!--right align -->
   <!--  <ul class="nav navbar-nav navbar-right"><li><a href="#">Logout</a></li></ul> -->
  </nav>

		<% 
			if(userType.equals("Student")) {
				
		%>
		<div class="profile-container">
			<div id = "user_container">
				<img id="profile-picture" src="<%=student.getImage()%>">
				<p id="username">@<%= student.getUsername() %></p>
			</div>
			<div id="content">

			<p id="fullname">Full Name: <%= student.getFullname() %></p>
			<p id="email">Email: <%=student.getEmail() %></p>
			<p id="city">City: <%=student.getCity() %></p>
			<p id="school">School: <%=student.getSchool()%></p>
			<p id="major">Major: <%=student.getMajor() %>
			<p id="gradYear">Graduation Year: <%=student.getGradYear() %>
			<p id="gpa">GPA: <%=student.getGPA()%></p>
			<P id="sponsorship">Sponsorship: <%=student.getSponsorship()%></P>
			<p id="resume">Resume: <%=student.getResume() %>
			<p id="phone">Phone: <%=student.getPhone() %>
			</div>
				
				<table id="matched">
					<caption>Matched</caption>
					<tbody>
					<%if(userType != null && userType.equals("Employer")){
						if(!studentList.isEmpty()){
							for(Student s : studentList){
								String studentname = s.getFullname();
								System.out.println("studentname: "+studentname);
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
							for(Employer e : employerList){
								String employername = e.getCompany();
								System.out.println("employername: "+employername);
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
					</tbody>
				</table>
		</div>
		
		<% 
		
			}
			else {
		%>
		<div class="profile-container">
			<div id = "user_container">
				<img id="profile-picture" src="<%=employer.getImage()%>">
				<p id="username">@<%= employer.getUsername() %></p>
			</div>
			<div id="content">
			<p id="fullname">Company Name: <%= employer.getCompany() %></p>
			<p id="recruiter">Recruiter: <%= employer.getRecruiter() %></p>
			<p id="position">Position: <%=employer.getPosition() %></p>
			<p id="city">City: <%=employer.getCity() %></p>
			<p id="sponsorship">Sponsorship: <%=employer.getSponsorship() %></p>
			<p id="email">Email: <%=employer.getEmail() %></p>
			<p id="phone">Phone: <%=employer.getPhone() %></p>
		  </div>
		  
			<table id="matched">
			<caption>Matched</caption>
			<%if(userType != null && userType.equals("Employer")){
				if(!studentList.isEmpty()){
					for(Student s : studentList){
						String studentname = s.getFullname();
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
					for(Employer e : employerList){
						String employername = e.getCompany();
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
		  
		</div>
		
		<% 
		
			}
		%>
		
		
		

		
		
</body>
</html>