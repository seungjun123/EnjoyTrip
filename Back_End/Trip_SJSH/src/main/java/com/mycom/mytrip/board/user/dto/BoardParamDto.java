package com.mycom.mytrip.board.user.dto;

import java.time.LocalDateTime;

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
public class BoardParamDto {
	private int limit;
	private int offset;
	private String searchWord;
	
	private int boardId;
	private int userSeq;
}
