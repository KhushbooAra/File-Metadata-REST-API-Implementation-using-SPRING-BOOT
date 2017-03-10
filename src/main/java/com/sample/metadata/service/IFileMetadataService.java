package com.sample.metadata.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sample.metadata.core.transfer.FileMetadataDO;

@Component
public interface IFileMetadataService {

	@Transactional(transactionManager = "transactionManager")
	public void createMetadata(FileMetadataDO fileMetadataDO) throws Exception;

	@Transactional(transactionManager = "transactionManager")
	public List<FileMetadataDO> search(FileMetadataDO criteria);
	
	@Transactional(transactionManager = "transactionManager")
	public void checkForNewfiles();

}