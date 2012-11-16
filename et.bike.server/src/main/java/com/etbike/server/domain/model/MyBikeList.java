package com.etbike.server.domain.model;

import java.util.List;

public class MyBikeList {
	
	private List<ShareBoard> myBikeBoard;

	public List<ShareBoard> getMyBikeBoard() {
		return myBikeBoard;
	}

	public void setMyBikeBoard(List<ShareBoard> myBikeBoard) {
		
		if(this.myBikeBoard.isEmpty())
			this.myBikeBoard = myBikeBoard;
		
		else{
			int len = myBikeBoard.size();
			for (int i = 0; i < len; i++) {
				this.myBikeBoard.add(myBikeBoard.get(i));
			}
		}
			
	}
	
	
}