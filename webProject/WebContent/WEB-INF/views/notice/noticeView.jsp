<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<style>
		.comment-wrapper{
			width: 1000px;
			margin: 0 auto;
		}
		.commnet-writer{
			width: 1000px;
			margin: 0 auto;
		}
		.table-warpper{
			width: 1000px;
			margin: 0 auto;
		}
		.commentList{
			width:100%;
			clear: both;
			border: 1px solid #ccc;
			border-radius: 5px;
			overflow: hidden;
		}
		
		.commentList>li{
			float:left;
			color:black;
			
		}
		
		
		td{
			width: 90%;
		}
	</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<section class="container">
		<div class="table-wrapper">
		<h1>공지</h1>
			<table class="table table-bordered">
				<tr>
					<th>제목</th>
					<td>${n.noticeTitle }</td>
				</tr>
				
				<tr>
					<th>작성자</th>
					<td>${n.noticeWriter }</td>
				</tr>
				
				<tr>
					<th>첨부파일</th>
					<td>
						<c:if test="${not empty n.filename }">
							<img src="/img/file.png" width="16px">
							<a href="javascript:fileDownload('${n.filename }', '${n.filepath }')">${n.filename }</a>
						</c:if>
					</td>
				</tr>
				
				<tr>
				<th class="con11">내용</th>
				 <td>${n.contentBr }</td>
				</tr>
				
				<tr>
					<th colspan="2" style="text-align: center">
						<c:if test="${not empty sessionScope.Member && sessionScope.Member.memberId == 'admin' }">
							<a class="btn btn-outline-primary btn sm" href="updateNoticeFrm?noticeNo=${n.noticeNo }">수정하기</a>
							<!-- href="javascript:void(0); : 클릭이벤트 취소시킴 -->
							<a class="btn btn-outline-primary btn sm" href="javascript:void(0);" onclick="deleteNotice('${n.noticeNo }');">삭제하기</a>
						</c:if>
						<a href="/noticeList?reqPage=1" class="btn btn-outline-primary btn sm">목록으로</a>
					</th>
				</tr>
			</table>
		</div>
		<!-- 로그인이 되면 생성 -->
		<c:if test="${not empty sessionScope.Member  }">
		<!-- 댓글 작성창 -->
		<div class="comment-wrapper">
			<form action="/noticeCommentInsert" method="post">
			<!-- 작성자, 게시글번호, 댓글레벨, 댓글번호 -->
			<input type="hidden" name="noticeCommentWriter" value="${sessionScope.Member.memberId }">
			<input type="hidden" name="noticeRef" value="${n.noticeNo }">
			<input type="hidden" name="noticeCommentLevel" value="1">
			<input type="hidden" name="noticeCommentRef" value="0">
				<table class="table">
					<tr>
						<td width="85%">
							<input type="text" class="form-control" name="noticeCommentContent">
						</td>
						
						<td width="15%">
							<button type="submit" class="btn btn-primary">등록</button>
						</td>
					</tr>
				</table>
				</form>
			</div>
		</c:if>
		
		<div class="comment-wrapper">
			<c:forEach items="${list }" var="nc">
			<c:if test="${nc.noticeCommentLevel eq 1 }">
				<ul class="commentList">
					<li style="width:5%; text-align:center;" >
						<span>${nc.noticeCommentWriter }</span>
					</li>
					
					<li style="width:65%">
						<span>${nc.noticeCommentContent }</span>
						<input type="text" class="form-control" name="noticeCommentContent" value="${nc.noticeCommentContent }"
						style="display:none;">
						
					</li>
					
					<li style="width:10%; text-align:center;">
						<span>${nc.noticeCommentDate }</span>
					</li>
					
					<li style="width:20%; text-align:center;">
						<c:if test="${not empty sessionScope.Member }">
							<a href="javascript:void(0)" onclick="insertComment(this,'${nc.noticeCommentNo }','${n.noticeNo }','${sessionScope.Member.memberId }')">댓글달기</a>
							<c:if test="${sessionScope.Member.memberId == nc.noticeCommentWriter }">
								<a href="javascript:void(0)" onclick="modifyComment(this, '${nc.noticeCommentNo }', '${nc.noticeRef }')">수정</a>
								<a href="javascript:void(0)" onclick="deleteComment('${nc.noticeCommentNo }', '${nc.noticeRef }')">삭제</a>
							</c:if>
						</c:if>
					</li>
				</ul>
			</c:if>
				<c:forEach items="${list }" var="ncc">
					<c:if test="${ncc.noticeCommentLevel eq 2 && nc.noticeCommentNo eq ncc.noticeCommentRef }">
						<ul class="commentList">
							<li style="width:5%; text-align: center">
								<span>└─</span>
							</li>
							
							<li style="width:10%; text-align: center">
								<span>${ncc.noticeCommentWriter }</span>
							</li>
							<li style="width:50%">
								<span>${ncc.noticeCommentContent }</span>
								<input type="text" class="form-control" name="noticeCommentContent" value="${ncc.noticeCommentContent }"
						style="display:none;">
							</li>
							
							<li style="width:10%; text-align: center">
								<span>${ncc.noticeCommentDate }</span>
							</li>
							
							<li style="width:20%; text-align:center;">
								<c:if test="${not empty sessionScope.Member && sessionScope.Member.memberId eq ncc.noticeCommentWriter }">
									<a href="javascript:void(0)" onclick="modifyComment(this, '${ncc.noticeCommentNo }', '${ncc.noticeRef }')">수정</a>
									<a href="javascript:void(0)" onclick="deleteComment('${ncc.noticeCommentNo }', '${ncc.noticeRef }')">삭제</a>
								</c:if>
							</li>
						</ul>
					</c:if>
				</c:forEach>
		</c:forEach>
	</div>
