<%@page import="com.hexagon.springmvc.security.AdminDetails"%>
<%@page import="org.springframework.security.core.Authentication"%>
<%@page import="com.hexagon.springmvc.domain.Admin"%>
<%@page import="org.springframework.security.core.context.SecurityContext"%>
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
	
	/*-------------------------------------------------------------------------------------------
		최상위 카테고리 목록 가져오기!! 
		비동기 통신을 위한 api 발전 순서 
		1) XMLHttpRequest
		2) JQuery ajax
		3) Promise ( 웹 요청 기능이 없어서 ajax 와 함께 씀 )
		4) fetch ( 비동기 웹 요청 함수이고 + 호출 시 promise 반환 ) = primise + $.ajax()
		5) async/await
	----------------------------------------------------------------------------------------------*/
	function getTopList(){
		// 비동기 요청일 경우 서버로부터 얻자고 하는 대상은 순수 데이터이므로 Url 은 Resful 을 준수하자  
			fetch("/api/topcategories", {
			method:"GET",
			
		}) 
		.then((response)=>{//서버에 보낸 요청에 대해 서버가 응답했을때는 무조건(500 응답에도 호출)
			// 이 시점에 서버로부터 응답 헤더 및 응답 데이터 객체가 도착한다 ( 성공코드200 이 올지 실패코드 300~가 올지 아직 모름 ) 
			if(!response.ok){ // 200 번대가 아니라면... 즉 뭔가 문제가 있는 경우..	
				throw new Error("목록 조회 실패");
			}
			return response.json(); //서버로부터 받아온 응답 결과물이 json형식을 갖추었다면 파싱, 형식이 올바르지 않다면 에러
											  //이 파싱 작업 자체도 비동기로 맡김..Promise
												// 응답 정보 자체는 아직 json 으로 변환되지 않은 객체 상태이므로, 이 데이터를 json 으로 꺼내기 위해
												// 비동기 방식의 파싱을 시도하며 파싱 시도를 위한 Promise 또 반환
		}) // fetch 에 의해 웹 요청이 일어나고, 서버에서 응답이 오면 그 때 then() 호출 됨 
		.then(res =>{ // response.json() 도 promise 가 반환되므로, 그 promise에 대한 then() 을 처리하자..	
							// 이 then()은 json 파싱이 성공된 시점에만 호출, 파싱에 실패하면 catch 문 수행
			console.log("목록 조회 성공", res);//js 객체 배열
			
			$("select[name='topCategory']").append("<option value='0'>▼상위 카테고리 선택</option>");
			
			for(let i=0;i<res.length;i++){
				$("select[name='topCategory']").append("<option value='"+res[i].topcategoryId+"'>"+res[i].topName+"</option>");
			}
			
		})
		.catch(error =>{
			console.log("에러발생", error);
		});
	}
	
	/*-------------------------------
	상위 카테고리에 소속된 하위 카테고리
	----------------------------------*/
	function getSubList(topcategoryId){
		fetch("/api/subcategories/" + topcategoryId,{
			method:"GET"
		})
		//서버로 여청한 후 응답 정보가 도착하면 무조건 Promise 의 then() 호출
		.then(response =>{
			if(!response.ok){
				throw new Error("목록 조회 실패");
			}
			return response.json();//응답 정보로 받은 json 문자열을 다시 js 의 객체로 복원(파싱)
		})
		//response.json()에 의한 피싱이 성공한 경우 아래의 then() 호출
		.then( res =>{
			console.log(res);
			
			$("select[name='subCategory']").empty();//기존 모든 요소 삭제!!
			
			for(let i=0;i<res.length;i++){
				$("select[name='subCategory']").append("<option value='"+res[i].subCategoryId+"'>"+res[i].subName+"</option>");
			}

		})
		.catch(error =>{
			console.log("에러발생", error);
		});
	}
	
	//동기 방식의 등록 요청
	/*
		1) XMLHttpRequest
		2) JQuery ajax
		3) Promise 
		4) fetch
		5) async/await - Promise+fetch()를 사용하지만 좀 더 단순화 시켜서 마치 동기 방식의 코드를 작성하는 방식으로
	*/
	
	const regist = async ()=>{
		
		//글쓰기 폼의 내용들 수집
		const news = {
			subCategoryId:$("select[name='subCategory']").val(),
			subject:$("input[name='subject']").val(),
			author:$("input[name='author']").val(),
			detail:$("textarea[name='detail']").val()
		}
		
		//인터넷 상으로 서버와 클라이언트간 데이터를 주고 받을 때는 문자열로 변환하여 전송 및 응답 받을 수 있음
		//위의 news는 js의 객체 이므로 그 자체론 전송 불가 따라서 문자열화 시켜야 함
		//개발자가 손수 쌍따옴표로 감싸지 말고 JSON 내장 객체의 자동 문자열화 메서드인 stringify() 를 ㅣ용하자!
		console.log("JSON으로 바꿈", JSON.stringify(news))
		
		try{
		//fetch 함수 호출 후 응답 정보 받기
		reponse = await fetch("/api/news", {
			method:"POST",
			headers:{
				"Content-Type" : "application/json"
			},
			body:JSON.stringify(news)//작성 된 데이터
		});
		
		//async/await 는 사실 내부에 Promise 수행 되기는 하나, 개발자가 then() 을 여러개
		//체이닝하여 코드를 작성하는 것 자체가 복잡하므로, 이 과정을 마치 동기 방식처럼 숨겨 놓은 기술
		//여전히 fetch() 문에 의해 Promise 가 반환되지만,서버로부터 응답이 없을 경우의 처리를
		//개발자가 직접 처리할 필요없으며 응답이 없을 경우 알아서 catch문을 수행함
		//또한 response,json()에 의해 또 다른 Promise가 반환 됨 역시 개발자가
		//then() 이용하여 피싱성공 시점 처리 할 필요가 없음
		//겉보기엔 비동기가 아닌 순차수행처럼 보임
		console.log("이 코드 라인은 fetch() 실행 후 서버에서 응답을 받게 되면 수행 됨");
		//파싱
		const result = response.json();//파싱시도(비동기 통신에서는 서버로부터 받은 응답 정보가 json형태라는 것을 전제로 함 일관성목적!!!!!!!!!!)
		console.log("서버로부터 받은 응답 데이터를 json으로 파실한 결과는", result);
		
	}catch(error){
		console.log("등록 중 에러 발생", error);
	}
	
}
	
	$(function(){
		getTopList();
		
		//첫번째 카테고리의 select 박스의 값을 변경하면
		$("select[name='topCategory']").change(function(){
			console.log("바꿈?", this.value);//이벤트 익명 함수 내에서의 this 란 이벤트를 일으킨 컴포넌트인 즉 select 박스를 의미
			getSubList(this.value);
		});

		$("#bt-regist").click(()=>{
		regist();
	});
});
	
	
</script>
</head>
<body>

	<div class="container-fluid">


		<div class="row">
			<div class="col-sm-4" style="background-color: lavender;">
				<h2>뉴스 기사 등록</h2>
				<form id="regist-form">

					<div class="form-group">
						<select class="form-control" name="topCategory"></select>
					</div>

					<div class="form-group">
						<select class="form-control" name="subCategory"></select>
					</div>

					<div class="form-group">
						<input type="text" class="form-control" placeholder="기사 제목" name="subject">
					</div>

					<div class="form-group">
						<input type="text" class="form-control" placeholder="작성자"
							name="author">
					</div>

					<div class="form-group">
						<textarea class="form-control" placeholder="작성해주세요" name="detail"></textarea>
					</div>

					<button type="button" class="btn btn-primary" id="bt-regist">기사 등록</button>

				</form>

			</div>
			<div class="col-sm-4" style="background-color: lavenderblush;">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>No</th>
							<th>사원명</th>
							<th>나이</th>
							<th>이메일</th>
							<th>키</th>
							<th>몸무게</th>
							<th>혈액형</th>
						</tr>
					</thead>

					<tbody id="tbody">
						<tr>
							<td colspan="7"><a href="/emp/list?currentPage=">◀</a> <a
								href="javascript:alert('이전 블럭이 없습니다.');">▶</a> <a
								href="/emp/list?currentPage="
								style="font-size: 25px; font-weight: bold; color: red;">[ ]
							</a> <a href="/emp/list?currentPage=">▶</a> <a href="javascript:alert('다음 블럭이 없습니다.');">▶</a></td>
						</tr>

						<tr>
							<td colspan="7"></td>
						</tr>
					</tbody>
				</table>

			</div>
			<div class="col-sm-4" style="background-color: lavender;">
				<h2>사원 상세 정보</h2>
				<form id="update-form">

					<!-- 수정 삭제 시 사용할 pk 값을 유저가 보지 못하게 숨겨놓기 위해.. -->
					<input type="hidden" name="empId">

					<div class="form-group">
						<input type="text" class="form-control" placeholder="사원명 입력" name="name">
					</div>

					<div class="form-group">
						<input type="text" class="form-control" placeholder="나이" name="age">
					</div>

					<div class="form-group">
						<input type="email" class="form-control" placeholder="이메일" name="email">
					</div>

					<div class="form-group">
						<input type="text" class="form-control" placeholder="키" name="height">
					</div>

					<div class="form-group">
						<input type="text" class="form-control" placeholder="몸무게" name="weight">
					</div>

					<div class="form-group">
						<select class="form-control" name="bloodType">
							<option value="">혈액형 선택</option>
							<option value="A">A형</option>
							<option value="B">B형</option>
							<option value="O">O형</option>
							<option value="AB">AB형</option>
						</select>
					</div>

					<button type="button" class="btn btn-primary" id="update-bt">수정</button>
					<button type="button" class="btn btn-primary" id="delete-bt">삭제</button>

				</form>
			</div>
		</div>
	</div>

</body>
</html>