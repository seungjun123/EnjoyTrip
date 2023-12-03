package com.mycom.mytrip.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.mytrip.comment.dao.CommentDao;
import com.mycom.mytrip.comment.dto.CommentDto;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentDao commentDao;
	
	@Override
	public List<CommentDto> list(int boardId) {
		return commentDao.list(boardId);
	}
	
	@Override
	public int insert(CommentDto dto) {
		return commentDao.insert(dto);
	}
	
	@Override
	public int update(CommentDto dto) {
		return commentDao.update(dto);
	}
	
	@Override
	public int delete(int commentId) {
		return commentDao.delete(commentId);
	}
}
