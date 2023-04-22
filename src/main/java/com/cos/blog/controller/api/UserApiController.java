package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import com.cos.blog.service.UserService;


@RestController
public class UserApiController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;	
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		userService.회원가입(user);
		return new  ResponseDto<Integer>(HttpStatus.OK,1);
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User requestuser) {
		System.out.println("requestuser" + requestuser);
		User user = userService.회원수정(requestuser);
	
		return new  ResponseDto<Integer>(HttpStatus.OK,1);
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
