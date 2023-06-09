<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

<div class="container">
<button class="btn btn-secondary"  onclick="history.back()">돌아가기</button>
  <c:if test="${board.user.username == principal.username }">
   <a href="/board/${board.id}/updateForm" class="btn btn-warning" >수정</a>
    <button class="btn btn-danger" id="btn-delete">삭제</button>
  </c:if>  
    <br><br>
    <div>
    글번호 : <span id="id"><i>${board.id}</i></span>&nbsp;
    작성자 : <span><i>${board.user.username }</i></span>
    <br>
    </div>
		<div class="form-group">
			<label for="title">Title</label>
			<h3>${board.title }</h3>
		</div>		
		<hr/>
		<div class="form-group">
			  <label for="content">Content</label>
			  <div>${board.content } </div>		  
		</div>	
		<hr/>
		<form>
			<div class="card">
					<input type="hidden" id="boardid" value="${board.id}">
					<div class="card-body"><textarea  id="reply-content" rows="1"  class="form-control""></textarea></div>
					<div class="card-footer"><button type="button" id="btn-reply-save" class="btn btn-primary">등록</button></div>
			</div>		
		</form>		
		<br />	
		<div class="card">
				<div class="card-header">댓글 리스트</div>
				<ul id="reply--box" class="list-group">
				<c:forEach var="reply"  items="${board.replys}">
					<il id="reply--1" class="list-group-item  d-flex justify-content-between">
						 	<div>${reply.content}</div>
						 	<div class="d-flex">
						 		<div class="d-flex">작성자 : ${reply.user.username} &nbsp;<button class="badge">삭제</button></div>
						 	</div>
					</il>
					</c:forEach>
				</ul>
		</div>	
</div>
 <script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>

