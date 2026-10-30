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
	/*
	1) 순수 XMLHttpRequest
	2) JQuery 에서 지원하는 비동기 - XMLHttpRequest 사용법을 Jquery 로 단순화 시킨것
	3) Promise 에서 ㅣㅂ동기
	4) fetch 에서 비동기
	5) async/await
	*/
	function regist(){
		//비동기 방식으로 POST 요청을 시도하되 순수 XMLHttpRequest를 사용하면 코드량이 많아지고 복잡함 JQuery 를 사용하여 좀 더 단순화 시켜줌
		$.ajax({
			url:"/rest/emps",
			type:"POST",
			//html 의 form 태그로 post 전송을 시도하면, 내부적으로 
			//contentType(전송할 데이터 유형)이 자동으로 브라우저에 의해 "application/x-www-form-urlencoded"; charset=UTF-8
			//우리의 경우 지금 비동기로 요청하는 바람에, 더이상 자동으로 설정되지 않는다 따라서 우리가 직접 붙인다
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			data:{
				name:$("input[name='name']").val(),
				age:$("input[name='age']").val(),
				email	:$("input[name='email']").val(),
				height:$("input[name='height']").val(),
				weight:$("input[name='weight']").val(),
				bloodType:$("select[name='bloodType']").val()
			},
			success:function(result, status, xhr){
				//서버에서 성공적인 응답 정보가 전송되면 이 익명함수 동작함
				//result 서버에서 보내온 데이터(우리의 경우 스트링)
				//status 서버에서 보내온 HTTP 상태코드(성공 200, 에러 500)
				//xhr : XMLHttpRequest
				console.log("서버에서 보내온 데이터는", result);
				
				getEmpList();
			}
		});
	}
	/*--------------------------
		사원 한건 가져오기
	-----------------------*/
	function getEmp(empId) {
		$.ajax({
			url:"/rest/emps/"+empId,
			type:"GET",
			success:function(result, status, xhr){
				console.log(result);
				//우측 상세보기 영역에 사원정보 채우기!!
				$("#update-form input[name='name']").val(result.ename);//사원명 채우기
				$("#update-form input[name='age']").val(result.age);//나이 채우기
				$("#update-form input[name='email']").val(result.email);//이메일 채우기
				$("#update-form input[name='height']").val(result.bio.height);//신장 채우기
				$("#update-form input[name='weight']").val(result.bio.weight);//몸무게 채우기
				$("#update-form select[name='bloodType']").val(result.bio.bloodType);//혈액형 채우기
				
				$("#update-form input[name='empId']").val(result.empId);//hidden에 채우기
				//alert(result.empId);
			}
		});
	}

	/*--------------------------
		사원 목록 시각화 처리
	--------------------------*/
	function empPrint(empList){
		//$("#tbody").html("<h3>여기에 회원목록나올예정</h3>");
		
		let tag="";//여기에 태그를 누적시킬 예정
		
		for(let i=0 ;i<empList.length;i++){
            let emp = empList[i];

            tag+="<tr>";
            tag+="<td>1</td>";
            tag+="<td><a href='#' onClick='getEmp("+emp.empId+")'>"+emp.ename+"</a></td>";
            tag+="<td>"+emp.age+"</td>";
            tag+="<td>"+emp.email+"</td>";
            tag+="<td>"+emp.bio.height+"</td>";
            tag+="<td>"+emp.bio.weight+"</td>";
            tag+="<td>"+emp.bio.bloodType+"</td>";
            tag+="</tr>";
        }
		$("#tbody").html(tag);
	}
	
	/*--------------------------
		사원 목록 비동기 요청
	-----------------------------*/
	function getEmpList(){
		
		//XMLHttpRequest 를 쓰기 쉽게 JQuery 로 숨겨놓았기 때문에 개발자가 코드를 단순화 시켜서 개발할 수 있다
		$.ajax({
			url:"/rest/emps",
			type:"GET",
			success:function(result, status, xhr){
				console.log(result);
				//서버에서 받아온 데이터가 문자열인지 js의 객체인지
				//만일 객체라면, 객체.속성으로 접근이 가능
				//서버에서 보내온 String 데이터가 만일 JS의 객체 표기법을 준수했다면 js에서는
				//문자열 객체로 변환 가능
				//let empList = JSON.parse(result);//JSON표기를 준수한 문자열을 실제 객체로 변환
				console.log("첫번째 사원의 메일은", result[0].email);
				empPrint(result);
				
		
			}
		});
	}
	
	//사원 정보 수정하기
	function update(){
		if(confirm("수정하시겠어요?")){
			//HTML의 form 태그는 post/get 만 지원하므로 PUT 방식으로 전송 불가
			//굳이 form 태그를 통해 전송하지 말고 json문자열로 데이터를 전송하자!!
			let jsonData={
				name:$("#update-form input[name='name']").val(),
				age:$("#update-form input[name='age']").val(),
				email	:$("#update-form input[name='email']").val(),
				height:$("#update-form input[name='height']").val(),
				weight:$("#update-form input[name='weight']").val(),
				bloodType:$("#update-form select[name='bloodType']").val(),
				empId:$("#update-form input[name='empId']").val()
			};
			
			$.ajax({
				url:"/rest/emps/45"+$("#update-form input[name='empId']").val(),
				type:"PUT",
				contentType:"application/json; charset=UTF-8",
				//js 객체를 JSON 문자열로 자동변환해준다 서버에 전송할때는 문자열로만 보낼수 있기 때문에
				data: JSON.stringify(jsonData),
				success:function(result, status, xhr){
					alert("수정 완료");
				},
				error:function(xhr, status, error){
					console.error("수정 실패", error);
				}
			});
		}
	}

	$(function(){
		
		$("#bt-regist").click(()=>{
			regist();
		});
		
		$("#update-bt").click(()=>{
			update();
		});
		
		getEmpList();
	});
