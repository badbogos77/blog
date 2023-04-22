<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<div class="container">
	<form >
		<div class="form-group">
			<label for="title">Title</label> <input type="text"  name="title" 	class="form-control" placeholder="Enter Title" id="title"  value="${board.title}">
		</div>		
		<div class="form-group">
		  <label for="content">Content</label>
		  <textarea class="form-control summernote" rows="5" id="content" >${board.content}</textarea>
		</div>		
		<input type="hidden" value="${board.id }" id="id">
	</form>
	<button id="btn-update" class="btn btn-primary">글쓰기수정하기</button>
</div>
<script>
      $('.summernote').summernote({       
        tabsize: 2,
        height: 300
      });
    </script>
    <script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>

