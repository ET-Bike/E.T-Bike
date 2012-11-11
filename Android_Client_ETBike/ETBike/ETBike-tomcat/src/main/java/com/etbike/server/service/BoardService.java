package com.etbike.server.service;

import com.etbike.server.domain.model.Board;
import com.etbike.server.domain.model.BoardCategory;
import com.etbike.server.domain.model.Reply;

public interface BoardService {

	BoardCategory getBoardCategory(String category);
	Object getBoards(String category, Integer page, String searchTerm);
	Board saveBoard(Board board);
	void deleteBoard(Long id);

	Object getReplies(Long boardId, Integer page);
	void saveReply(Reply model);
	void deleteReply(Long id);
	
	Board readById(Long id);
}
