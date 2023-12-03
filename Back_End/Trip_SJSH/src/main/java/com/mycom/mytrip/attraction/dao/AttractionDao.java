package com.mycom.mytrip.attraction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycom.mytrip.attraction.dto.AttractionDto;
import com.mycom.mytrip.attraction.dto.AttractionParamDto;
import com.mycom.mytrip.attraction.dto.SearchDto;

@Mapper
public interface AttractionDao {
	List<AttractionDto> search(SearchDto searchDto);
	AttractionDto detail(int contentId);
	
	List<AttractionDto> attractionList(AttractionParamDto dto);
	
	List<AttractionDto> attractionListLike(AttractionParamDto dto);
	
	List<AttractionDto> attractionListLikeSelectOption(AttractionParamDto dto);
}