</script>
</head>
<body>

	<div class="container-fluid">
		<%
			SecurityContext securityContext=(SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");
		
			Admin admin=null;
			if(securityContext !=null){
				Authentication authentication = securityContext.getAuthentication();
				
				AdminDetails adminDetails = (AdminDetails)authentication.getPrincipal();//AdminDetails 꺼내기
				admin = adminDetails.getAdmin();
			}
		%>
		<h1>환영함둥!<%=admin.getName() %>님</h1>
		
		<div class="row">
			<div class="col-sm-4" style="background-color: lavender;">
				<h2>사원 등록</h2>
				<form id="regist-form">

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
							<option value="AB">AB형</option>
							<option value="O">O형</option>
						</select>
					</div>

					<button type="button" class="btn btn-primary" id="bt-regist">Submit</button>
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
						<!-- 이 영역에 서버에서 가져온 데이터를 동적(=실행시 retime)으로 출력하겠음 -->

						<tr>
							<td colspan="7"><a href="javascript:alert('이전 블럭이 없습니다');">◀</a>
								<a href="/emp/list">▶</a> <a href="javascript:alert('다음 블럭이 없습니다');"></a></td>
						</tr>

						<tr>
							<td colspan="7">
								<button onClick="location.href='/emp/registform';">사원등록</button>
							</td>
						</tr>
					</tbody>
				</table>

			</div>
			<div class="col-sm-4" style="background-color: lavender;">
				<h2>사원 상세 정보</h2>
				<form id="update-form">
					
					<!-- 수정, 삭제 시 사용할 pk값을 유저가 보지 못하게 숨겨놓기 위해 -->
					<input type="hidden" name="empId">

					<div class="form-group">
						<input type="text" class="form-control" placeholder="사원명 입력"
							name="name">
					</div>

					<div class="form-group">
						<input type="text" class="form-control" placeholder="나이"
							name="age">
					</div>

					<div class="form-group">
						<input type="email" class="form-control" placeholder="이메일"
							name="email">
					</div>

					<div class="form-group">
						<input type="text" class="form-control" placeholder="키"
							name="height">
					</div>

					<div class="form-group">
						<input type="text" class="form-control" placeholder="몸무게"
							name="weight">
					</div>

					<div class="form-group">
						<select class="form-control" name="bloodType">
							<option value="">혈액형 선택</option>
							<option value="A">A형</option>
							<option value="B">B형</option>
							<option value="AB">AB형</option>
							<option value="O">O형</option>
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
