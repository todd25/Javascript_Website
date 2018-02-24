window.onload = function() {
	var url = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 1)) + "/SearchServlet?";
	var p = "";
	
	// Only display the filtering params 
	var likedButton = document.getElementsByClassName("like")[0];
	var dislikedButton = document.getElementsByClassName("dislike")[0];
	document.getElementsByClassName("error-message")[0].style.display = "none";
	document.getElementsByClassName("image-container")[0].style.display = "none";
	document.getElementsByClassName("action-buttons")[0].style.display = "none";
	document.getElementsByClassName("action-buttons")[1].style.display = "none";

	
	
	document.getElementById('filtering-button').onclick = function() {
		document.getElementsByClassName("filtering-params")[0].style.display = "none";
		
		var params = {};
		var userType = document.getElementById('filtering-button').className;
		console.log("userType: ", userType)
		var sponsorship = document.getElementById('sponsorship').checked;
		if(userType == 'Student') {
			var industry = document.getElementById('industry').value;
			var position = document.getElementById("position").value;
			
			params['userType'] = userType;
			if(sponsorship) params['sponsorship'] = 'true';
			else params['sponsorship'] = 'false';
			if(industry.length > 0) params['industry'] = industry;
			else params['industry'] = 'none';
			if(position.length > 0) params['position'] = position;
			else params['position'] = 'none';
			
		}
		else {
			params['userType'] = userType;
			var major = document.getElementById('major').value;
			var gradYear = document.getElementById('gradYear').value;
			if(sponsorship.checked) params['sponsorship'] = 'true';
			else params['sponsorship'] = 'false';
			if(major.length > 0) params['major'] = major;
			else params['major'] = 'none';
			if(gradYear.length > 0) params['gradYear'] = gradYear;
			else params['gradYear'] = 'none';
		}
		
		for(var key in params) {
			p += key + '=' + params[key] + '&';
		}
		
		p = p.substring(0, p.length - 1); // all the parameters minus the action
		
		var initial_p = 'action=initial&' + p;
		var initial_url = encodeURI(url + initial_p);
		
		console.log("GET REQUEST URL: ", initial_url);
		
		
		// do initial ajax request
		var req = new XMLHttpRequest();
		req.open("GET", initial_url, true);
		req.onreadystatechange = function () {
			if(req.readyState == 4 && req.status == 200) {
				var json = JSON.parse(req.responseText);
				console.log(json);
				var message = json.message;
				if(message.startsWith("error")) {
					document.getElementsByClassName('filtering-params')[0].style.display = 'none';
					document.getElementsByClassName("error-message")[0].style.display = "block";
				}
				else {
					document.getElementsByClassName("image-container")[0].style.display = 'block';
					document.getElementsByClassName("action-buttons")[0].style.display = 'block';
					document.getElementsByClassName("action-buttons")[1].style.display = 'block';
					document.getElementsByClassName("error-message")[0].style.display = "none";
					
					
					var username = json.username;
					var image = json.image;
					document.getElementById("potential-match-image").src = image;
					document.getElementById("username").innerHTML = username;	
					
					for(var key in json) {
						if(key == 'message' || key == 'image') continue;
						console.log("adding fields to the page");
						var h = document.createElement("H1");
						var t = document.createTextNode(key + ": " +json[key]);
						h.appendChild(t);
						document.getElementById("others").appendChild(h);
					}
				}
			}
		}
		req.send(null);
	}
	
	// the intial filtering is done, now go into the normal flow
	
	likedButton.addEventListener('click', function (){
		var likedUser = document.getElementById("username").innerHTML;
		alert("You have liked: " + likedUser);
		var liked_p = 'action=liked&username=' + likedUser + '&';
		var liked_params = liked_p + p;
		
		
		var req = new XMLHttpRequest();
		console.log("liked button url: ", encodeURI(url + liked_params));
		req.open("GET", url + liked_params, true);
		req.onreadystatechange = function () {
			if(req.readyState == 4 && req.status == 200) {
				var json = JSON.parse(req.responseText);
				console.log(json);
				if(json.match == true) {
					alert("You have matched! Please check your matched list for more details");
				}
				var message = json.message;
				if(message.startsWith("error")) {
					document.getElementsByClassName("image-container")[0].style.display = 'none';
					document.getElementsByClassName("action-buttons")[0].style.display = 'none';
					document.getElementsByClassName("action-buttons")[1].style.display = 'none';
					document.getElementsByClassName("error-message")[0].style.display = "block";
				}
				else {
					var username = json.username;
					var image = json.image;
					document.getElementById("potential-match-image").src = image;
					document.getElementById("username").innerHTML = username;
					while(document.getElementById("others").firstChild) {
						document.getElementById("others").removeChild(document.getElementById("others").firstChild);
					}
					for(var key in json) {
						if(key == 'message' || key == 'image' || key == 'match') continue;
						console.log("adding fields to the page");
						var h = document.createElement("H1");
						var t = document.createTextNode(key + ": " + json[key]);
						h.appendChild(t);
						document.getElementById("others").appendChild(h);
					}
				} 
			}
		}
		req.send(null);
	});
	
	dislikedButton.addEventListener('click', function() {
		var dislikedUser = document.getElementById("username").innerHTML;
		alert("You have disliked: " + dislikedUser);
		
		var disliked_p = 'action=disliked&username=' + dislikedUser + '&';
		var disliked_params = disliked_p + p;
		
		var req = new XMLHttpRequest();
		console.log("dislked button url: ", encodeURI(url + disliked_params));
		req.open("GET", url + disliked_params, true);
		req.onreadystatechange = function () {
			if(req.readyState == 4 && req.status == 200) {
				console.log(req.responseText);
				var json = JSON.parse(req.responseText);
				console.log(json);
				var message = json.message;
				if(message.startsWith("error")) {
					document.getElementsByClassName("image-container")[0].style.display = 'none';
					document.getElementsByClassName("action-buttons")[0].style.display = 'none';
					document.getElementsByClassName("action-buttons")[1].style.display = 'none';
					document.getElementsByClassName("error-message")[0].style.display = "block";
				}
				else {
					var username = json.username;
					var image = json.image;
					document.getElementById("potential-match-image").src = image;
					document.getElementById("username").innerHTML = username;	
					while(document.getElementById("others").firstChild) {
						document.getElementById("others").removeChild(document.getElementById("others").firstChild);
					}
					for(var key in json) {
						if(key == 'message' || key == 'image' || key == 'match') continue;
						var h = document.createElement("H1");
						var t = document.createTextNode(key + ": " + json[key]);
						h.appendChild(t);
						document.getElementById("others").appendChild(h);
					}
				}
			}
		}
		req.send(null);
	});
}