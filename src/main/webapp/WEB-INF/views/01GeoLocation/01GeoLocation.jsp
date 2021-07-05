<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../resources/css/bootstrap.css" />
<script src="../resources/jquery/jquery-3.6.0.js"></script>
<title>Insert title here</title>
	<script type="text/javascript">
		/*
			Geolocation : 위치정보 서비스로써 브라우저가 현재 위도와 경도를
					JS코드에게 공급하는 서비스
					
					getCurrentPosition() : 현재위치얻기
						사용법 : getCurrentPosition(
								현재위치가 파악되었을때 호출되는 콜백메소드,
								위치파악중 오류발생시 호출되는 콜백메소드,
								옵션(위치를 파악할 최대 허용시간, 대기시간, 위치의 정확도)
								);
		*/
		var span;
		window.onload = function() {
			span = document.getElementById("result");
			
			if(navigator.geolocation) {
				span.innerHTML = "Geolocation API를 지원합니다.";
				
				/*
					옵션 항목은 3개의 값을 가진 전역객체를 사용한다.
						enableHighAccurcy : 정확도를 결정하는 Boolean타입의 값
						timeout : 위치값을 장치로 받을때까지의 대기시간(1000분의 1초)
						maximumAge : 캐시된 위치값을 반환받을수 있는 대기시간. 0을 지정하면
								캐시값을 사용하지 않고 항상 현재 위치값을 수집함.
				*/
				var options = {
						enableHighAccurcy : true,
						timeout : 5000,
						maximumAge : 3000
				};
				navigator.geolocation.getCurrentPosition(showPosition,showError,
						options);
			}
			else {
				span.innerHTML = "이 브라우저는 Geolocation API를 지원하지 않습니다.";
			}
		}
		
		// 위치를 찾았을대의 콜백메소드
		var showPosition = function(position) {
			// 콜백된 데이터를 통해 위도, 경도값을 얻어온다.
			var latitude = position.coords.latitude;
			var longitude = position.coords.longitude;
			span.innerHTML = " 위도:"+ latitude +", 경도:"+ longitude;
			
			document.getElementById("lat").value = latitude;
			document.getElementById("lng").value = longitude;
		}
		
		// 위치를 찾지 못했을때의 콜백메소드
		var showError = function(error) {
			switch(error.code) {
			case error.UNKNOWN_ERROR:
				span.innerHTML = "알수없는오류발생";break;
			case error.PERMISSION_DENIED:
				span.innerHTML = "권한이 없습니다.";break;
			case error.POSITION_UNAVAILABLE:
				span.innerHTML = "위치 확인불가";break;
			case error.TIMEOUT:
				span.innerHTML = "시간초과";break;
			}
		}
	</script>
</head>
<body>
	<div class="container">
		<h2>GeoLocation = 현재위치의 위도, 경도 알아내기</h2>
		<fieldset>
			<legend>현재위치 - 위도, 경도</legend>
			<span id="result" style="color:red; font-size:1.5em;
				font-weight:bold;"></span>
		</fieldset>
		
		위도 : <input type="text" id="lat" />
		경도 : <input type="text" id="lng" />
	</div>
</body>
</html>