package com.etbike.server.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.etbike.server.domain.model.Board;
import com.etbike.server.domain.model.BoardCategory;
import com.etbike.server.domain.model.MyBikeList;
import com.etbike.server.domain.model.ShareBoard;
import com.etbike.server.domain.model.UploadedFile;
import com.etbike.server.persistence.AccountRepository;
import com.etbike.server.persistence.BoardRepository;
import com.etbike.server.persistence.BoardSpecifications;
import com.etbike.server.persistence.FileRepository;
import com.etbike.server.persistence.FileSpecifications;
import com.etbike.server.persistence.ReplyRepository;

@Controller
public class BoardRepoController {
	@Autowired private BoardRepository boardRepository;
	@Autowired private ReplyRepository replyRepository;
	@Autowired private AccountRepository accountRepository;
	@Autowired private FileRepository fileRepository;
	
	@RequestMapping(value="/shareBoard/getMyBikeList/{username}")
	public String getMyDealList(@PathVariable String username, ModelMap map){
		MyBikeList myBikeList = new MyBikeList();
		List<ShareBoard> shareboards = new ArrayList<ShareBoard>();
		List<Board> boards = boardRepository.findAll(BoardSpecifications.isWriterName(username));
		
		for (int i = 0; i < boards.size(); i++) {
			ShareBoard shareboard = new ShareBoard();
			Board board = boards.get(i);
			shareboard.setTitle(board.getTitle());
			shareboard.setContent(board.getContent());
			shareboard.setWriter(board.getWriter());
			shareboard.setCategory(board.getCategory());
			shareboard.setUpdatedTime(board.getUpdatedTime());
			shareboard.setMyImagePath(board.getMyImagePath());
			shareboard.setBikeImagePath(board.getBikeImagePath());
			shareboard.setBikeType(board.getBikeType());
			shareboard.setTradeType(board.getTradeType());
			shareboard.setShareType(board.getShareType());
			shareboard.setLati(board.getLati());
			shareboard.setLongi(board.getLongi());
			shareboard.setCostPerTime(board.getCostPerTime());
			shareboard.setCostPerDay(board.getCostPerDay());
			shareboard.setCostPerWeek(board.getCostPerWeek());
			shareboard.setLikeCount(board.getLikeCount());
			shareboard.setDealWith(board.getDealWith());
			shareboard.setBikeImagePathThumb(board.getBikeImagePathThumb());
			
			shareboards.add(shareboard);
		}
			
		myBikeList.setMyBikeBoard(shareboards);
		
		
		map.put("myBikeList", myBikeList);
		return "jsonView";
		
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
		List<UploadedFile> bikeImages =fileRepository.findAll(FileSpecifications.isfileName(board.getBikeImagePath()));
		UploadedFile selectedImege = bikeImages.get(bikeImages.size() - 1);
		board.setBikeImagePath(selectedImege.getFileDownloadUrl());
		board.setBikeImagePathThumb("http://125.209.193.11:8080/etbike/thumb/"+ selectedImege.getId()+"/50");
		List<UploadedFile>  myImages =fileRepository.findAll(FileSpecifications.isfileName(board.getMyImagePath()));
		board.setMyImagePath(myImages.get(myImages.size() - 1).getFileDownloadUrl());
		
		
		boardRepository.saveAndFlush(board);
		
		//System.err.println("GET Error");
		return "OKAY";
	}
}
