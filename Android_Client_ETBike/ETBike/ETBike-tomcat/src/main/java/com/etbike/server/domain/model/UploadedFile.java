package com.etbike.server.domain.model;

import javax.persistence.Entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class UploadedFile extends AbstractPersistable<Long>{
	private static final long serialVersionUID = 7812861868688385351L;
	
	private String fileName;
	private String filePath;
	private String fileSize;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
