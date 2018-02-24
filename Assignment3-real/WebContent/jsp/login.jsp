<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="data.StringConstants" %>
<html>
<head>
	<title>Login</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
	<link href="https://fonts.googleapis.com/css?family=Lato:700i" rel="stylesheet">
</head>
<body>

	<div id = "title_container">
		JOOB
	</div>
		
	<div id = "login_container">
		<form action = "${pageContext.request.contextPath}<%= StringConstants.LoginServlet %>">
			<input type="text" name="username" placeholder="Username">
			<br>
			<input type="text" name="password" placeholder="Password">
			<br>
			<br>
			<div class=error_message>
		 		<!-- if there is an error display it, else display the empty string -->
				<% String error = (String)request.getAttribute("error");
				if (error == null) error = "";%>
				<%= error %>
			</div>
			<input id="sub" type="submit" value="Log In" name="act">
			<h1>Do not have an account?</h1>
			<input id="student_signup" type="submit" value="Sign Up as Student" name="act">
			<input id="employer_signup" type="submit" value="Sign Up as Employer" name="act">
			<input id="guest" type="submit" value="Log in as Guest" name="act">
		</form> 
	</div>

</body>
</html>