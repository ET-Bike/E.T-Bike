package com.etbike.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.etbike.server.domain.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board>{

}
