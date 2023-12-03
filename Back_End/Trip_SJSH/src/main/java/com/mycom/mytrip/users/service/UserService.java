package com.mycom.mytrip.users.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;

import com.mycom.mytrip.attraction.dto.AttractionDto;
import com.mycom.mytrip.users.dto.FavoriteDto;
import com.mycom.mytrip.users.dto.UserDto;
import com.mycom.mytrip.users.dto.UserParamDto;
import com.mycom.mytrip.users.dto.UserResultDto;


public interface UserService {
	UserDto userDetail(int userSeq); 
	UserDto userLogin(String userId, String userPassword);
	int userInsert(UserDto userDto);
	
	UserDto userFind(@Param("userId")String userId, @Param("userName")String userName);
	
	int userUpdate(UserDto userDto);
	int userDelete(int userSeq);
	
	
	// 즐겨찾기
	UserResultDto favoriteInsert(UserParamDto userParamDto);
	UserResultDto favoriteDelete(UserParamDto userParamDto);
	UserResultDto favoriteList(UserParamDto userParamDto);
}
