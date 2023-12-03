package com.mycom.mytrip.plan.service;

import com.mycom.mytrip.plan.dto.PlanDto;
import com.mycom.mytrip.plan.dto.PlanParamDto;
import com.mycom.mytrip.plan.dto.PlanResultDto;

public interface PlanService {
	PlanResultDto plan(PlanParamDto dto);
	PlanResultDto planDetail(int planId);
	int insert(PlanDto dto);
	int insertPlanList(PlanDto dto);
	int update(PlanDto dto);
	int delete(int planId);
	int deletePlanList(int planListId);
}
