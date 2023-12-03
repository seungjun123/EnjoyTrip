package com.mycom.mytrip.plan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.mytrip.attraction.dto.AttractionDto;
import com.mycom.mytrip.plan.dto.PlanDto;
import com.mycom.mytrip.plan.dto.PlanParamDto;

@Mapper
public interface PlanDao {
	List<PlanDto> plan(PlanParamDto dto);
	PlanDto planDetail(int planId);
	List<AttractionDto> list(int planId);
	int insert(PlanDto dto);
	int insertPlanList(PlanDto dto);
	int update(PlanDto dto);
	int deletePlan(int planId);
	int deletePlanForeign(int planId);
	int deletePlanList(int planListId);
}
