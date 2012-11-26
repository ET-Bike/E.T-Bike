package com.swmaestro.etbike.network.object;

import java.util.Date;

public class Reply {

	private String message;
	private String writer;
	private MyBikeBoard board;
	private String updatedTime;

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public MyBikeBoard getBoard() {
		return board;
	}

	public void setBoard(MyBikeBoard board) {
		this.board = board;
	}

}
