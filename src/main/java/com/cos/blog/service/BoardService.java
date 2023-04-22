package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;

@Transactional
@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	public void 글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);			
	}
	
	public Page<Board> 글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	public Board 글상세보기(int id) {
		return boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을수 없습니다.");
		});		
	}
	
	public void 글삭제하기(int id) {
		 boardRepository.deleteById(id);
	}
	
	public void 글수정하기(int id, Board board) {
		System.out.println("update  id" + id);
		System.out.println("update  board" + board);
		Board newboard = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을수 없습니다.");
				});		
		newboard.setTitle(board.getTitle());
		newboard.setContent(board.getContent());
		// 해당 함수로 종료시 (Service가 종료될때) 트렌젹션이 종료
	}
	
	public void 덧글쓰기(User user, int boardid, Reply requestreply )  {
		System.out.println("덧글쓰기 boardid:-=====>"  + boardid);
		Board board = boardRepository.findById(boardid).orElseThrow(()->{
			return new IllegalArgumentException("덧글 쓰기 실패 : 아이디를 찾을수 없습니다.");
		});		
		System.out.println("덧글쓰기 board:"  + board);
		requestreply.setUser(user);
		requestreply.setBoard(board);
		replyRepository.save(requestreply);
	}

}
