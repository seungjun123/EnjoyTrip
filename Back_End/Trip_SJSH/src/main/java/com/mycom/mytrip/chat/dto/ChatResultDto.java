package com.mycom.mytrip.chat.dto;

import java.util.List;

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
public class ChatResultDto {
	private int result;
	private ChatRoom dto; // 상세 1건
	private List<ChatRoom> list; // 목록 여러건
	
	private ChatMessage msgDto;
	private List<ChatMessage> msgList;

}
