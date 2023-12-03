package com.mycom.mytrip.comment.service;

import java.util.List;

import com.mycom.mytrip.comment.dto.CommentDto;

public interface CommentService {
	List<CommentDto> list(int boardId);
	int insert(CommentDto dto);
	int update(CommentDto dto);
	int delete(int commentId);
}
