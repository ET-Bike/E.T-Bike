package com.etbike.server.persistence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.etbike.server.domain.model.UploadedFile_;
import com.etbike.server.domain.model.UploadedFile;

public class FileSpecifications {


	public static Specification<UploadedFile> isfileName(final String fileName){
		return new Specification<UploadedFile>(){
			@Override
			public Predicate toPredicate(Root<UploadedFile> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.<String>get(UploadedFile_.fileName), fileName);
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
