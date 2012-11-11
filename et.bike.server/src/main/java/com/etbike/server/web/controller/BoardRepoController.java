package com.etbike.server.web.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etbike.server.domain.model.Board;
import com.etbike.server.domain.model.BoardCategory;
import com.etbike.server.domain.model.MyBikeList;
import com.etbike.server.persistence.AccountRepository;
import com.etbike.server.persistence.BoardRepository;
import com.etbike.server.persistence.BoardSpecifications;
import com.etbike.server.persistence.ReplyRepository;

@Controller
public class BoardRepoController {
	@Autowired private BoardRepository boardRepository;
	@Autowired private ReplyRepository replyRepository;
	@Autowired private AccountRepository accountRepository;
	
	@RequestMapping(value="/shareBoard/getMyBikeList/{username}")
	public String getMyDealList(@PathVariable String username, ModelMap map){
		MyBikeList myBikeList = new MyBikeList();
		
		myBikeList.setMyBikeBoard(
				boardRepository.findAll(BoardSpecifications.isWriterName(username)));
		
		
		map.put("myBikeList", myBikeList);
		return "jsonView";
		
//		Gson gson = new Gson();
//		return gson.toJson(myBikeList);
		
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
