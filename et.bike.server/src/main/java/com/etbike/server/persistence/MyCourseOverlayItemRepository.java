package com.etbike.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.etbike.server.domain.model.MyCourseOverlayItem;

public interface MyCourseOverlayItemRepository extends JpaRepository<MyCourseOverlayItem, Long>, JpaSpecificationExecutor<MyCourseOverlayItem>{

}
 