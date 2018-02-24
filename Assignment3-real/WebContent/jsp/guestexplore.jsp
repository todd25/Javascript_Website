<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="data.StringConstants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Explore Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/explore.css">
<script src="../js/explore.js" type="text/javascript"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

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
    <li class="active"><a href="#">Explore</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="<%=StringConstants.loginPage%>"><span class="glyphicon glyphicon-log-in"></span> Exit</a></li>
    </ul>
    </div>

    <!--right align -->
   <!--  <ul class="nav navbar-nav navbar-right"><li><a href="#">Logout</a></li></ul> -->
  </nav>
	<div id="content">
	<h1>Please Enter the Name of the Company you want to Explore!</h1>
		<div id="search">
			<input id="input-field" type="text">
			<button id="search-button">Search!</button>
		</div>
		<div id="results">
			<div id="photo"></div>
			<div id="place-details"></div>
			<div id="map"></div>
			<div id="photos"></div>
		</div>
	</div>

</body>
</html>