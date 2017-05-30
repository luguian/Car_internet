package com.lu.car_internet.beans;

import cn.bmob.v3.BmobObject;

public class TalkBean extends BmobObject {

	private String username;
	private String talking;
	private String time;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTalking() {
		return talking;
	}

	public void setTalking(String talking) {
		this.talking = talking;
	}

	public TalkBean(String username, String talking, String time) {
		super();
		this.username = username;
		this.talking = talking;
		this.time = time;
	}

	public TalkBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TalkBean(String tableName) {
		super(tableName);

	}

}