</section>
	
	<script>
		function fileDownload(filename, filepath){
			var newFilename = encodeURIComponent(filename);
			var newFilepath = encodeURIComponent(filepath);
			
			location.href="/noticeFileDownload?filename="+newFilename+"&filepath="+newFilepath;
			
		}
		
		function deleteNotice(noticeNo){
			if(confirm("삭제할래요?")){
				location.href="/deleteNotice?noticeNo="+noticeNo;
			}
		}
		function insertComment(obj,commentNo,noticeNo,memberId){
			$(obj).parent().hide();
			var $form = $("<form action='/noticeCommentInsert' method='post'></form>");
			var $ul = $("<ul class='commentList'></ul>");
			$form.append($("<input type='hidden' name='noticeCommentWriter' value='"+memberId+"'>"));
			$form.append($("<input type='hidden' name='noticeRef' value='"+noticeNo+"'>"));
			$form.append($("<input type='hidden' name='noticeCommentLevel' value='2'>"));
			$form.append($("<input type='hidden' name='noticeCommentRef' value='"+commentNo+"'>"));
			var $li1 = $("<li style='width:5%'>└─</li>");
			var $li2 = $("<li style='width:75%'></li>");
			$li2.append($("<input type='text' name='noticeCommentContent' class='form-control'>"));
			var $li3 = $("<li style='width:20%'></li>");
			$li3.append($("<button type='submit' class='btn btn-link btn-sm'>등록</button>"));
			$li3.append($("<button type='button' class='btn btn-link btn-sm' onclick='insertCancel(this)'>취소</button>"));
			$ul.append($li1).append($li2).append($li3);
			$form.append($ul);
			$(obj).parent().parent().after($form);
		}
		
		function insertCancel(obj){
			$(obj).parents('form').prev().children().last().show();
			$(obj).parents('form').remove();
		}
		
		function deleteComment(noticeCommentNo, noticeRef){
			location.href="/noticeCommentDelete?noticeCommentNo="+noticeCommentNo+"&noticeRef="+noticeRef;
		}
		
		function modifyComment(obj, noticeCommentNo, noticeRef){
			$(obj).prev().hide();
			$(obj).html('수정완료');
			$(obj).attr('onclick','modifyComplete(this, "'+noticeCommentNo+'", "'+noticeRef+'")');
			$(obj).next().html('취소');
			$(obj).next().attr('onclick','modifyCancel(this, "'+noticeCommentNo+'", "'+noticeRef+'")');
			$(obj).parent().parent().find('input').show();
			$(obj).parent().parent().find('input').prev().hide();
		}
		
		function modifyCancel(obj, noticeCommentNo, noticeRef){
			$(obj).prev().prev().show();
			$(obj).prev().html('수정');
			$(obj).prev().attr('onclick','modifyComment(this,"'+noticeCommentNo+'", "'+noticeRef+'")');
			$(obj).html('삭제');
			$(obj).attr('onclick','deleteComment("'+noticeCommentNo+'", "'+noticeRef+'")');
			$(obj).parent().parent().find('input').hide();
			$(obj).parent().parent().find('input').prev().show();
		}
		
		function modifyComplete(obj, noticeCommentNo, noticeRef){
			var $form=$("<form action='/noticeCommentUpdate' method='post'></form>");
			$form.append($("<input type='text' name='noticeCommentNo' valur='"+noticeCommentNo+"'>"));
			$form.append($("<input type='text' name='noticeRef' valur='"+noticeRef+"'>"));
			$form.append($(obj).parent().parent().find('input'));
			$('body').append($form);
			$form.submit();
			
		}
		
		
	</script>
</body>
</html>