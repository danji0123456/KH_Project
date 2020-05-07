<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

	<style>
		section.container{
		width:1000px;
		margin: 0 auto;
		}
		
		
		table th, table td{
			text-align:center;
		}
		
		#searchbox{
			margin: 0 auto;
			width:500px;
			text-align: center;
		}
		select[name=type]{
			display: inline-block;
			width: 100px;
			height:30px;
			font-size: 0.8em;
		}
		input[name=keyword]{
			display: inline-block;
			width: 100px;
			height:30px;
			font-size: 0.8em;
		}
	</style>
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<section class="container">
		<table class="table table-hover">
			<tr>
				<th>ID</th>
				<th>이름</th>
				<th>나이</th>
				<th>EMAIL</th>
				<th>전화번호</th>
				<th>주소</th>
				<th>가입일</th>
				<th>제거</th>
			</tr>
			
			<c:forEach items="${list }" var="m" >
				<tr>
					<td>${m.memberId }</td>
					<td>${m.memberName }</td>
					<td>${m.age }</td>
					<td>${m.email }</td>
					<td>${m.phone }</td>
					<td>${m.address }</td>
					<td>${m.enrollDate }</td>
					<td>
						<button class="btn btn-outline-info btn-sm" onclick="location.href='/adminDelMember?memberId=${m.memberId }'">제거</button>
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<div id="searchBox">
			<form action="/searchKeyword">
				<select name="type" class="form-control">
					<option value="memberId">아이디</option>
					<option value="memberName">이름</option>
				</select>
				<input type="text" class="form-control" name="keyword">
				<button type="submit" class="btn btn-outline-secondary btn-sm">조회</button>
			</form>
		</div>
	</section>
		
	<script>
	function deleteMember(memberId){
		if(confirm("제거하시겠습니까?")){
			location.href="/adminDelMember?memberId="+memberId;
		}
	}
	</script>
	
</body>
</html>