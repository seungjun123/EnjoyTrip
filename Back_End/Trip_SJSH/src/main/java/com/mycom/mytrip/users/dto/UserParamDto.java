package com.mycom.mytrip.users.dto;

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
public class UserParamDto {
	private int limit;
	private int offset;
	private String searchWord;
	
	private int contentId;
	private int userSeq;
}
