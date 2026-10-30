<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
	/*
	비동기 기술의 발전 순서
		1) XMLHttpRequest 직접 제어
		2) JQuery Ajax(외부 의존 라이브러리이므로 사용이 지양되고 있는 추세)
		3) Promise(브라우저 자체 지원 API)
		4) fetch(Promise 가 사용하기에 너무 코드량이 많고 복잡하므로 fetch 로 숨겨놓음)
		5) async/await
	*/
	
	//자바스크립트에서는 함수도 변수에 담을 수 있따(일급 citizen 객체)
	const login = async ()=>{
		
		/*
		let form Data={
			//전송할 데이터가 복합 데이터이므로, js의 객체로 표현한다. 이때 객체의 프로퍼티가 전송 파라미터가 된다
			accountId: $("input[name='accountId']").val(),
			pwd: $("input[name='pwd']").val()
		}
		*/
		
		let formData = new URLSearchParams();// 폼 전송 시 파라미터와 그 값을 query string 으로 자동 변환 ex) 변수=값&변수=값
		formData.append("accountId", $("input[name='accountId']").val());//accountId=batman
		formData.append("pwd", $("input[name='pwd']").val());//pwd=1234
		//최종 쿼리 스트링 accountId=batman&pwd=1234(즉 브라우저가 해쭸던 작업을 이 객체가 자동으로 해줌)
		
		//Promise 를 직접 쓰게 되면 개발자가 then() 을 처리해야 코드가 지저분해 진다 따라서 Promise를 숨겨놓은 방식인 async/await를 쓰자
		try{
            const response = await fetch("/login", {
                method:"POST",
                headers:{
                    //비동기 방식의 요청이므로,form을 직접 이용하여 전송하지 않으므로 지금까지 브라우저가 form 을 submit() 할때 대신 해줬던 헤더설정을
                    //개발자가 직접 해야 함.. 원래 비동기 방식에서는  json 형식을 압도적으로 많이 사용하지만, 우리의 경우 스프링 시큐리티의 로그인 폼이므로, 
                    //이 요청을 시큐리티의 UsernamePasswordAuthenticationFilter 가 받기 때문에 폼형식으로 보내야 한다..(즉  json 인식 못함)
                    contentType:"application/www-x-form-urlencoded; charset=UTF-8"
                },
                //폼형식으로 전송 시 그동안 웹브라우저가 문자열로 자동으로 변경해주는데, 그 형식은 아래와 같다..
                // accountId=batman&pwd=1234  이런 형식을 가리켜 query string  이라 한다..
                body:formData
            }); //비동기 요청 함수, Promise 반환

            //원래는 fetch 함수 호출시 반환되는  Promise가 서버의 응답을 받을때 호출되므로, 개발자가 이 then에서 응답을 받은 타이밍에 처리할 코드를 작성했으나.
            //이제는  then 을 숨겨놓았으므로, 별도의  .then을 쓰지 않고 그냥 fetch  아래에 응답이 왔을때의 코드를 작성하면 된다..
            
            //지금부터 머리와 몸으로 구성된 응답 정보로 부터 드디어 요청이 성공인지 실패인지에 대한 판단이 가능함
            console.log("서버의 status 코드는", response.status);
            if(response.status !=200){
            		throw new Error("서버로 부터 응답이 없어용!");
            }
            
            //then()을 쓰지 않고 파싱을 시도, 파싱이 성공되면 바로 아해 라인을 수행하며 별도의]\
            //then() 으로 처리하지 않음 또한 파싱이 실패할 경우 catch 문으로 자동으로 집입
            const json = await response.json();
            
            console.log("서버로부터 받은 응답 데이터는", json);
            alert(json.message);

            //응답이 도착한 시점 부터는 개발자는 서버의 응답 정보를 꺼내서, 이 요청이성공( 200 ) 인지 실패(400이상)인지를 판단해야 한다..

        }catch(error){
            	//1) fetch 에의해 서버에 요청을 했으나, 응답이 도착하지 않을 경우 이 catch 문 수행
			//2) response.json() 에 의해 파싱이 실패할 경우
			console.log("로그인 실패", error);
        }
	}

	$(function(){
		$("#bt-login").click(()=>{
			login()
		});
	});
	
</script>
</head>
<body>

	<div class="container">
		<h2>로그인</h2>
		<form id="form1">
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Enter Your Id" name="accountId">
			</div>
			
			<div class="form-group">
				<input type="password" class="form-control" id="pwd" placeholder="Enter password" name="pwd">
			</div>
			
			<button type="button" class="btn btn-primary" id="bt-login">Submit</button>
		</form>
	</div>

</body>
</html>
