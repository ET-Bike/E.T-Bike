package com.etbike.server.domain.model;

import java.util.List;

public class MyBikeList {
	
	private List<ShareBoard> myBikeBoards;

	public List<ShareBoard> getMyBikeBoard() {
		return myBikeBoards;
	}

	public void setMyBikeBoard(List<ShareBoard> myBikeBoard) {
		
		if(myBikeBoard.isEmpty())
			this.myBikeBoards = myBikeBoard;
		
		else
			this.myBikeBoards.addAll(myBikeBoard);
	}
	
	
}