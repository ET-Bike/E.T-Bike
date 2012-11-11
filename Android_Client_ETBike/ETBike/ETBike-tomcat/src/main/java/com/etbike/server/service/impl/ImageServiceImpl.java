package com.etbike.server.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.etbike.server.domain.model.UploadedFile;
import com.etbike.server.persistence.FileRepository;
import com.etbike.server.service.ImageService;
import com.etbike.server.support.img.ThumbnailCreatable;

@Service
public class ImageServiceImpl implements ImageService {
	@Resource(name = "defaultProperties")
	protected Properties defaultProperties;
	
	@Autowired private ThumbnailCreatable thumbnailCreatable;
	@Autowired private FileRepository fileRepository;
	
	@Override
	public Map<String, String> uploadImage(MultipartFile multipartFile) {
		String uploadPath = defaultProperties.getProperty("file.img.path");
		Map<String, String> fileInfo = upload(multipartFile, uploadPath);	

		String uploadedFileFullPath = fileInfo.remove("path");
		int dotIndex = uploadedFileFullPath.lastIndexOf(".");
		String thumbnail50FullPath = new StringBuffer(uploadedFileFullPath.substring(0, dotIndex)).append("_thumb50").append(uploadedFileFullPath.substring(dotIndex)).toString();
		String thumbnail100FullPath = new StringBuffer(uploadedFileFullPath.substring(0, dotIndex)).append("_thumb100").append(uploadedFileFullPath.substring(dotIndex)).toString();
		String thumbnail150FullPath = new StringBuffer(uploadedFileFullPath.substring(0, dotIndex)).append("_thumb150").append(uploadedFileFullPath.substring(dotIndex)).toString();
		String thumbnail200FullPath = new StringBuffer(uploadedFileFullPath.substring(0, dotIndex)).append("_thumb200").append(uploadedFileFullPath.substring(dotIndex)).toString();
		thumbnailCreatable.create(uploadedFileFullPath, thumbnail50FullPath, 50, -1);
		thumbnailCreatable.create(uploadedFileFullPath, thumbnail100FullPath, 100, -1);
		thumbnailCreatable.create(uploadedFileFullPath, thumbnail150FullPath, 150, -1);
		thumbnailCreatable.create(uploadedFileFullPath, thumbnail200FullPath, 200, -1);

		return fileInfo;
	}

	@Override
	@Transactional(readOnly = true)
	public Object getUploadedImage(Long id) {
		return fileRepository.findOne(id);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	private Map<String, String> upload(MultipartFile multipartFile, String uploadPath) {
		String fileUniqueName = String.valueOf(System.nanoTime());

		String originFileName = multipartFile.getOriginalFilename();		
	    String uploadedFileFullPath = new StringBuffer(uploadPath).append(fileUniqueName).append("_").append(originFileName).toString();

		String uploadedPath = uploadedFileFullPath.substring(0, uploadedFileFullPath.lastIndexOf("/"));
    	File uploadedPathFile = new File(uploadedPath);
    	if(!uploadedPathFile.exists()){
    		uploadedPathFile.mkdirs();
    	}
	    
	    File uploadedFile = new File(uploadedFileFullPath);
	    try {
			FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(uploadedFile));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	    
	    UploadedFile uploadedFileBean = new UploadedFile();
	    uploadedFileBean.setFileName(originFileName);
	    uploadedFileBean.setFilePath(uploadedFileFullPath);
	    uploadedFileBean.setFileSize(String.valueOf(multipartFile.getSize()));

	    fileRepository.save(uploadedFileBean);
	    
		Map<String, String> fileInfo = new HashMap<String, String>();
		fileInfo.put("name", originFileName);	
		fileInfo.put("path", uploadedFileFullPath);	
		fileInfo.put("id", String.valueOf(uploadedFileBean.getId()));

		return fileInfo;
	}
}
