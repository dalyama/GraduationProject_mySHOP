package com.myShop.entities;



import lombok.Data;

@Data
public class MailRequest {

	private String from;

	private String to;
    
	private String subject;
    
	private String name;
	
	private String content;
	
	
}
