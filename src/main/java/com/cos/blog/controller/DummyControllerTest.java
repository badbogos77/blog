package com.cos.blog.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String DeleteUser(@PathVariable int id) {
		try
		{
			userRepository.deleteById(id);
		}
		catch (Exception e) {
			return "삭제가 실패하였습니다";
		}
		
		return "삭제되었습니다";
	}
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		
		User user = userRepository.findById(id).orElseThrow(()-> {
			return new IllegalArgumentException("유저 정보가 없습니다.");
		});
		
		user.setUsername(requestUser.getUsername());
		user.setEmail(requestUser.getEmail());
		//userRepository.save(user);  // 더티체킹
		return user;
	}
		
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> user =userRepository.findAll(pageable);
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		User user = userRepository.findById(id).orElseThrow(()-> {
			return new IllegalArgumentException("해당사용자가 없습니다.");
		});
		return user;		
	}
	
	@PostMapping("/dummy/join")
	public String join(@RequestBody User user) {
		System.out.println("username:" + user.getUsername());
		System.out.println("password:" +  user.getPassword());
		System.out.println("email:" + user.getEmail());
		
		user.setRole(RoleType.USER);
		
		userRepository.save(user);
		return "회원가입이 완료되었습니다";
	}

}
