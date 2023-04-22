package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Board;

public interface BoardRepository  extends JpaRepository<Board, Integer>{
	

	
//JPA Naming 전략
// SELECT * FROM USER WHERE USERNAM=? AND PASSWORD =?)
//	User findByUsernameAndPassword(String username, String password);
	
//	@Query(value="SELELCT * FROM USER WHERE USERNAME=?1 AND PASSWORD=?2", nativeQuery=true)
//	User login(String username, String password);

}
