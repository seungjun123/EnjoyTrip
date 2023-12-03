package com.mycom.mytrip.board.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.mytrip.board.user.dto.BoardDto;
import com.mycom.mytrip.board.user.dto.BoardParamDto;
import com.mycom.mytrip.board.user.dto.BoardResultDto;
import com.mycom.mytrip.board.user.dto.UserBoardDto;
import com.mycom.mytrip.board.user.service.UserBoardService;
import com.mycom.mytrip.users.dto.UserDto;

@RestController
public class UserBoardController {
	
	@Autowired
	UserBoardService userBoardService;
		
	// 유저 게시판 목록 조회
	@GetMapping(value = "/userBoard")
    public List<UserBoardDto> userBoardList() {
		System.out.println("userBoardList");
		List<UserBoardDto> list = userBoardService.list();
		for(UserBoardDto dto : list)
			System.out.println(dto);
        return list;
    }
	
	// 유저 게시판 상세 조회
	@GetMapping(value = "/userBoard/{userBoardId}")
    public UserBoardDto userBoardDetail(@PathVariable String userBoardId) {
		System.out.println("userBoardDetail");
		UserBoardDto dto = userBoardService.detail(userBoardId);
		System.out.println(dto);
        return dto;
    }
	
	// 유저 게시물 등록
	@PostMapping(value = "/userBoard")
    public Map<String, String> userBoardInsert(UserBoardDto userBoardDto) {
		System.out.println("userBoardInsert");
		Map<String, String> map = new HashMap<>();
		System.out.println(userBoardDto);
		int ret = userBoardService.insert(userBoardDto);
		
		if (ret == 1) {
			map.put("result", "succcess");
		} else {
			map.put("result", "fail");
		}
        return map;
    }
	
	// 유저 게시물 수정
	@PutMapping(value = "/userBoard/{boardId}")
	public Map<String, String> userBoardUpdate(@ModelAttribute UserBoardDto userBoardDto, @PathVariable String boardId) {
		System.out.println("userBoardUpdate");
		Map<String, String> map = new HashMap<>();
		
		int ret = userBoardService.update(userBoardDto);
		if (ret == 1) {
			map.put("result", "succcess");
		} else {
			map.put("result", "fail");
		}
		return map;
	}
	
	// 유저 게시물 삭제
	@DeleteMapping(value = "/userBoard/{userBoardId}")
    public Map<String, String> userBoardDelete(@PathVariable String userBoardId) {
		System.out.println("userBoardDelete");
		Map<String, String> map = new HashMap<>();
		int ret = userBoardService.delete(userBoardId);
		if (ret == 1) {
			map.put("result", "succcess");
		} else {
			map.put("result", "fail");
		}
		return map;
    }
	
	
	// ======================= 새롭게 추가한 부분
	@GetMapping(value="/boards")
    public BoardResultDto boardList(BoardParamDto boardParamDto){
        
        BoardResultDto boardResultDto;

        if( boardParamDto.getSearchWord().isEmpty() ) {
            boardResultDto = userBoardService.boardList(boardParamDto);
        }else {
            boardResultDto = userBoardService.boardListSearchWord(boardParamDto);
        }
        
        return boardResultDto;
    }
	
	@GetMapping(value="/myBoards")
    public BoardResultDto myBoardList(BoardParamDto boardParamDto){
        System.out.println(boardParamDto);
        BoardResultDto boardResultDto;

        if( boardParamDto.getSearchWord().isEmpty() ) {
            boardResultDto = userBoardService.myBoardList(boardParamDto);
        }else {
            boardResultDto = userBoardService.boardListSearchWord(boardParamDto);
        }
        
        return boardResultDto;
    }

    
    @GetMapping(value="/boards/{boardId}")
    public BoardResultDto boardDetail(@PathVariable int boardId, HttpSession session){

        BoardParamDto boardParamDto = new BoardParamDto();
        boardParamDto.setUserSeq( ((UserDto) session.getAttribute("userInfo")).getUserSeq());
        
        //System.out.println("session 에 잘 들어가나 확인");
        //System.out.println(((UserDto) session.getAttribute("userInfo")).getUserSeq());
        boardParamDto.setBoardId(boardId);

        BoardResultDto boardResultDto = userBoardService.boardDetail(boardParamDto);
        // 게시글 작성자와 현 사용자가 동일
        if( ((UserDto) session.getAttribute("userInfo")).getUserSeq() == boardResultDto.getDto().getUserSeq() ) {
            boardResultDto.getDto().setSameUser(true);
        }                
                
        return boardResultDto;     
    }
    
    @PostMapping(value="/boards")
    public BoardResultDto boardInsert(
            BoardDto boardDto, 
            MultipartHttpServletRequest request) {
        
        boardDto.setUserSeq( ((UserDto) request.getSession().getAttribute("userInfo")).getUserSeq());
        BoardResultDto boardResultDto = userBoardService.boardInsert(boardDto, request);
        
        return boardResultDto;     
    }
    
    // PUT + multipart/form-data (X)
    // In RESTful,
    // PUT & DELETE methods are defined to be idempotent
    
    @PostMapping(value="/boards/{boardId}") 
    public BoardResultDto boardUpdate(
            BoardDto boardDto, 
            MultipartHttpServletRequest request){
        boardDto.setUserSeq( ((UserDto) request.getSession().getAttribute("userInfo")).getUserSeq());
        BoardResultDto boardResultDto = userBoardService.boardUpdate(boardDto, request);

        return boardResultDto;        
    }
    
    @DeleteMapping(value="/boards/{boardId}") 
    public BoardResultDto boardDelete(@PathVariable(value="boardId") int boardId){
        BoardResultDto boardResultDto = userBoardService.boardDelete(boardId);
        
        return boardResultDto;         
    }
	
    @GetMapping(value="/boards/avg")
    public BoardResultDto boardListSearchWordAvg(BoardParamDto boardParamDto){
        
        return userBoardService.boardListSearchWordAvg(boardParamDto);
    }
    
    
}
