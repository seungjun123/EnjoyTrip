package com.mycom.mytrip.attraction.dto;

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
public class AttractionResultDto {
	private int result;
	private AttractionDto dto; // 상세 1건
	private List<AttractionDto> list; // 목록 여러건
	private int count; // 총 건수
}
