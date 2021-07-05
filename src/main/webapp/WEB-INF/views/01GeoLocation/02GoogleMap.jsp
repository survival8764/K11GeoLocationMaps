<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../resources/css/bootstrap.css" />
<script src="../resources/jquery/jquery-3.6.0.js"></script>
<title>Insert title here</title>
	<style>
		#map {
			width:800px; height:600px;
		}
	</style>
	<script>
		function initMap() {
			var uluru = {lat:37.480316, lng:126.882771};
			var map = new google.maps.Map(document.getElementById('map'), {
				zoom: 17,
				center: uluru
			});
			var marker = new google.maps.Marker({
				position: uluru,
				map: map
			});
		}
		window.onload = function() {
			initMap();
		}
	</script>
</head>
<body>
	<div class="container">
		<h2>Google Map 띄워보기</h2>
		<div id="map"></div>
		<script async defer
			src="https://maps.googleapis.com/maps/api/js?key=${apiKey }"></script>
	</div>
</body>
</html>