package com.etbike.server.service.impl;

import static com.etbike.server.persistence.BoardSpecifications.categoryIs;
import static com.etbike.server.persistence.BoardSpecifications.titleOrContentIsLike;
import static com.etbike.server.persistence.ReplySpecifications.boardIs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.etbike.server.domain.model.Board;
import com.etbike.server.domain.model.BoardCategory;
import com.etbike.server.domain.model.Reply;
import com.etbike.server.persistence.BoardRepository;
import com.etbike.server.persistence.ReplyRepository;
import com.etbike.server.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService {

	private static final int BOARD_ITEM_COUNT_PER_PAGE = 15;
	private static final int REPLY_ITEM_COUNT_PER_PAGE = 5;
	private final Sort sortDescById = new Sort(Sort.Direction.DESC, "id");

	@Autowired private BoardRepository boardRepository;
	@Autowired private ReplyRepository replyRepository;

//	@Cacheable(value="boardCache")
	@Override
	public Object getBoards(String category, Integer page, String searchTerm) {
		return boardRepository.findAll(getBoardSpecifications(category, searchTerm), getBoardPageSpecification(page));
	}

	private Specifications<Board> getBoardSpecifications(String category, String searchTerm) {
		Specifications<Board> spec = Specifications.where(categoryIs(getBoardCategory(category)));
		return StringUtils.hasText(searchTerm) ? spec.and(titleOrContentIsLike(searchTerm)) : spec;
	}
	
	public BoardCategory getBoardCategory(String category){
		BoardCategory boardCategory = null; 
		try {
			boardCategory = BoardCategory.valueOf(category.toUpperCase());
		} catch (Exception e) {
			boardCategory = BoardCategory.NOTICE;
		}
		
		return boardCategory;
	}

	private PageRequest getBoardPageSpecification(Integer page) {
		return new PageRequest(page-1, BOARD_ITEM_COUNT_PER_PAGE, sortDescById);
	}
	
//	@CacheEvict(value="boardCache", allEntries=true)
	@Transactional(readOnly=false, rollbackFor=Exception.class)
	public Board saveBoard(Board board) {
        return boardRepository.save(board);
	}

//	@CacheEvict(value="boardCache", allEntries=true)
	@Transactional(readOnly=false, rollbackFor=Exception.class)
	public void deleteBoard(Long id) {
		boardRepository.delete(id);
	}

//	@Cacheable(value="boardCache")
	@Transactional(readOnly=true)
	public Board readById(Long id) {
		return boardRepository.findOne(id);
	}

	
	
	@Override
	public Object getReplies(Long boardId, Integer page) {
		Board board = boardRepository.findOne(boardId);
		return replyRepository.findAll(Specifications.where(boardIs(board)), getReplyPageSpecification(page));
	}

	private PageRequest getReplyPageSpecification(Integer page) {
		return new PageRequest(page-1, REPLY_ITEM_COUNT_PER_PAGE, sortDescById);
	}

	@Override
	public void saveReply(Reply model) {
		replyRepository.save(model);
	}

	@Override
	public void deleteReply(Long id) {
		replyRepository.delete(id);
	}

}
