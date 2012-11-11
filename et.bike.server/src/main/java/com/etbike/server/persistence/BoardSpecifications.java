package com.etbike.server.persistence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.etbike.server.domain.model.Board;
import com.etbike.server.domain.model.BoardCategory;
import com.etbike.server.domain.model.Board_;

public class BoardSpecifications {

	public static Specification<Board> contentIsLike(final String searchTerm) {
		return new Specification<Board>() {
			@Override
			public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String>get(Board_.content), getLikePattern(searchTerm));
			}
		};
	}
	
	public static Specification<Board> isWriterName(final String writer){
		return new Specification<Board>() {
			@Override
			public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder cb){
				return cb.equal(root.<String>get(Board_.writer), writer);
			}
		};
	}
	
	public static Specification<Board> titleIsLike(final String searchTerm){
		return new Specification<Board>(){
			@Override
			public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String>get(Board_.title), getLikePattern(searchTerm));
			}
		};
	}
	
	public static Specification<Board> titleOrContentIsLike(final String searchTerm){
		return new Specification<Board>(){
			@Override
			public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.or(
						cb.like(root.<String>get(Board_.title), getLikePattern(searchTerm)), 
						cb.like(root.<String>get(Board_.content), getLikePattern(searchTerm)));
			}
		};
	}
	
	public static Specification<Board> categoryIs(final BoardCategory boardCategory){
		return new Specification<Board>(){
			@Override
			public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get(Board_.category), boardCategory);
			}
		};
	}

	private static String getLikePattern(final String searchTerm) {
		StringBuilder pattern = new StringBuilder();
		pattern.append("%");
		pattern.append(searchTerm);
		pattern.append("%");
		return pattern.toString();
	}
}
