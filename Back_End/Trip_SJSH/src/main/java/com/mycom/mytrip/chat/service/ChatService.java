package com.mycom.mytrip.chat.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycom.mytrip.chat.dao.ChatDao;
import com.mycom.mytrip.chat.dto.ChatMessage;
import com.mycom.mytrip.chat.dto.ChatRequestParam;
import com.mycom.mytrip.chat.dto.ChatResultDto;
import com.mycom.mytrip.chat.dto.ChatRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
	
	@Autowired
	ChatDao chatDao;
	
	private final int SUCCESS = 1;
	private final int FAIL = -1;

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    // 만들어진 모든 게시글(그룹채팅방) 리스트 뱉음
    public ChatResultDto findAllRoom() {
    	ChatResultDto chatResultDto = new ChatResultDto();
    	try {
    		List<ChatRoom> list = chatDao.chatboardList();
    		chatResultDto.setList(list);
    		chatResultDto.setResult(SUCCESS);
    	}catch(Exception e) {
    		e.printStackTrace();
    		chatResultDto.setResult(FAIL);
    	}
    	return chatResultDto;
//        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(ChatMessage chatMessage) {
    	System.out.println("==========채팅방목록들=======");
    	System.out.println(chatRooms.values());
    	
    	String roomId = chatMessage.getRoomId();
    	
    	if(!chatRooms.containsKey(roomId)) {
    		
    		// 새로운 채팅방 정보를 받아오기
    		ChatRequestParam chatRequestParam = new ChatRequestParam();
    		chatRequestParam.setUserSeq(chatMessage.getUserSeq());
    		chatRequestParam.setUniqueRoomId(chatMessage.getRoomId());
    		ChatRoom cr = chatDao.chatboardListDetail(chatRequestParam);
    		
    		// 새롭게 방 개설
    		newCreateRoom(cr);
    	}
        return chatRooms.get(roomId);
    }

    public ChatResultDto createRoom(ChatRoom chatRoom) {
    	ChatResultDto chatResultDto = new ChatResultDto();
    	
    	System.out.println(chatRoom);
    	try {
    		// 방 random room_id 지정
    		String randomId = UUID.randomUUID().toString();
    		chatRoom.setRoomId(randomId);
    		
    		chatDao.chatboardInsert(chatRoom);
    		
    		// 성공
    		chatResultDto.setResult(SUCCESS);
    		
    		chatRooms.put(randomId, chatRoom);
    		
    		System.out.println(chatRooms);
    	}catch(Exception e) {
    		e.printStackTrace();
    		chatResultDto.setResult(FAIL);
    	}
        return chatResultDto;
    }
    
    public void newCreateRoom(ChatRoom chatRoom) {  	
    	chatRooms.put(chatRoom.getRoomId(), chatRoom);
    }
    
    public int storeMessage(ChatMessage chatMessage) {
//    	ChatResultDto chatResultDto = new ChatResultDto();
//    	
//    	try {
//    		chatDao.chatMsgInsert(chatMessage);
//    		
//    		// 성공
//    		chatResultDto.setResult(SUCCESS);
//    		
//    	}catch(Exception e) {
//    		e.printStackTrace();
//    		chatResultDto.setResult(FAIL);
//    	}
//        return chatResultDto;
    	return chatDao.chatMsgInsert(chatMessage);
    	
    }
    
    public ChatResultDto chatmsgList(int roomId) {
    	ChatResultDto chatResultDto = new ChatResultDto();
    	
    	try {
    		List<ChatMessage> list = chatDao.chatMsgList(roomId);
    		System.out.println(list);
    		chatResultDto.setMsgList(list);
    		chatResultDto.setResult(SUCCESS);
    	}catch(Exception e) {
    		chatResultDto.setResult(FAIL);
    	}
    	
    	return chatResultDto;
    }
    
    public ChatResultDto chatboardListMypage(int userSeq) {
    	ChatResultDto chatResultDto = new ChatResultDto();
    	
    	try {
    		List<ChatRoom> list = chatDao.chatboardListMypage(userSeq);
    		
    		chatResultDto.setList(list);
    		chatResultDto.setResult(SUCCESS);
    	}catch(Exception e) {
    		chatResultDto.setResult(FAIL);
    	}
    	
    	return chatResultDto;
    }
    
    public ChatResultDto chatboardDetail(int roomId) {
    	ChatResultDto chatResultDto = new ChatResultDto();
    	
    	try {
    		ChatRoom chatRoom = chatDao.chatboardDetail(roomId);
    		
    		chatResultDto.setDto(chatRoom);
    		chatResultDto.setResult(SUCCESS);
    	}catch(Exception e) {
    		chatResultDto.setResult(FAIL);
    	}
    	
    	return chatResultDto;
    }
    
    
    @Transactional
    public ChatResultDto chatDelete(int roomId) {
    	ChatResultDto chatResultDto = new ChatResultDto();
    	
    	try {
    		chatDao.chatMsgDelete(roomId);
    		//chatpeople도 지우기
    		chatDao.chatRoomPeopleDelete(roomId);
    		chatDao.chatRoomDelete(roomId);
    		chatResultDto.setResult(SUCCESS);
    	}catch(Exception e) {
    		e.printStackTrace();
    		chatResultDto.setResult(FAIL);
    	}
    	
    	return chatResultDto;
    }
    
    public ChatResultDto chatPeopleInsert(ChatRequestParam chatRequestParam) {
    	ChatResultDto chatResultDto = new ChatResultDto();
    	
    	try {
    		chatDao.chatPeopleInsert(chatRequestParam);
    		chatResultDto.setResult(SUCCESS);
    	}catch(Exception e) {
    		chatResultDto.setResult(FAIL);
    	}
    	
    	return chatResultDto;
    }
    
    public ChatResultDto chatPeopleDelete(ChatRequestParam chatRequestParam) {
    	ChatResultDto chatResultDto = new ChatResultDto();
    	
    	try {
    		chatDao.chatPeopleDelete(chatRequestParam);
    		chatResultDto.setResult(SUCCESS);
    	}catch(Exception e) {
    		chatResultDto.setResult(FAIL);
    	}
    	
    	return chatResultDto;
    }
    
    public ChatResultDto chatPeopleSelect(int roomId) {
    	ChatResultDto chatResultDto = new ChatResultDto();
    	
    	try {
    		List<ChatRoom> list = chatDao.chatPeopleSelect(roomId);
    		
    		chatResultDto.setList(list);
    		chatResultDto.setResult(SUCCESS);
    	}catch(Exception e) {
    		chatResultDto.setResult(FAIL);
    	}
    	
    	return chatResultDto;
    }
}

