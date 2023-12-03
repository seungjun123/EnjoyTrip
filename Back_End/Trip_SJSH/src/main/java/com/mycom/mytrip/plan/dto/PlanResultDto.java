package com.mycom.mytrip.plan.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.mycom.mytrip.attraction.dto.AttractionDto;
import com.mycom.mytrip.board.user.dto.BoardFileDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlanResultDto {
	private int result;
	private PlanDto dto; // 상세 1건
	private List<AttractionDto> attrList; // 목록 여러건
	private List<PlanDto> list; // 목록 여러건
	private int count; // 총 건수
}
