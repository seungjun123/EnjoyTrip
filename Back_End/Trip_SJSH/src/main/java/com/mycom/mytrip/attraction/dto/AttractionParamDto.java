package com.mycom.mytrip.attraction.dto;

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
public class AttractionParamDto {
	private int limit;
	private int offset;
	private String searchWord;
	
	private int boardId;
	private int userSeq;
}
