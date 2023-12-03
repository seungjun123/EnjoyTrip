package com.mycom.mytrip.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.mytrip.comment.dto.CommentDto;

@Mapper
public interface CommentDao {
	List<CommentDto> list(int boardId);
	int insert(CommentDto dto);
	int update(CommentDto dto);
	int delete(int commentId);
}
