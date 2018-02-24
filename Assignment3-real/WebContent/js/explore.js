function getPlaceDetails(place_id, key) {
	console.log("calling getPlaceDetails()");
	var place_details = {};
	var url = "https://maps.googleapis.com/maps/api/place/details/json?key="+key+"&placeid="+place_id;
	var req = new XMLHttpRequest();
	req.open("GET", url, false);
	req.onreadystatechange = function () {
		if(req.readyState == 4 && req.status == 200) {
			var json = JSON.parse(req.responseText);
			var result = json.result;
			console.log("result: ", result);
			place_details.Name = result.name;
			place_details.Address = result.formatted_address;
			place_details.Phone = result.formatted_phone_number;
			place_details.Website = result.website;
			place_details.Lng = result.geometry.location.lng;
			place_details.Lat = result.geometry.location.lat;
		}
	};
	req.send(null);
	return place_details;
}



function imageHelper(images) {
	var profilePicSrc = images[0].display_sizes[0].uri;
	var img = document.createElement("img");
	img.id = "profile-picture";
	img.src = profilePicSrc;
	document.getElementById("photo").appendChild(img);
	
	var length = images.length;
	if(images.length >= 10) {
		length = 10;
	}
	
	for(var i = 0 ; i < length; i++) {
		var source = images[i].display_sizes[0].uri;
		var image = document.createElement("img");
		image.src = source;
		document.getElementById("photos").appendChild(image);
	}
	
}


function populateImage(query) {
	var url = "https://api.gettyimages.com/v3/search/images?fields=id,title,thumb,referral_destinations&sort_order=best&phrase="+query;
	var key = "pe3mkg3ueygckp9ugt487y2h";
	document.getElementById('photo').innerHTML = "";
	document.getElementById('photos').innerHTML = "";
	var req = new XMLHttpRequest();
	req.open("GET", url, false);
	req.setRequestHeader("Api-Key", key);
	req.onreadystatechange = function () {
		if(req.readyState == 4 && req.status == 200) {
			var json = JSON.parse(req.responseText);
			imageHelper(json.images);
			console.log("getty json: ", json);
		}
	}
	req.send(null);
}

function populateDOM(place_details) {
	console.log("calling populateDOM");
	document.getElementById("place-details").innerHTML = "";
	for(var key in place_details) {
		if(place_details[key] != null && key != 'Lng' && key != 'Lat') {
			if(key == 'Website') {
				var h = document.createElement("H1");
				h.innerHTML = 'Website: ';
				var a = document.createElement('a');
				var linkText = document.createTextNode(place_details[key]);
				a.appendChild(linkText);
				a.title = "Website: ";
				a.href = place_details[key];
				h.appendChild(a);
				document.getElementById("place-details").appendChild(h);
				
			}
			else {
				var h = document.createElement("H1");
				var t = document.createTextNode(key + ": " + place_details[key]);
				h.appendChild(t);
				document.getElementById("place-details").appendChild(h);
			}
			
		}
	}
}

function infoMap() {
	console.log("calling infoMap()")
	// make initial call to get placeID
	var query = document.getElementById("input-field").value;
	console.log("query: " + query);
	var key = "AIzaSyAsZEpDtr46wJTZ6RfPApy_isFBL6MeI3A";
	var textSearchURL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+query+"&key="+key;
	var req = new XMLHttpRequest();
	req.open("GET", textSearchURL, false);
	req.onreadystatechange = function () {
		if(req.readyState == 4 && req.status == 200) {
			var json = JSON.parse(req.responseText);
			var results = json.results;
			if(results.length >= 1) {
				var place_id = results[0].place_id;
				var place_details = getPlaceDetails(place_id, key); // get extended place_details
				console.log("place_details: ", place_details);
				populateDOM(place_details);
				populateImage(query);
			}
		}
	};
	req.send(null);
}


window.onload = function () {
	console.log("ready!!");
	document.getElementById("search-button").onclick = function() {
		infoMap();
	};
};
