package com.mycom.mytrip.users.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycom.mytrip.users.dto.UserDto;

@Mapper
public interface UserDao {
	UserDto userDetail(int userSeq); 
	UserDto userLogin(@Param("userId")String userId, @Param("userPassword")String userPassword);
	int userInsert(UserDto userDto);
	
	UserDto userFind(@Param("userId")String userId, @Param("userName")String userName);
	
	int userUpdate(UserDto userDto);
	int userDelete(int userSeq);
}
