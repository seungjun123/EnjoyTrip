package com.mycom.mytrip.board.user.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.mytrip.board.user.dto.BoardDto;
import com.mycom.mytrip.board.user.dto.BoardParamDto;
import com.mycom.mytrip.board.user.dto.BoardResultDto;
import com.mycom.mytrip.board.user.dto.UserBoardDto;

public interface UserBoardService {
	List<UserBoardDto> list(); // 유저 게시판 목록 조회
	UserBoardDto detail(String userBoardId); // 유저 게시판 상세 조회
	int insert(UserBoardDto boardDto); // 유저 게시물 등록
	int update(UserBoardDto boardDto); // 유저 게시물 수정
	int delete(String userBoardId); // 유저 게시물 삭제
	
	// =================== 새롭게 추가한 부분
	BoardResultDto boardDetail(BoardParamDto boardParamDto);
    
    BoardResultDto boardDelete(int boardId);
    
    BoardResultDto boardUpdate(BoardDto dto, MultipartHttpServletRequest request);

    BoardResultDto boardInsert(BoardDto dto, MultipartHttpServletRequest request);

    BoardResultDto boardList(BoardParamDto boardParamDto);
    BoardResultDto myBoardList(BoardParamDto boardParamDto);
    //int boardListTotalCount();    
    BoardResultDto boardListSearchWord(BoardParamDto boardParamDto);
    //int boardListSearchWordTotalCount(boardParamDto boardParamDto);
    
    BoardResultDto boardListSearchWordAvg(BoardParamDto boardParamDto);
}
