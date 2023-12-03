package com.mycom.mytrip.board.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycom.mytrip.board.user.dto.BoardDto;
import com.mycom.mytrip.board.user.dto.BoardFileDto;
import com.mycom.mytrip.board.user.dto.BoardParamDto;
import com.mycom.mytrip.board.user.dto.BoardResultDto;
import com.mycom.mytrip.board.user.dto.UserBoardDto;

@Mapper
public interface UserBoardDao {
	List<UserBoardDto> list(); // 유저 게시판 목록 조회
	UserBoardDto detail(String userBoardId); // 유저 게시판 상세 조회
	int insert(UserBoardDto boardDto); // 유저 게시물 등록
	int update(UserBoardDto boardDto); // 유저 게시물 수정
	int delete(String userBoardId); // 유저 게시물 삭제
	
	// ================= 새로 추가한 부분 
	// 게시글 한 개 + 딸린 파일들
    BoardDto boardDetail(BoardParamDto boardParamDto);
    List<BoardFileDto> boardDetailFileList(int boardId);
   
   // map - Dto
    int boardUserReadCount(BoardParamDto boardParamDto); 
   
   // map - @param
    int boardUserReadInsert(
           @Param("boardId") int boardId, 
           @Param("userSeq") int userSeq ); 
   
    int boardReadCountUpdate(int boardId);
   
   
    int boardDelete(int boardId);    
    int boardFileDelete(int boardId);
    List<String> boardFileUrlDeleteList(int boardId);
    int boardReadCountDelete(int boardId);
   
    int boardInsert(BoardDto dto);
    int boardFileInsert(BoardFileDto dto);
   
    List<BoardDto> boardList(BoardParamDto boardParamDto);
    List<BoardDto> myBoardList(BoardParamDto boardParamDto);
    int boardListTotalCount();
   
    List<BoardDto> boardListSearchWord(BoardParamDto boardParamDto);
    int boardListSearchWordTotalCount(BoardParamDto boardParamDto);
   

    int boardUpdate(BoardDto dto);
    
    BoardResultDto boardListSearchWordAvg(BoardParamDto boardParamDto);
	
}
