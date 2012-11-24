package com.etbike.server.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	@RequestMapping(value="/request/push", method = RequestMethod.GET)
	@ResponseBody
	public String requestPush(@PathVariable String username, ModelMap map){

		
		//sendMsg("doo871128","응답바");
		//map.put("myBikeList", myBikeList);
		//eturn "jsonView";
		return "OK";
		
	}
	
	@RequestMapping(value="/shareBoard/getMyBikeList/{username}")
	public String getMyDealList(@PathVariable String username, ModelMap map){
		MyBikeList myBikeList = new MyBikeList();
		List<ShareBoard> shareBoards = new ArrayList<ShareBoard>();
		List<Board> boards = boardRepository.findAll(BoardSpecifications.isWriterName(username));
		
		toShareBoard(boards, shareBoards);		
		myBikeList.setMyBikeBoard(shareBoards);
		
		map.put("myBikeList", myBikeList);
		return "jsonView";
		
	}
	
	@RequestMapping(value="/shareBoard/addBoard") //, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String addBoard(String title, String content,String writer,BoardCategory category
	 ,Date updatedTime,String myImagePath,String bikeImagePath
	 ,String bikeType,String tradeType,String shareType,String lati,String longi,String costPerTime
	 ,String costPerDay,String costPerWeek, HttpServletRequest req, HttpServletResponse resp){
		/*try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setCharacterEncoding("UTF-8");*/
		Board board = new Board( title,  content, writer, category
				 , updatedTime, myImagePath, bikeImagePath
				 , bikeType, tradeType, shareType, lati, longi, costPerTime
				 , costPerDay, costPerWeek);
		List<UploadedFile> bikeImages =fileRepository.findAll(FileSpecifications.isfileName(board.getBikeImagePath()));
		if(!bikeImages.isEmpty()){
			UploadedFile selectedImege = bikeImages.get(bikeImages.size() - 1);
			board.setBikeImagePath(selectedImege.getFileDownloadUrl());
			board.setBikeImagePathThumb("http://125.209.193.11:8080/etbike/thumb/"+ selectedImege.getId()+"/200");	
		}
		List<UploadedFile>  myImages =fileRepository.findAll(FileSpecifications.isfileName(board.getMyImagePath()));
		if(!myImages.isEmpty())
			board.setMyImagePath(myImages.get(myImages.size() - 1).getFileDownloadUrl());

		boardRepository.saveAndFlush(board);
		
		//System.err.println("GET Error");
		return "OKAY";
	}
	
	@RequestMapping(value="/shareBoard/updateLikeCount")
	@ResponseBody
	public String updateGrade(Long id){
		
		Board selectedBoard  = boardRepository.findOne(id);
		
		if(selectedBoard != null){
			int likeCount = selectedBoard.getLikeCount();
			selectedBoard.setLikeCount(++likeCount);
			boardRepository.saveAndFlush(selectedBoard);
			return "OKAY";
				
		} else{
			System.err.println("Error : There is not board_id...");
			return "Error : There is not board_id ";
		}
	}
	
	@RequestMapping(value="/getShareBoards" , method = RequestMethod.GET)
	public String getShareBoards(ModelMap map){
		MyBikeList myBikeList = new MyBikeList();
		List<ShareBoard> saleBoards = new ArrayList<ShareBoard>();
		List<ShareBoard> rentalBoards = new ArrayList<ShareBoard>();
		List<ShareBoard> donationBoards = new ArrayList<ShareBoard>();
		
		List<Board> boards = boardRepository.findAll(BoardSpecifications.isShareType("판매"));
		toShareBoard(boards, saleBoards);
		boards = boardRepository.findAll(BoardSpecifications.isShareType("대여"));
		toShareBoard(boards, rentalBoards);	
		boards = boardRepository.findAll(BoardSpecifications.isShareType("기부"));
		toShareBoard(boards, donationBoards);	
		
		myBikeList.setMyBikeBoard(saleBoards); 
		myBikeList.setMyBikeBoard(rentalBoards);	
		myBikeList.setMyBikeBoard(donationBoards);	
		
		map.put("myBikeList", myBikeList);
		return "jsonView";
		
	}
	
	@RequestMapping(value="/shareBoard/getRentalBoard", method = RequestMethod.GET)
	public String getRentalBoard(ModelMap map){
		MyBikeList myBikeList = new MyBikeList();
		List<ShareBoard> rentalBoards = new ArrayList<ShareBoard>();
		List<Board> boards = boardRepository.findAll(BoardSpecifications.isShareType("rent"));
		
		toShareBoard(boards, rentalBoards);	
		myBikeList.setMyBikeBoard(rentalBoards);
		
		
		map.put("myBikeList", myBikeList);
		return "jsonView";
		
	}
	
	public List<ShareBoard> toShareBoard(List<Board> boards, List<ShareBoard> shareBoards){
		
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
			
			shareBoards.add(shareboard);
		}
		
		return shareBoards;
	}
}
