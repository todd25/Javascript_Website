
var socket = new WebSocket("ws://localhost:8080/"+"/Assignment3-real"+"/ws");

socket.onerror = function(event) {
    onError(event);
};

socket.onopen = function(event) {
    onOpen(event);
};

socket.onmessage = function(event) {
	onMessage(event);
};

function onMessage(event) {
//	var json = JSON.parse(event.data); 
	
	 document.getElementById('board').innerHTML 
        = event.data;
}

function onOpen(event) {
	alert('Connection established');
}

function onError(event) {
	alert('Error');
}

function send(username) {
	var message = document.getElementById('boardMessage').value;
	var username = username;
	var json = { 
			'username' : username,
			'boardMessage' : message
	};
	socket.send(JSON.stringify(json));
	return false;
}

function post(username)
{
	send(username);
}




window.onload=function(){
	  document.getElementById("chatHistory").style.visibility = "hidden";
}
function loadDoc(username,type) {
	  showChatBox();
	  var user = username;
	  var xhttp = new XMLHttpRequest();
		xhttp.open("GET", window.location.pathname.substring(0, window.location.pathname.indexOf("/", 1)) +"/ChatServer?"+"name"+"="+user, false);
		xhttp.send();
		if (xhttp.responseText.trim().length > 0) {
			if (xhttp.responseText.localeCompare("false")!=0)
				{
					var arr = xhttp.responseText.split('/');
					var str = "";
					for (var i = 0; i < arr.length; i ++) {
						var line = arr[i].split(";");
						var boo = line[1];
						if ((boo==("true") && type==("Employer")) || (boo==("false") && type==("Student")))
						{
							str = str + '<p "id = left">'+line[0]+'</p>';
						}
						else
						{
							str = str + '<p "id = right">'+line[0]+'</p>';
						}
					}
				}
			setInterval(function(){
				loadDoc(username,type);
			},2000);
			
			document.getElementById("History").innerHTML =str;
		}
}
function showChatBox(){
	document.getElementById("chatHistory").style.visibility = "visible";
}
function saveName(username,type){
	var user = username;
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", window.location.pathname.substring(0, window.location.pathname.indexOf("/", 1)) +"/ChatServer?"+"savedName"+"="+username, false);
	xhttp.send();
	loadDoc(username,type);
}
function sendMessage(username,type)
{
	var str = document.getElementById("message").value;
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", window.location.pathname.substring(0, window.location.pathname.indexOf("/", 1)) +"/ChatServer?"+"message"+"="+str+"&name="+username + "&type="+type, false);
	xhttp.send();
}