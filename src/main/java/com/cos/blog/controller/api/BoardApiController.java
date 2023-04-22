package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.UserRepository;
import com.cos.blog.service.BoardService;

@RestController
public class BoardApiController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private BoardService boardService;	
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		System.out.println("api/board 글쓰기" + board);
		boardService.글쓰기(board, principal.getUser());
		return new  ResponseDto<Integer>(HttpStatus.OK,1);
	}
	
	@DeleteMapping("api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id)
	{
		System.out.println("delete id" + id);
		boardService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK, 1);
	}
	
	@PutMapping("api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
		System.out.println("update board" + board);
		boardService.글수정하기(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK, 1);
	}	
	
	@PostMapping("/api/board/{boardid}/reply")
	public ResponseDto<Integer> reply(@PathVariable int boardid, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal) {
		System.out.println("api/board/{id}/덧글쓰기" + boardid);
		boardService.덧글쓰기(principal.getUser(), boardid, reply);
		return new  ResponseDto<Integer>(HttpStatus.OK,1);
	}
	
	@GetMapping("/test/board/1")
	public Board getBoard()
	{
		return boardRepository.findById(1).get();
	}
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
//		
//	   User principal = userService.로그인(user);	  
//	   if (principal != null) {
//		   session.setAttribute("principal", principal);
//	   }
//		return new ResponseDto<Integer>(HttpStatus.OK, 1);
//	}
}
