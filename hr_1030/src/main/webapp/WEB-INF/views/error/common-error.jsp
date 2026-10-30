<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body style="background: yellow;">
	<h3>이용에 불편을 드려서 죄송</h3>
	
	<%=request.getAttribute("errorMessage") %>
	
</body>
</html>