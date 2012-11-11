package com.etbike.server.web.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etbike.server.domain.model.Board;
import com.etbike.server.domain.model.BoardCategory;
import com.etbike.server.persistence.BoardRepository;
import com.etbike.server.persistence.ReplyRepository;
import com.google.gson.Gson;

@Controller
public class BoardRepoController {
	@Autowired private BoardRepository boardRepository;
	@Autowired private ReplyRepository replyRepository;
	
	@RequestMapping(value="/shareBoard/getMyDealList/{username}")
	@ResponseBody
	public String getMyDealList(@PathVariable String username){
		List<Board> boards = boardRepository.findAll();
		String myDealList = null;
		
		Gson gson = new Gson();
		for (int i = 0; i < boards.size(); i++) {
			myDealList =""+ myDealList + gson.toJson(boards.get(i));
		}
		
		return myDealList;
	}
	
	@RequestMapping(value="/shareBoard/addBoard")
	@ResponseBody
	public String addUser(String title, String content,String writer,BoardCategory category
	 ,Date updatedTime,String myImagePath,String bikeImagePath
	 ,String bikeType,String tradeType,String shareType,String lati,String longi,String costPerTime
	 ,String costPerDay,String costPerWeek){
		Board board = new Board( title,  content, writer, category
				 , updatedTime, myImagePath, bikeImagePath
				 , bikeType, tradeType, shareType, lati, longi, costPerTime
				 , costPerDay, costPerWeek);
		boardRepository.saveAndFlush(board);
		
		System.err.println("GET Error");
		return "OKAY";
	}
}
