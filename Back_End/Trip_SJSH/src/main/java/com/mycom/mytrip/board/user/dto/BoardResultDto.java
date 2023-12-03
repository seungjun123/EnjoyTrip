package com.mycom.mytrip.board.user.dto;

import java.util.List;

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
public class BoardResultDto {
	private int result;
	private BoardDto dto; // 상세 1건
	private List<BoardDto> list; // 목록 여러건
	private int count; // 총 건수
	
	private double avg; // 별점 평균
}
