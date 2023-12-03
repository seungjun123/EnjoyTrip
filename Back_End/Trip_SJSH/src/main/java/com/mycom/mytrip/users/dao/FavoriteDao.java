package com.mycom.mytrip.users.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.mytrip.attraction.dto.AttractionDto;
import com.mycom.mytrip.users.dto.UserParamDto;

@Mapper
public interface FavoriteDao {
	int favoriteInsert(UserParamDto userParamDto);
	int favoriteDelete(UserParamDto userParamDto);
	List<AttractionDto> favoriteList(UserParamDto userParamDto);
}
