package com.sample.metadata.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sample.metadata.core.transfer.FileMetadataDO;
import com.sample.metadata.dao.FileMetadataDao;
import com.sample.metadata.domain.FileMetadata;

@Component
public class FileMetadataServiceImpl implements IFileMetadataService {
	@Autowired
	FileMetadataDao fileMetadataDao;

	public void createMetadata(FileMetadataDO fileMetadataDO) throws Exception {
		this.validateCreateFileMetadata(fileMetadataDO);
		FileMetadata fileMetadata = assembleFileMetadata(fileMetadataDO);
		this.fileMetadataDao.save(fileMetadata);
	}

	private FileMetadata assembleFileMetadata(FileMetadataDO fileMetadataDO) {
		FileMetadata fileMetadata = new FileMetadata();
		fileMetadata.setActor(fileMetadataDO.getActor());
		fileMetadata.setDirector(fileMetadataDO.getDirector());
		fileMetadata.setFilename(fileMetadataDO.getFilename());
		fileMetadata.setSinger(fileMetadataDO.getSinger());
		fileMetadata.setSize(fileMetadataDO.getSize());
		fileMetadata.setTitle(fileMetadataDO.getTitle());
		fileMetadata.setCreatedTime(new Date());
		
		return fileMetadata;
	}

	private void validateCreateFileMetadata(FileMetadataDO parameter) throws Exception {
		if (parameter == null) {
			throw new Exception("Null File Metadata");
		}
		if (parameter.getActor() == null) {
			throw new Exception("Actor is required");
		}
		if (parameter.getDirector() == null) {
			throw new Exception("Director is required");
		}
		if (parameter.getFilename() == null) {
			throw new Exception("Filename is required");
		}

		if (parameter.getSinger() == null) {
			throw new Exception("Singer is required");
		}
		if (parameter.getSize() == null) {
			throw new Exception("Size is required");
		}
		if (parameter.getTitle() == null) {
			throw new Exception("Title is required");
		}
	}

	public List<FileMetadataDO> search(FileMetadataDO criteria) {
		if(criteria == null){
			criteria = new FileMetadataDO();
		}
		
		List<FileMetadataDO> dtoList = new ArrayList<FileMetadataDO>();
		
		List<FileMetadata> entities = fileMetadataDao.serachMetadata(criteria);
		if(entities != null){
			
			for(FileMetadata fileMetadata : entities){
				
				FileMetadataDO fileMetadataDO= new FileMetadataDO();
				
				fileMetadataDO.setActor(fileMetadata.getActor());
				fileMetadataDO.setDirector(fileMetadata.getDirector());
				fileMetadataDO.setFilename(fileMetadata.getFilename());
				fileMetadataDO.setSinger(fileMetadata.getSinger());
				fileMetadataDO.setSize(fileMetadata.getSize());
				
				dtoList.add(fileMetadataDO);
			}
		}
		
		return dtoList;
	}
		
	public void checkForNewfiles(){
		
		
		Calendar currentTime = Calendar.getInstance();
		Date createdTimeTo = currentTime.getTime();
		currentTime.set(Calendar.HOUR_OF_DAY, currentTime.get(Calendar.HOUR_OF_DAY) - 1);
		
		Date createdTimeFrom = currentTime.getTime();
		
		List<FileMetadata> newFiles = fileMetadataDao.searchNewFiles(createdTimeFrom, createdTimeTo);
		
		if(newFiles != null){
			
			System.out.println("Number of New files : " + newFiles.size());
			
			for(FileMetadata fileMetadata : newFiles){
				
				System.out.println("Send Email for new File : " + fileMetadata.getFilename());
				
			}
			
		}
	}
}


