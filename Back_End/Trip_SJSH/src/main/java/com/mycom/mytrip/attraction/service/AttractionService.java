package com.mycom.mytrip.attraction.service;

import java.util.List;

import com.mycom.mytrip.attraction.dto.AttractionDto;
import com.mycom.mytrip.attraction.dto.AttractionParamDto;
import com.mycom.mytrip.attraction.dto.AttractionResultDto;
import com.mycom.mytrip.attraction.dto.SearchDto;

public interface AttractionService {
	List<AttractionDto> search(SearchDto searchDto);
	AttractionDto detail(int contentId);
	
	AttractionResultDto attractionList(AttractionParamDto dto);
	AttractionResultDto attractionListLike(AttractionParamDto dto);
	
	AttractionResultDto attractionListLikeSelectOption(AttractionParamDto dto);
	
}
