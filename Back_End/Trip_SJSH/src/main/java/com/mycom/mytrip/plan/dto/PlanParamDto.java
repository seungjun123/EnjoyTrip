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
public class PlanParamDto {
	private int limit;
	private int offset;
	private int userSeq;
}
