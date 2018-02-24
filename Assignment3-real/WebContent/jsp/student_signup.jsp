<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="data.StringConstants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/signup.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="signupContainer">
		<form action="${pageContext.request.contextPath}/<%=StringConstants.StudentSignupServlet %>">
			
		<fieldset class="row1">
			<legend>Account</legend>
			<label id="star">* </label>
			<label id="indicate">Indicates required field</label>
			<br>
			<br>
			<label id="star">* </label>Name:<br>
			<input type="text" name="name">
			<br>
			<label id="star">* </label>Username:<br>
			<input type="text" name="username">
			<br>
			<label id="star">* </label>Password:<br>
			<input type="text" name="password">
			<br>
		</fieldset>
		<fieldset class="row2">
			<legend>Personal Information</legend>
			<label id="star">* </label>School:<br>
			<input type="text" name="school">
			<br>
			<label id="star">* </label>Link to Profile Image:<br>
			<input type="text" name="image">
			<br>
			<label id="star">* </label>Major:<br>
			<input type="text" name="major">
			<br>
			<label id="star">* </label>Graduation Year:<br>
			<input type="text" name="gradYear">
			<br>
			<label id="star">* </label>City:<br>
			<input type="text" name="city">
			<br>
			<label id="star">* </label>Resume:<br>
			<input type="text" name="resume">
			<br>
			<label id="star">*</label>GPA:<br>
			<input type="text" name="GPA">
			<br>
			<label id="star">* </label>Email:<br>
			<input type="text" name="email">
			<br>
			<label id="star">*</label>Phone Number:<br>
			<input type="text" name="phone">
			<br>
			<label id="star">* </label>Sponsorship:(check if needed)
			<input id="checkbox" type="checkbox" name="sponsorship">
			<br>
		</fieldset>
			<div class ="error_message">
				<% String error = (String)request.getAttribute("error");
				if(error == null) error = "";
				%>
				<%= error %>
			</div>
			<input id="sub" type="submit" value="Create Account">
		</form>
		
		
	</div>

</body>
</html>