<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="data.chat" %>
<%@ page import="data.StringConstants" %>
<%@ page import="database.MySQLDriver" %>
<html>
<% 
	String type = (String)session.getAttribute("user_type");
	String myName = (String)session.getAttribute("logged_in_user");
	MySQLDriver server = (MySQLDriver)session.getAttribute("sqldriver");
	
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

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat.css">

<script src = "../js/chatPage.js" type="text/javascript"></script>
<title>Chat</title>
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
    <li><a href="jsp/<%=StringConstants.searchPage%>">Search</a></li>
    <li><a href="jsp/<%=StringConstants.explorePage %>">Explore</a></li>
    <li><a href="jsp/<%=StringConstants.chatPage%>" >Chat</a><li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="<%=StringConstants.loginPage%>"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
    </ul>
    </div>

    <!--right align -->
   <!--  <ul class="nav navbar-nav navbar-right"><li><a href="#">Logout</a></li></ul> -->
  </nav>
  
  <div id="discussion_board">
  	<h1>Discussion Board</h1>
  	<div id = "board"></div>
  	<div id = "db_wrapper">
	  	<input type="text" name="boardMessage" id  = "boardMessage"><br/><br/>
	  	<button id = "boardSumbit" type = "button" onclick = "post('<%= myName %>')">Submit to MessageBoard</button>
  	</div>
  </div>
		
		<table>
			<tbody>
			<td><h1>Matches</h1></td>
				<%
				if (type.equals("Student"))
				{
					for (String username : server.getStudentMatchUsernames(myName)){
				%>
					<tr>
						<td><button class = "userButton" onclick="saveName('<%=username%>','<%=type%>')"><%=username %></button></td>
					</tr>
					<%
					}
				}
				else
				{
					for (String username : server.getEmployerMatchUsernames(myName))
					{
				%>
							<tr>
								<td><button type="button" onclick="saveName('<%=username%>','<%=type%>')"><%=username %></button></td>
							</tr>
				<% 
					}
				}
				%>
			</tbody>
		</table>
		<div id="chatHistory">
			<h1>Chat History</h1>
			<div id = "p_wrapper">
				<p id="History"></p>
			</div>
			<div id = "chatWrapper">
				<input id = "message" type="text" name="mes" id = "message"><br/><br/>
				<input type="submit" value="Send" onclick="sendMessage('<%=myName%>','<%=type%>')">
			</div>
		</div>
	 	
		
</body>

</html>