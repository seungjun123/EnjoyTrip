package com.mycom.mytrip.attraction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.mytrip.attraction.dto.AttractionDto;
import com.mycom.mytrip.attraction.dto.AttractionParamDto;
import com.mycom.mytrip.attraction.dto.AttractionResultDto;
import com.mycom.mytrip.attraction.dto.SearchDto;
import com.mycom.mytrip.attraction.service.AttractionService;
import com.mycom.mytrip.users.dto.UserDto;

@RestController
public class AttractionController {
	
	@Autowired
	AttractionService attrationService;
	
	@PostMapping(value = "/attraction")
	public List<AttractionDto> attractionInfo(@ModelAttribute SearchDto searchDto) {
		System.out.println("attractionInfo");

		System.out.println(searchDto);
		List<AttractionDto> list = attrationService.search(searchDto);
		for(AttractionDto dto : list)
			System.out.println(dto);
		return list;
	}
	
	@GetMapping(value = "/attraction/{contentId}")
	public AttractionDto attractionDetail(@PathVariable int contentId) {
		System.out.println("attractionDetail");

		AttractionDto dto = attrationService.detail(contentId);
		System.out.println(dto);
		return dto;
	}
	
	// 紐⑸줉 get 
	@GetMapping(value="/attraction")
	public AttractionResultDto attractionList(AttractionParamDto attractionParamDto) {
		AttractionResultDto attractionResultDto = attrationService.attractionList(attractionParamDto);
		return attractionResultDto;
	}
	
	@GetMapping(value="/attraction/search")
	public AttractionResultDto attractionListLike(AttractionParamDto attractionParamDto, HttpSession session) {
		attractionParamDto.setUserSeq( ((UserDto) session.getAttribute("userInfo")).getUserSeq());
		//System.out.println(attractionParamDto);
		AttractionResultDto attractionResultDto = attrationService.attractionListLike(attractionParamDto);
		return attractionResultDto;
	}
	
	@GetMapping(value="/attraction/search/select")
	public AttractionResultDto attractionListLikeSelectOption(AttractionParamDto attractionParamDto) {
		AttractionResultDto attractionResultDto = attrationService.attractionListLikeSelectOption(attractionParamDto);
		return attractionResultDto;
	}
}
