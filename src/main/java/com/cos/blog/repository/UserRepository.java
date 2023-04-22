package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

public interface UserRepository  extends JpaRepository<User, Integer>{
	
	Optional<User> findByUsername(String username);
	
//JPA Naming 전략
// SELECT * FROM USER WHERE USERNAM=? AND PASSWORD =?)
//	User findByUsernameAndPassword(String username, String password);
	
//	@Query(value="SELELCT * FROM USER WHERE USERNAME=?1 AND PASSWORD=?2", nativeQuery=true)
//	User login(String username, String password);

}
