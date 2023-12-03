package com.mycom.mytrip.users.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.mytrip.attraction.dto.AttractionDto;
import com.mycom.mytrip.users.dao.FavoriteDao;
import com.mycom.mytrip.users.dao.UserDao;
import com.mycom.mytrip.users.dto.FavoriteDto;
import com.mycom.mytrip.users.dto.UserDto;
import com.mycom.mytrip.users.dto.UserParamDto;
import com.mycom.mytrip.users.dto.UserResultDto;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDao dao;
	
	@Autowired
	FavoriteDao favoriteDao;
	
	private final int SUCCESS = 1;
	private final int FAIL = -1;

	@Override
	public UserDto userDetail(int userSeq) {
		return dao.userDetail(userSeq);
	}

	@Override
	public UserDto userLogin(String userId, String userPassword) {
		return dao.userLogin(userId, userPassword);
	}

	@Override
	public int userInsert(UserDto userDto) {
		return dao.userInsert(userDto);
	}

	@Override
	public UserDto userFind(String userId, String userName) {
		return dao.userFind(userId, userName);
	}

	@Override
	public int userUpdate(UserDto userDto) {
		return dao.userUpdate(userDto);
	}

	@Override
	public int userDelete(int userSeq) {
		return dao.userDelete(userSeq);
	}


	@Override
	public UserResultDto favoriteInsert(UserParamDto userParamDto) {
		UserResultDto userResultDto = new UserResultDto();
		try {
			System.out.println(userParamDto);
			int result = favoriteDao.favoriteInsert(userParamDto);
			
			userResultDto.setResult(SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			userResultDto.setResult(FAIL);
		}
		return userResultDto;
	}

	@Override
	public UserResultDto favoriteDelete(UserParamDto userParamDto) {
		UserResultDto userResultDto = new UserResultDto();
		try {
			int result = favoriteDao.favoriteDelete(userParamDto);
			
			userResultDto.setResult(SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			userResultDto.setResult(FAIL);
		}
		return userResultDto;
	}

	@Override
	public UserResultDto favoriteList(UserParamDto userParamDto) {
		UserResultDto userResultDto = new UserResultDto();
		try {
			List<AttractionDto> list = favoriteDao.favoriteList(userParamDto);
			
			userResultDto.setAttrlist(list);
			userResultDto.setResult(SUCCESS);
			
		}catch(Exception e) {
			e.printStackTrace();
			userResultDto.setResult(FAIL);
		}
		return userResultDto;
	}


}
