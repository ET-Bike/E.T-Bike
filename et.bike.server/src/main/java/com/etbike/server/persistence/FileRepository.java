package com.etbike.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.etbike.server.domain.model.Board;
import com.etbike.server.domain.model.UploadedFile;

public interface FileRepository extends JpaRepository<UploadedFile, Long> ,JpaSpecificationExecutor<UploadedFile>{

}
