package com.mycom.mytrip.plan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.mytrip.attraction.dto.AttractionDto;
import com.mycom.mytrip.attraction.dto.AttractionParamDto;
import com.mycom.mytrip.attraction.dto.AttractionResultDto;
import com.mycom.mytrip.plan.dao.PlanDao;
import com.mycom.mytrip.plan.dto.PlanDto;
import com.mycom.mytrip.plan.dto.PlanParamDto;
import com.mycom.mytrip.plan.dto.PlanResultDto;

@Service
public class PlanServiceImpl implements PlanService {
	@Autowired
	PlanDao planDao;
	
	private final int SUCCESS = 1;
	private final int FAIL = -1;
	
	@Override
	public PlanResultDto plan(PlanParamDto dto) {
		PlanResultDto planResultDto = new PlanResultDto();
		try {
			planResultDto.setList(planDao.plan(dto));
			planResultDto.setResult(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			planResultDto.setResult(FAIL);
		}
		return planResultDto;
	}
	
	@Override
	public PlanResultDto planDetail(int planId) {
		PlanResultDto planResultDto = new PlanResultDto();
		try {
			planResultDto.setDto(planDao.planDetail(planId));
			planResultDto.setAttrList(planDao.list(planId));
			planResultDto.setResult(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			planResultDto.setResult(FAIL);
		}
		return planResultDto;
	}
	
	@Override
	public int insert(PlanDto dto) {
		return planDao.insert(dto);
	}
	
	@Override
	public int insertPlanList(PlanDto dto) {
		return planDao.insertPlanList(dto);
	}
	
	@Override
	public int update(PlanDto dto) {
		return planDao.update(dto);
	}
	
	@Override
	public int delete(int planId) {
		planDao.deletePlanForeign(planId);
		return planDao.deletePlan(planId);
	}
	
	@Override
	public int deletePlanList(int planListId) {
		return planDao.deletePlanList(planListId);
	}
}
