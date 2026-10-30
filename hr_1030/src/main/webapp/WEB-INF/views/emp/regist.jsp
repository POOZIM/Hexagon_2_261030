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
	function regist(){
		$("#form1").attr({
			action:"/emp/regist",
			method:"POST"
		});
		
		$("#form1").submit();//전송
	}

	$(function(){
		//등록버튼 누르면 서버에 form 전송
		$("#bt-regist").click(()=>{
			regist();
		});
	});
</script>
</head>
<body>

	<div class="container">
		<h2>사원 등록</h2>
		<form id="form1">

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

</body>
</html>
