package com.mycom.mytrip.chat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.mytrip.chat.dto.ChatMessage;
import com.mycom.mytrip.chat.dto.ChatRequestParam;
import com.mycom.mytrip.chat.dto.ChatRoom;

@Mapper
public interface ChatDao {
	int chatboardInsert(ChatRoom chatRoom);
	List<ChatRoom> chatboardList();
	ChatRoom chatboardListDetail(ChatRequestParam chatRequestParam);
	
	int chatMsgInsert(ChatMessage chatMessage);
	List<ChatMessage> chatMsgList(int roomId);
	
	List<ChatRoom> chatboardListMypage(int userSeq);
	ChatRoom chatboardDetail(int roomId);
	
	int chatMsgDelete(int roomId);
	int chatRoomDelete(int roomId);
	int chatRoomPeopleDelete(int roomId);
	
	int chatPeopleInsert(ChatRequestParam chatRequestParam);
	int chatPeopleDelete(ChatRequestParam chatRequestParam);
	List<ChatRoom> chatPeopleSelect(int roomId);

}
