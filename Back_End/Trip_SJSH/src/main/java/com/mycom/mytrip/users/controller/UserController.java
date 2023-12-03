package com.mycom.mytrip.users.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.mytrip.board.user.dto.BoardDto;
import com.mycom.mytrip.board.user.dto.BoardParamDto;
import com.mycom.mytrip.board.user.dto.BoardResultDto;
import com.mycom.mytrip.users.dto.FavoriteDto;
import com.mycom.mytrip.users.dto.UserDto;
import com.mycom.mytrip.users.dto.UserParamDto;
import com.mycom.mytrip.users.dto.UserResultDto;
import com.mycom.mytrip.users.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService service;

	// 유저 개인 정보 조회
	@GetMapping(value = "/{userSeq}")
	public UserDto userDetail(@PathVariable String userSeq) {
		UserDto dto = service.userDetail(Integer.parseInt(userSeq));
		System.out.println(dto);
		return dto;
	}
	 
	// 유저 등록
	@PostMapping()
	public Map<String, String> register(@RequestBody UserDto userDto){
		System.out.println(userDto);
		Map<String,String> response = new HashMap<>();
		int ret = service.userInsert(userDto);
		
		if(ret == 1) {
			response.put("result","success");
			response.put("message","회원 가입 축하");
		}
		else {
			response.put("result","fail");
			response.put("message","아이디가 중복됩니다");
		}
		
		return response;
	}
	
	// 유저 정보 수정
	@PutMapping(value = "/{userSeq}")
	public Map<String, String> update(@PathVariable String userSeq, @RequestBody UserDto userDto){
		userDto.setUserSeq(Integer.parseInt(userSeq));
		int ret = service.userUpdate(userDto);
		Map<String, String> response = new HashMap<>();
		
		if(ret == 1) {
			response.put("result","success");
			response.put("message","회원 정보 수정 완료");
            response.put("userPassword" , userDto.getUserPassword());
            response.put("userEmail" , userDto.getUserEmail());
		}
		else {
			response.put("result","fail");
			response.put("message","실패");
		}
		
		return response;
	}

	// 유저 정보 삭제
	@DeleteMapping(value = "/{userSeq}")
	public Map<String, String> delete(@PathVariable String userSeq) {
		int ret = service.userDelete(Integer.parseInt(userSeq));
		Map<String, String> response = new HashMap<>();
		
		if(ret == 1) {
			response.put("result","success");
			response.put("message","탈퇴 성공");
		}
		else {
			response.put("result","fail");
			response.put("message","탈퇴 실패");
		}
		
		return response;
	}
	
	// 유저 비밀번호 찾기
	@PostMapping(value = "/find")
    public UserDto login(@RequestParam String userId, @RequestParam String userName) {
        Map<String, String> response = new HashMap<>();
        
        UserDto dto = service.userFind(userId, userName);
        
        return dto;
    }
	
	// 유저 로그인
	@PostMapping(value = "/login")
    public Map<String, String> login(@RequestParam String userId, @RequestParam String userPassword, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        
        System.out.println(userId);
        System.out.println(userPassword);
        UserDto dto = service.userLogin(userId, userPassword);
        
        if (dto != null) {
            response.put("result", "success");
            response.put("message" , "로그인 성공");
            response.put("userSeq" , Integer.toString(dto.getUserSeq()));
            response.put("userName" , dto.getUserName());
            response.put("userPassword" , dto.getUserPassword());
            response.put("userProfileImageUrl" , dto.getUserProfileImageUrl());
            session.setAttribute("userInfo", dto);
            System.out.println("로그인 성공");
        } else {
            response.put("result", "fail");
            response.put("message" , "로그인 실패. 다시 시도하세요 ");
            System.out.println("로그인 실패");
        }
        
        return response;
    }
	
	// 유저 로그아웃
	@GetMapping(value = "/logout")
	public Map<String, String> logout(HttpSession session){
		session.invalidate();
		
		Map<String, String> response = new HashMap<>();
		response.put("result","success");
		
		return response;
	}
	
	// -----------------------------------------------------------------------------------------
	
	// 유저 즐겨찾기 조회
	@GetMapping(value="/favorite")
	public UserResultDto favoriteList(UserParamDto userParamDto, HttpSession session){
		UserResultDto userResultDto = new UserResultDto();
	        
        System.out.println("세선에 잘 들어가나");
        System.out.println(((UserDto) session.getAttribute("userInfo")).getUserSeq());
        userParamDto.setUserSeq( ((UserDto) session.getAttribute("userInfo")).getUserSeq());
        userResultDto = service.favoriteList(userParamDto);
        
        return userResultDto;    
    }
		
	// 유저 즐겨찾기 등록
	@PostMapping(value="/favorite")
    public UserResultDto favoriteInsert(UserParamDto userParamDto, HttpSession session) {
        UserResultDto userResultDto = new UserResultDto();
        
        System.out.println("세선에 잘 들어가나");
        System.out.println(((UserDto) session.getAttribute("userInfo")).getUserSeq());
        userParamDto.setUserSeq( ((UserDto) session.getAttribute("userInfo")).getUserSeq());
        userResultDto = service.favoriteInsert(userParamDto);
        
        return userResultDto;
    }
	
	// 유저 즐겨찾기 삭제
	@DeleteMapping(value="/favorite") 
    public UserResultDto favoriteDelete(UserParamDto userParamDto, HttpSession session){
		UserResultDto userResultDto = new UserResultDto();
	        
        System.out.println("세선에 잘 들어가나");
        System.out.println(((UserDto) session.getAttribute("userInfo")).getUserSeq());
        userParamDto.setUserSeq( ((UserDto) session.getAttribute("userInfo")).getUserSeq());
        userResultDto = service.favoriteDelete(userParamDto);
        
        return userResultDto;    
    }
}
