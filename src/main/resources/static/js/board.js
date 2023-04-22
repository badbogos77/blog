let index = {
	init: function() {	
		$("#btn-save").on("click", ()=>{			
			this.save();
		});
		$("#btn-delete").on("click", ()=>{			
			this.deleteById();
		});		
		$("#btn-update").on("click", ()=>{			
			this.update();
		});	
		$("#btn-reply-save").on("click", ()=>{			
			this.replySave();
		});	
	},
	
	save: function() {
		let data ={
			title: $("#title").val(),
			content: $("#content").val()
		};
		console.log(data)
		
		// 비동기 처리
		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("글쓰기가 완료되었습니다.");
			location.href="/"
		}).fail(function(error) {
			alert(JSON.stringify(error));
		}); // ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
	},
	deleteById: function() {
		var id = $("#id").text();
		console.log("id===",id);
		// 비동기 처리
		$.ajax({
			type: "DELETE",
			url: "/api/board/"+id,		
			dataType: "json"
		}).done(function(resp) {
			alert("글이 삭제되었습니다..");
			location.href="/"
		}).fail(function(error) {
			alert(JSON.stringify(error));
		}); // ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
	},
	update: function() {
		let id = $("#id").val();
		
		let data ={
			title: $("#title").val(),
			content: $("#content").val()
		};
		console.log(data)
		console.log(id)
		// 비동기 처리
		$.ajax({
			type: "PUT",
			url: "/api/board/"+id,		
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("글이 수정되었습니다..");
			location.href="/"
		}).fail(function(error) {
			alert(JSON.stringify(error));
		}); // ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
	},
	replySave: function() {
		let boardid = $("#boardid").val();
		let data ={			
			content: $("#reply-content").val()
		};
		console.log(data)
		alert(JSON.stringify(data))
		// 비동기 처리
		$.ajax({
			type: "POST",
			url: `/api/board/${boardid}/reply`,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("덧글쓰기가 완료되었습니다.");
			location.href=`/board/${boardid}`;
		}).fail(function(error) {
			alert(JSON.stringify(error));
		}); // ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
	},				
}	

index.init();