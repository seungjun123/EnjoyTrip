package com.mycom.mytrip.plan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.mytrip.attraction.dto.AttractionDto;
import com.mycom.mytrip.plan.dto.PlanDto;
import com.mycom.mytrip.plan.dto.PlanParamDto;
import com.mycom.mytrip.plan.dto.PlanResultDto;
import com.mycom.mytrip.plan.service.PlanService;

@RestController
@RequestMapping("/plan")
public class PlanController {
	@Autowired
	PlanService planService;
	
	// 여행 계획 불러오기
	@GetMapping(value = "")
	public PlanResultDto plan(PlanParamDto dto) {
		return planService.plan(dto);
	}
	
	// 여행 계획 등록
	@PostMapping(value = "")
	public Map<String, String> insert(@RequestBody PlanDto dto) {
		Map<String, String> map = new HashMap<>();
		if(planService.insert(dto) == 1) {
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}
		return map;
	}
	
	// 여행 계획 수정
	@PutMapping(value = "")
	public Map<String, String> update(@RequestBody PlanDto dto) {
		Map<String, String> map = new HashMap<>();
		if(planService.update(dto) == 1) {
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}
		return map;
	}
	
	// 여행 계획 삭제
	@DeleteMapping(value = "/{planId}")
	public Map<String, String> delete(@PathVariable int planId) {
		Map<String, String> map = new HashMap<>();
		if(planService.delete(planId) == 1) {
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}
		return map;
	}
	
	// 여행 일정 불러오기
	@GetMapping(value = "/detail/{planId}")
	public PlanResultDto planDetail(@PathVariable String planId) {
		System.out.println(planId);
		return planService.planDetail(Integer.parseInt(planId));
	}
	
	// 여행 일정 등록
	@PostMapping(value = "/list")
	public Map<String, String> planListInsert(@RequestBody PlanDto dto) {
		Map<String, String> map = new HashMap<>();
		if(planService.insertPlanList(dto) == 1) {
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}
		return map;
	}
	
	// 여행 일정 삭제
	@DeleteMapping(value = "/list/{planListId}")
	public Map<String, String> planDelete(@PathVariable int planListId) {
		Map<String, String> map = new HashMap<>();
		if(planService.deletePlanList(planListId) == 1) {
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}
		return map;
	}
}
