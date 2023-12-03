package com.mycom.mytrip.attraction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.mytrip.attraction.dao.AttractionDao;
import com.mycom.mytrip.attraction.dto.AttractionDto;
import com.mycom.mytrip.attraction.dto.AttractionParamDto;
import com.mycom.mytrip.attraction.dto.AttractionResultDto;
import com.mycom.mytrip.attraction.dto.SearchDto;

@Service
public class AttractionServiceImpl implements AttractionService{

	@Autowired
	AttractionDao attractionDao;
	
	private final int SUCCESS = 1;
	private final int FAIL = -1;
	
	@Override
	public List<AttractionDto> search(SearchDto searchDto) {
		return attractionDao.search(searchDto);
	}
	
	@Override
	public AttractionDto detail(int contentId) {
		return attractionDao.detail(contentId);
	}

	@Override
	public AttractionResultDto attractionList(AttractionParamDto dto) {
		
		AttractionResultDto attractionResultDto = new AttractionResultDto();
		try {
			List<AttractionDto> list = attractionDao.attractionList(dto);
			System.out.println(list);
			attractionResultDto.setList(list);
			attractionResultDto.setResult(SUCCESS);
		}catch(Exception  e) {
			e.printStackTrace();
			attractionResultDto.setResult(FAIL);
		}
		return attractionResultDto;
	}

	@Override
	public AttractionResultDto attractionListLike(AttractionParamDto dto) {
		AttractionResultDto attractionResultDto = new AttractionResultDto();
		try {
			List<AttractionDto> list = attractionDao.attractionListLike(dto);
			System.out.println(list);
			attractionResultDto.setList(list);
			attractionResultDto.setResult(SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			attractionResultDto.setResult(FAIL);
		}
		return attractionResultDto;
	}

	@Override
	public AttractionResultDto attractionListLikeSelectOption(AttractionParamDto dto) {
		AttractionResultDto attractionResultDto = new AttractionResultDto();
		try {
			List<AttractionDto> list = attractionDao.attractionListLikeSelectOption(dto);
			attractionResultDto.setList(list);
			attractionResultDto.setResult(SUCCESS);
		}catch(Exception e) {
			e.printStackTrace();
			attractionResultDto.setResult(FAIL);
		}
		return attractionResultDto;
	}
}
