package com.mycom.mytrip.chat.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.mycom.mytrip.board.user.dto.BoardFileDto;

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
public class ChatMessage {
    // 메시지 타입 : 입장, 채팅, 나감
    public enum MessageType {
        ENTER, TALK,QUIT
    }
    private int id;
    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
    private int userSeq;
    private String userId;
    private LocalDateTime regDt; // java 8
    
    private boolean sameUser;
    
    public void setRegDt(Date regDt) {
        // lombok + mybatis mapping 충돌 방지
        this.regDt = LocalDateTime.ofInstant(regDt.toInstant(), ZoneId.systemDefault());        
    }
}
