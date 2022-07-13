package com.axiata.boostDemo.dto;

import lombok.Data;
import lombok.ToString;
import java.util.Date;

@Data
@ToString
public class CommentDTO {
	private String id;
	private String message;
	private UserDTO owner;
	private String post;
	private Date publishDate;
}