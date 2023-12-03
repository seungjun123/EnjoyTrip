package com.mycom.mytrip.users.dto;

import java.sql.Date;

public class FavoriteDto {
	private Integer favoriteId;
	private int userSeq;
	private int attractionId;
	private Date createdAt;
	
	
	public Integer getFavoriteId() {
		return favoriteId;
	}


	public void setFavoriteId(Integer favoriteId) {
		this.favoriteId = favoriteId;
	}


	public int getUserSeq() {
		return userSeq;
	}


	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}


	public int getAttractionId() {
		return attractionId;
	}


	public void setAttractionId(int attractionId) {
		this.attractionId = attractionId;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public FavoriteDto(Integer favoriteId, int userSeq, int attractionId, Date createdAt) {
		super();
		this.favoriteId = favoriteId;
		this.userSeq = userSeq;
		this.attractionId = attractionId;
		this.createdAt = createdAt;
	}


	@Override
	public String toString() {
		return "FavoriteDto [favoriteId=" + favoriteId + ", userSeq=" + userSeq + ", attractionId=" + attractionId
				+ ", createdAt=" + createdAt + "]";
	}
	
	
}
