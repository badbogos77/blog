package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.Board;
import com.cos.blog.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@GetMapping({"","/"})
	public String index(@AuthenticationPrincipal PrincipalDetail principal, Model model, @PageableDefault(size=2) Pageable pageable) {
		//System.out.println(principal.getUsername());		
		Page<Board> aaa = boardService.글목록(pageable);
		model.addAttribute("boards",aaa );		
		return "index";
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {		
		return "/board/saveForm";
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		Board board = boardService.글상세보기(id);
		model.addAttribute("board", board );
		return "/board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateFrom(@PathVariable int id, Model model) {
		Board board = boardService.글상세보기(id);
		System.out.println("updateForm board " + board);
		model.addAttribute("board", board);
		return "board/updateForm";
	}
}
