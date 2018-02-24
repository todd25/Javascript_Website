<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="data.StringConstants" %>
 <%@page import="data.Employer" %>
 <%@page import="data.Student" %>
 <%@page import="database.MySQLDriver" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<%
		Student student = null;
		Employer employer = null;
		MySQLDriver sqldriver = (MySQLDriver)session.getAttribute("sqldriver");
		
		String userType = (String)session.getAttribute("user_type");
		String searchType = "";
		String username = (String)session.getAttribute("logged_in_user");
		if(userType.equals("Student")) {
			student = sqldriver.getStudent(username);
			searchType = "Employer";
		}
		else {
			employer = sqldriver.getEmployer(username);
			searchType = "Student";
		}
	
	%>
	<head>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/search.css">
		<!-- jQuery library -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<!-- Latest compiled JavaScript -->
		<link href="https://fonts.googleapis.com/css?family=Lato:700i" rel="stylesheet">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<script src="../js/searchPage.js" type="text/javascript"></script>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
		
		<title>Search</title>
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
		    <li id="profile-button"><a href="#">Profile</a></li>
		    <li class="active"><a href="<%=StringConstants.searchPage%>">Search</a></li>
		    <li><a href="<%=StringConstants.explorePage %>">Explore</a></li>
		    </ul>
		    <ul class="nav navbar-nav navbar-right">
		      <li><a href="<%=StringConstants.loginPage%>"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
		    </ul>
		    </div>
		
		    <!--right align -->
		   <!--  <ul class="nav navbar-nav navbar-right"><li><a href="#">Logout</a></li></ul> -->
  		</nav>

		<div class="filtering-params">
				<input id="sponsorship" type="checkbox" name="sponorship">
				<% if(userType.equals("Student")) {
					
				%>
				<input id="industry" placeholder="industry name" type="text" name="industry">
				<input id="position" placeholder="position name" type="text" name="position">
				<%	
				}
				else {
				%>
				<input id="major" placeholder="major" type="text" name="major">
				<input id="gradYear" placeholder="gradYear"  type="text" name="gradYear">
				<% 
				}
				%>
				<button class="<%=userType%>" id="filtering-button">Filter!</button>
			</div>
		
		<div class="error-message">You have no more users to search</div>
		<div class="action-buttons">
			<img class="dislike" id="action" src="../img/dislike.png">
		</div>
		<div class="image-container">
			<img id="potential-match-image" src="">
			<div id="username"></div>
			<div id="others"></div>
			
		</div>
		<div class="action-buttons">
			<img class="like" id="action" src="../img/like.png">
		</div>
	</body>
	
</html>