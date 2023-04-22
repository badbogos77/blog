package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Transactional(readOnly=true)
	public User 회원확인(String username) {
		return userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
	}
	
	@Transactional
	public void 회원가입(User user) {
		System.out.println("회원가입 service user " + user);
		String rawPassword = user.getPassword();
		String endPassword = encoder.encode(rawPassword);
		user.setPassword(endPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);			
	}
	
	@Transactional
	public User 회원수정(User user) {
		User newuser = userRepository.findById(user.getId()).orElseThrow(()-> {
			return new IllegalArgumentException("회원 수정 실패 : 아이디를 찾을수 없습니다.");
		});		
		if (newuser.getOauth() == null || newuser.getOauth().equals("")) 
		{
			String encpassword=  encoder.encode(user.getPassword());
			newuser.setPassword(encpassword);
			newuser.setEmail(user.getEmail());
		}
		
		System.out.println("회원수정" + newuser);
		
		// 세션을 강제로 변경 시켜 줌
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(newuser.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return newuser;
	}
	
//	@Transactional(readOnly=true) //SELECT할 트랜젹선 시작, 서비스 종료시에 트랜젹선 종료
//	public User 로그인(User user) {
//		 return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());		
//	}

}
