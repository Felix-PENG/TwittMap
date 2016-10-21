<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>TwittMap</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
</head>
<body style="margin: 0%;font-family: 'Comic Sans MS', 'Comic Sans', cursive;">
	<div>
		<div style="background-color: #0666c6;height:50px;line-height: 50px;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);">
			<span style="margin-left: 1%;text-shadow: 2px 2px 10px black;font-size: 5vh;color:white">TwittMap</span>
			<span style="display: inline-block;float: right;margin-right: 1%;margin-top:8px">
				<select id="filterSelect" class="form-control">
					<option>Food</option>
					<option>Travel</option>
					<option>Study</option>
					<option>life</option>
					<option>love</option>
				</select>
			</span>
			<span style="float: right;margin-right: 1%;color:white;">Filter</span>
		</div>
		<div id="map" style="width:100%;height:580px;"></div>
	</div>

	<script  type="text/javascript">
		function myMap() {
			var myCenter = new google.maps.LatLng(51.508742,-0.120850);
			var mapCanvas = document.getElementById("map");
			var mapOptions = {center: myCenter, zoom: 5};
			var map = new google.maps.Map(mapCanvas, mapOptions);
			var marker = new google.maps.Marker({
				position:myCenter,
			});
			marker.setMap(map);
		}
	</script>
	
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCjxHGCl5uN2DPCMVaRttY0BeIaMFV-xM4&callback=myMap"></script>
	<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="js/filter.js"></script>
</body>
</html>