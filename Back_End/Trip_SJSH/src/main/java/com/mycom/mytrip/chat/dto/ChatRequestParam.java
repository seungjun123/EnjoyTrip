package com.mycom.mytrip.chat.dto;

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
public class ChatRequestParam {

	int roomId;
	String roomName;
	String userName;
	int userSeq;
	
	String uniqueRoomId;
}
