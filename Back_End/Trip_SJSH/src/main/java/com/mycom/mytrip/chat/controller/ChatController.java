package com.mycom.mytrip.chat.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.mytrip.board.user.dto.BoardParamDto;
import com.mycom.mytrip.chat.dto.ChatMessage;
import com.mycom.mytrip.chat.dto.ChatRequestParam;
import com.mycom.mytrip.chat.dto.ChatResultDto;
import com.mycom.mytrip.chat.dto.ChatRoom;
import com.mycom.mytrip.chat.service.ChatService;
import com.mycom.mytrip.users.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;


//    @RequestMapping("/chat/chatList") // 채팅방 목록들 
//    public String chatList(Model model){
//        List<ChatRoom> roomList = chatService.findAllRoom();
//        model.addAttribute("roomList",roomList);
//        return "chat/chatList";
//    }

//    @GetMapping("/chat/chatRoom")
//    public String chatRoom(Model model, @RequestParam String roomId){
//        ChatRoom room = chatService.findRoomById(roomId);
//        model.addAttribute("room",room);   //현재 방에 들어오기위해서 필요한데...... 접속자 수 등등은 실시간으로 보여줘야 돼서 여기서는 못함
//        return "chat/chatRoom";
//    }
    
    //======================================
    
    @PostMapping("/chat/createRoom")  //방을 만들었으면 해당 방으로 가야지. // 방 만들기
    public ChatResultDto createRoom(ChatRoom chatRoom, HttpSession session) {
    	chatRoom.setUserSeq( ((UserDto) session.getAttribute("userInfo")).getUserSeq());
    	return chatService.createRoom(chatRoom);
    }
    
    @GetMapping("/chat/list")
    public ChatResultDto chatList(HttpSession session) { // sameuser 넣어주기
    	ChatResultDto chatResultDto = chatService.findAllRoom();
    	for( ChatRoom chatRoom : chatResultDto.getList()) {
    		if(((UserDto) session.getAttribute("userInfo")).getUserSeq() == chatRoom.getUserSeq() ) {
    			chatRoom.setSameUser(true);
        	}
    	}
    	return chatResultDto;
    }
    
    //room_id 받아서 디테일로 
    @GetMapping("/chatdetail/{roomId}")
    public ChatResultDto chatListDetail(@PathVariable int roomId, HttpSession session) {
    	ChatResultDto chatResultDto = chatService.chatboardDetail(roomId);
    	System.out.println(((UserDto) session.getAttribute("userInfo")).getUserSeq());
    	System.out.println(chatResultDto.getDto().getUserSeq());
    	if(((UserDto) session.getAttribute("userInfo")).getUserSeq() == chatResultDto.getDto().getUserSeq() ) {
    		chatResultDto.getDto().setSameUser(true);
    	}
    	
    	return chatResultDto;
    }
    
    @GetMapping("/chat/{roomId}")
    public ChatResultDto chatList(@PathVariable int roomId) {
    	return chatService.chatmsgList(roomId);
    }
    
    @GetMapping("/chat/mypage")
    public ChatResultDto chatboardListMypage(HttpSession session) {
    	int userSeq = ((UserDto) session.getAttribute("userInfo")).getUserSeq();
    	
    	ChatResultDto chatResultDto = chatService.chatboardListMypage(userSeq);
    	for( ChatRoom chatRoom : chatResultDto.getList()) {
    			chatRoom.setSameUser(true);
    	}
    	return chatResultDto;
    }
    
    @DeleteMapping("/chat/{roomId}")
    public ChatResultDto chatRoomDelete(@PathVariable int roomId) {
    	return chatService.chatDelete(roomId);
    }
    
    @PostMapping("/chat/people/{roomId}")
    public ChatResultDto chatPeopleInsert(@PathVariable int roomId, HttpSession session) {
    	 ChatRequestParam chatRequestParam = new ChatRequestParam();
    	 
    	 chatRequestParam.setRoomId(roomId);
    	 chatRequestParam.setUserSeq( ((UserDto) session.getAttribute("userInfo")).getUserSeq());
    	 
    	return chatService.chatPeopleInsert(chatRequestParam);
    }
    
    @DeleteMapping("/chat/people/{roomId}")
    public ChatResultDto chatPeopleDelete(@PathVariable int roomId, HttpSession session) {
    	ChatRequestParam chatRequestParam = new ChatRequestParam();
   	 
   	 	chatRequestParam.setRoomId(roomId);
   	 	chatRequestParam.setUserSeq( ((UserDto) session.getAttribute("userInfo")).getUserSeq());
    	return chatService.chatPeopleDelete(chatRequestParam);
    }
    
    @GetMapping("/chat/people/{roomId}")
    public ChatResultDto chatPeopleSelect(@PathVariable int roomId) {
    	return chatService.chatPeopleSelect(roomId);
    }
    
//    @PostMapping("/chat/message")  //방을 만들었으면 해당 방으로 가야지. // 방 만들기
//    public ChatResultDto storeMessage(ChatMessage chatMessage, HttpSession session) {
//    	chatMessage.setUserSeq( ((UserDto) session.getAttribute("userInfo")).getUserSeq());
//    	return chatService.storeMessage(chatMessage);
//    }
}
