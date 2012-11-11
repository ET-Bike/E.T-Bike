package com.etbike.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.etbike.server.domain.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>, JpaSpecificationExecutor<Reply>{

}
