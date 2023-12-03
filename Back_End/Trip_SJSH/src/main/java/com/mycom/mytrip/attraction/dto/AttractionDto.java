package com.mycom.mytrip.attraction.dto;

import java.math.BigDecimal;

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
public class AttractionDto {
	private int contentId;
	private int contentTypeId;
	private String title;
	private String addr1;
	private String addr2;
	private String zipcode;
	private String tel;
	private String firstImage;
	private String firstImage2;
	private int readCount;
	private int sidoCode;
	private int gugunCode;
	private BigDecimal Latitude;
	private BigDecimal Longitude;
	private String Mlevel;
	private String created_time;
	private String modified_time;
	private String booktour;
	private String overview;
	private int day;
	private int userSeq;
	private int planListId;
}
