package com.mycom.mytrip.plan.dto;

import java.sql.Date;

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
public class PlanDto {
	private int planListId;
	private int planId;
	private int userSeq;
	private String planTitle;
	private String content;
	private int budget;
	private String startDate;
	private String endDate;
	private int day;
	private String contentId;
}
