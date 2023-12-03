package com.mycom.mytrip.attraction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchDto {
	private Integer sidoCode;
	private Integer gugunCode;
	private String Cat1;
	private String Cat2;
	private String Cat3;
	private String Title;
}
