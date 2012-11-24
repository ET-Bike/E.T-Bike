package com.etbike.server.persistence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.etbike.server.domain.model.MyCourseOverlayItem;
import com.etbike.server.domain.model.MyCourseOverlayItem_;

public class MyCourseOverlayItemSpecifications {

	public static Specification<MyCourseOverlayItem> isCoordinates(final Double sLatitude,final Double sLongitude){
		return new Specification<MyCourseOverlayItem>() {
			@Override
			public Predicate toPredicate(Root<MyCourseOverlayItem> root, CriteriaQuery<?> query, CriteriaBuilder cb){
				return cb.and(cb.equal(root.<Double>get(MyCourseOverlayItem_.sLatitude), sLatitude),
						      cb.equal(root.<Double>get(MyCourseOverlayItem_.sLongitude), sLongitude));
			}
		};
	}
	
}
