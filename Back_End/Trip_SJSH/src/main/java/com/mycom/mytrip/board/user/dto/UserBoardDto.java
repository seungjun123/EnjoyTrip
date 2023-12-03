package com.mycom.mytrip.board.user.dto;

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
public class UserBoardDto {
	private int boardId;
	private int userSeq;
	private String Title;
	private String Content;
	private String regDt;
	private int readCount;
}
