package com.mycom.mytrip.chat.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycom.mytrip.chat.dao.ChatDao;
import com.mycom.mytrip.chat.dto.ChatMessage;
import com.mycom.mytrip.chat.dto.ChatRequestParam;
import com.mycom.mytrip.chat.dto.ChatRoom;
import com.mycom.mytrip.chat.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        
        // 사용자가 보낸 메시지
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        System.out.println("chatMessage " + chatMessage);
        
        
        // 해당 채팅방의 sessions 들을 찾아줌
        // 근데, 서버가 새롭게 시작되어 기존의 채팅방 목록들이 다 날아감
        // 새롭게 채팅방을 만들어줘야함
        ChatRoom room = chatService.findRoomById(chatMessage);
        
        System.out.println(room);
        HashMap<Integer, WebSocketSession> sessions =room.getSessions();   //방에 있는 현재 사용자 한명이 WebsocketSession
        
        
        
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");  //TALK일 경우 msg가 있을 거고, ENTER일 경우 메세지 없으니까 message set
            message = new TextMessage(objectMapper.writeValueAsString(chatMessage));
            
            
        }else if (chatMessage.getType().equals(ChatMessage.MessageType.QUIT)) { // 퇴장할때마다 sessions 방 확인해서 0명이면 대화 다 지워주기
            sessions.remove(chatMessage.getUserSeq());
            
            chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장했습니다..");
            
            message = new TextMessage(objectMapper.writeValueAsString(chatMessage));

        }else {
        	chatMessage.setMessage(chatMessage.getMessage());
            
            message = new TextMessage(objectMapper.writeValueAsString(chatMessage));
        }
        System.out.println("새로운 사용자 " + chatMessage.getUserSeq());
        System.out.println("세션 정보" + session);
        System.out.println("sessions 채티방 몇명 있는지 " + sessions.size());
        
        sessions.put(chatMessage.getUserSeq(), session);
        chatService.storeMessage(chatMessage);
        
        // 세션 있는지 봐주기
        sendToEachSocket(sessions,message );
    }
    private  void sendToEachSocket(HashMap<Integer, WebSocketSession> sessions, TextMessage message){
        sessions.values().parallelStream().forEach( roomSession -> {
        	System.out.println("roomSession &&&&&&&&&&&&"+roomSession);
        	
            try {
                roomSession.sendMessage(message);
       
            } catch (IOException e) {
                //throw new RuntimeException(e);
            }
        });
    }
    

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
       //javascript에서  session.close해서 연결 끊음. 그리고 이 메소드 실행.
        //session은 연결 끊긴 session을 매개변수로 이거갖고 뭐 하세요.... 하고 제공해주는 것 뿐
    }



}

