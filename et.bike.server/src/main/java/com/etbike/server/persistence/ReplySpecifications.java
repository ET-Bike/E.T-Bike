package com.etbike.server.persistence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.etbike.server.domain.model.Board;
import com.etbike.server.domain.model.Reply;
import com.etbike.server.domain.model.Reply_;

public class ReplySpecifications {
	
	public static Specification<Reply> boardIs(final Board board){
		return new Specification<Reply>(){
			@Override
			public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get(Reply_.board), board);
			}
		};
	}
}
