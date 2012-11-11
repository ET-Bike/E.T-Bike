package com.etbike.server.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.etbike.server.domain.model.Account;
import com.etbike.server.domain.model.Board;
import com.etbike.server.domain.model.BoardCategory;
import com.etbike.server.domain.model.Reply;
import com.etbike.server.service.BoardService;

@Controller
public class BoardController {

	@Autowired private BoardService boardService;
	
	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public void board(ModelMap map) {
		map.put("boardCategories", BoardCategory.values());
	}

	@RequestMapping(value = "/boards/{category}/{page}", method = RequestMethod.GET)
	public String list(@PathVariable String category, @PathVariable Integer page, String q, ModelMap map) {
		map.put("category", category);
		map.put("page", boardService.getBoards(category, page, q));
		return "jsonView";
	}

	@RequestMapping(value = "/boards/view/{id}", method = RequestMethod.GET)
	public String readById(@PathVariable Long id, ModelMap map) {
		map.put("board", boardService.readById(id));
		return "jsonView";
	}


	@RequestMapping(value = "/boards", method = RequestMethod.PUT)
	public String add(Account account, Board model, ModelMap map) {
		model.setAccount(account);
		boardService.saveBoard(model);
		map.put("result", "success");

		return "jsonView";
	}

	@RequestMapping(value = "/boards", method = RequestMethod.DELETE)
	public String delete(Long id) {
		boardService.deleteBoard(id); 
		return "jsonView";
	}
	


	@RequestMapping(value = "/replies/{boardId}/{page}", method = RequestMethod.GET)
	public String replies(@PathVariable Long boardId, @PathVariable Integer page, ModelMap map) {
		map.put("page", boardService.getReplies(boardId, page));
		return "jsonView";
	}

	@RequestMapping(value = "/replies", method = RequestMethod.PUT)
	public String addReply(Account account, Reply model, ModelMap map) {
		model.setAccount(account);
		boardService.saveReply(model);
		map.put("result", "success");

		return "jsonView";
	}

	@RequestMapping(value = "/replies", method = RequestMethod.DELETE)
	public String deleteReply(Long id) {
		boardService.deleteReply(id); 
		return "jsonView";
	}
}
