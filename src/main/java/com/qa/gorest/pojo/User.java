package com.qa.gorest.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	private Integer id;
	private String name;
	private String email;
	private String gender;
	private String status;
	
	public User(String name, String email, String gender, String status) {
		
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.status = status;
	}
	
	
	

}
