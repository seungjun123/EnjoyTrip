package com.mycom.mytrip.chat.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatRoom {
	private int id;
    private String roomId;
    private int userSeq;
    private String userId;
    private String userName;
    private String name; // 글 제목이자 채팅방 방이름
    private String content;
    private LocalDateTime regDt; // java 8
    private boolean sameUser;
    
//    private Set<WebSocketSession> sessions = new HashSet<>();
    private HashMap<Integer, WebSocketSession> sessions = new HashMap<Integer, WebSocketSession>();
    
    
    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }
    
 // regDt setter
    public void setRegDt(Date regDt) {
        // lombok + mybatis mapping 충돌 방지
        this.regDt = LocalDateTime.ofInstant(regDt.toInstant(), ZoneId.systemDefault());        
    }
}
