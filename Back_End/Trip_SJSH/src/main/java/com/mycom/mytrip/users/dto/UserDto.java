package com.mycom.mytrip.users.dto;

import java.sql.Date;

public class UserDto {
	private Integer userSeq;
	private String userId;
	private String userName;
	private String userPassword;
	private String userEmail;
	private String userProfileImageUrl;
	private Date userRegisterDate;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserProfileImageUrl() {
		return userProfileImageUrl;
	}
	public void setUserProfileImageUrl(String userProfileImageUrl) {
		this.userProfileImageUrl = userProfileImageUrl;
	}
	public Date getUserRegisterDate() {
		return userRegisterDate;
	}
	public void setUserRegisterDate(Date userRegisterDate) {
		this.userRegisterDate = userRegisterDate;
	}
	
	public UserDto() {}
	
	public UserDto(Integer userSeq, String userId, String userName, String userPassword, String userEmail,
			String userProfileImageUrl, Date userRegisterDate) {
		super();
		this.userSeq = userSeq;
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userProfileImageUrl = userProfileImageUrl;
		this.userRegisterDate = userRegisterDate;
	}
	
	@Override
	public String toString() {
		return "UserDto [userSeq=" + userSeq + ", userId=" + userId + ", userName=" + userName + ", userPassword="
				+ userPassword + ", userEmail=" + userEmail + ", userProfileImageUrl=" + userProfileImageUrl
				+ ", userRegisterDate=" + userRegisterDate + "]";
	}
	
	
}
