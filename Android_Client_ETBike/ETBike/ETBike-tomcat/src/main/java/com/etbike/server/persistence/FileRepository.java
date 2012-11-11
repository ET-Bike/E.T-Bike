package com.etbike.server.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etbike.server.domain.model.UploadedFile;

public interface FileRepository extends JpaRepository<UploadedFile, Long> {

}
