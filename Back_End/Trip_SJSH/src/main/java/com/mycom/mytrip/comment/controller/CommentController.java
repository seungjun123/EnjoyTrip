package com.mycom.mytrip.comment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.mytrip.comment.dto.CommentDto;
import com.mycom.mytrip.comment.service.CommentService;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	// 댓글 목록 불러오기
	@GetMapping(value = "{boardId}")
	public List<CommentDto> list(@PathVariable String boardId) {
		System.out.println(boardId);
		return commentService.list(Integer.parseInt(boardId)); 
	}
	
	// 댓글 등록
	@PostMapping(value = "")
	public int insert(@RequestBody CommentDto dto) {
		return commentService.insert(dto); 
	}
	
	// 댓글 수정
	@PutMapping(value = "")
	public int update(@RequestBody CommentDto dto) {
		System.out.println(dto);
		return commentService.update(dto); 
	}

	// 댓글 삭제
	@DeleteMapping(value = "{boardId}")
	public int update(@PathVariable String boardId) {
		return commentService.delete(Integer.parseInt(boardId));
	}
}
