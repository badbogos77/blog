let index = {
	init: function() {	
		$("#btn-save").on("click", ()=>{			
			this.save();
		});	
		$("#btn-update").on("click", ()=>{			
			this.update();
		});		
	},
	
	save: function() {
		let data ={
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		console.log(data)
		
		// 비동기 처리
		$.ajax({
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("회원가입이 완료되었습니다.");
			location.href="/"
		}).fail(function(error) {
			alert(JSON.stringify(error));
		}); // ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
	},
	update: function() {
		let data ={
			id: $("#id").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		console.log(data)
		
		// 비동기 처리
		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			alert("회원정보가 수정 완료되었습니다.");
			location.href="/"
		}).fail(function(error) {
			alert(JSON.stringify(error));
		}); // ajax통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!
	}
}	

index.init();