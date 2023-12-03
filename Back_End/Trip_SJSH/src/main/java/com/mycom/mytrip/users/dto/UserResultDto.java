package com.mycom.mytrip.users.dto;

import java.util.List;

import com.mycom.mytrip.attraction.dto.AttractionDto;

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
public class UserResultDto {
	private int result;
	private FavoriteDto dto; // 상세 1건
	private List<FavoriteDto> favlist; // 즐겨찾기 목록 여러건
	private List<AttractionDto> attrlist; // 즐겨찾기 목록 여러건
	private int count; // 총 건수
}
