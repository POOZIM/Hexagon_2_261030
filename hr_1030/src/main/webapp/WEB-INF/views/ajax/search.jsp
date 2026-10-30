<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>

/*서버에 요청을 하되, 기존의 동기 방식이 아닌 비동기 방식으로 요청을 할 것임    
 
 동기 - js 가 웹브라우저에게 서버로 출발할 것을 부탁을 하면 브라우저의 api가 js 대신 서버로 출발함   
 		이때 js 는 브라우저가 서버로 부터 응답 정보를 받을때까지 대기 상태에 빠짐   
 		이 방식은 서버가 클라이언트에게 보여질 html 을 받아서 화면을 다시 그리는데 적당..   
 
 비동기 - js 가 웹브라우에게 서버로 출발할 것을 부탁을 하면 브라우저의 api가 js 대신 서버로 출발함..   
 			이때 js는 브라우저를 기다리지 않고, 자기가 할 로직을 수행할 수 있다(자유롭다)
			이 방식은 형재 사용자가 보고 잇는 화면은 ㄱ그대로 유지하고 서버로 부터 변경 될 일부 데이터만을 받아올때 적절
			참고로 비동기방식은 웹상의 요청이 현재 js의 메인실행부에서 수행되는 것이 아니라 백그라운드에서 진행되기 때문에
			화면 페이지 새로고침이 일어나지 않음
 */
 
 //비동기 방식의 통신을 하려면 XMLHttpRequest 객체를 이용
function sendAsync(){
	let xhttp = new XMLHttpRequest();//비동기 통신이 가능한 객체 생성
	
	//서버로 부터 응답이 올때 감지하는 이벤트
	xhttp.onload = function(){
		document.getElementById("content").innerText=xhttp.responseText;		
	}
	xhttp.open("GET", "/ajax/test", true);//사용자가 피부로 느끼지 못하게 백그라운드에서 요청을 비동기 방식으로 시도하겠다는 의미
	xhttp.send();//요청이 일어나는 시점
}

//서버에서 지금 입력한 단어와 관련이ㅐㅆ는 단어들을
function getHint(){
	let form1 = document.getElementById("form1");
	form1.action="/ajax/getword";
	form1.method="POST";
	form1.submit();//전송
	
	console.log("실행");
}
</script>
</head>
<body>
	
	<form id="form1">
		<input type="text">
		<button type="button" onClick="sendAsync()">검색</button>
		<div id="content">검색어에 사용 될 제안 키워드들</div>
	</form>

</body>
</html>