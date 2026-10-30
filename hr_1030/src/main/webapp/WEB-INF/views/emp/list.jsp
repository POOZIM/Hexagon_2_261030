<%@page import="com.hexagon.springmvc.domain.Emp"%>
<%@page import="com.hexagon.springmvc.common.web.Pager"%>
<%@page import="com.hexagon.springmvc.domain.Bio"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	//Paging 처리란? -> 많은 수의 데이터를 분할하여 화면에 보여주는 기법
	Pager pager=(Pager)request.getAttribute("pager");
	List<Emp> empList = pager.getList();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>

	<div class="container">
		<h2>Hover Rows</h2>
		<p>The .table-hover class enables a hover state on table rows:</p>
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
			<tbody>
				<%
					int num = pager.getNum(); 
					int offset = pager.getOffset();//가져온 리스트의 페이지당 시작 index
				%>
				<%//모든 레코드를 가져온게 아니라 필요한 만큼 10개만 가져온 상태이므로, List 안에 들어있는 모든 걸 뿌리면 됨 %>
				<%for(Emp emp : empList){ %>
				<%if(num<1)break; %>
				<tr>
					<td><%=num-- %></td>
					<td><%=emp.getEname() %></td>
					<td><%=emp.getAge() %></td>
					<td><%=emp.getEmail() %></td>
					<td><%=emp.getBio().getHeight() %></td>
					<td><%=emp.getBio().getWeight() %></td>
					<td><%=emp.getBio().getBloodType() %></td>
				</tr>
				<%} %>
				<tr>
					<td colspan="7">
						<%if(pager.getFirstPage()-1 > 0){ %>
							<a href="/emp/list?currentPage=<%=pager.getFirstPage()-1 %>">◀</a>
						<%}else{ %>
							<a href="javascript:alert('고만눌러');">◀</a>
						<%} %>
						<%for(int i=pager.getFirstPage();i<=pager.getLastPage();i++){ %>
							<%if(i>pager.getTotalPage())break; //페이지를 출력하는 반복문의 i가 내가 가진 총 페이지수를 넘어서면 반복문 빠져나와라 %>
							<a href="/emp/list?currentPage=<%=i %>" <%if(i==pager.getCurrentPage()){ %> style="font-size: 25px; font-weight: bold; color: red "<%}%>>[<%=i %>]</a>	
						<%} %>
						
						<%if(pager.getLastPage()+1 < pager.getTotalPage()){//다음 페이지가 총페이지 수보다 작으면 %>
							<a href="/emp/list?currentPage=<%=pager.getLastPage()+1 %>">▶</a>
						<%}else{ %>
							<a href="javascript:alert('고만눌러');">▶</a>
						<%} %>
					</td>
				</tr>
				<tr>
					<td colspan="7">
						<button onClick="location.href='/emp/registform';">사원등록</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

</body>
</html>
