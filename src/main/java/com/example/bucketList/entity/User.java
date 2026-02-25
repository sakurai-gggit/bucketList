package com.example.bucketList.entity;

public class User {
	public User(String email, String password) {
		this.email = email;
		this.password = password;

	}

	private String email;
	private String password;
	private Long userId;
}
