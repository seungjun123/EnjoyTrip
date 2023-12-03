package com.mycom.mytrip.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDto {
	private int commentId;
	private int boardId;
	private int userSeq;
	private String userId;
	private String commentContent;
	private String commentRegDt;
}
