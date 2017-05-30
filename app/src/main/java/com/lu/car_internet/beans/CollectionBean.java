package com.lu.car_internet.beans;

public class CollectionBean {
	private int q_type;// 题型：0：判断题 1：选择题
	private String title;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private int rightAnswer;
	private byte[] picture;// 有些问题中含图片

	public int getQ_type() {
		return q_type;
	}

	public void setQ_type(int q_type) {
		this.q_type = q_type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public int getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(int rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public CollectionBean(int q_type, String title, String optionA,
			String optionB, String optionC, String optionD, int rightAnswer,
			byte[] picture) {
		super();
		this.q_type = q_type;
		this.title = title;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.rightAnswer = rightAnswer;
		this.picture = picture;
	}

}
