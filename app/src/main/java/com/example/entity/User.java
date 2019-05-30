package com.example.entity;

import java.io.Serializable;

public class User implements Serializable {
	private String username;
	private String nickname;
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String pass;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPass() {
		return pass;
	}
	public User() {
		super();
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

}
