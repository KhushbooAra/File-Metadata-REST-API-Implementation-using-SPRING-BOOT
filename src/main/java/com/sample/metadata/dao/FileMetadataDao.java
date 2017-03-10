package com.sample.metadata.dao;

import java.util.Date;
import java.util.List;

import com.sample.metadata.core.transfer.FileMetadataDO;
import com.sample.metadata.domain.FileMetadata;

public interface FileMetadataDao {

	void save(FileMetadata fileMetadata);

	List<FileMetadata> serachMetadata(FileMetadataDO fileMetadataDO);

	List<FileMetadata> searchNewFiles(Date createdTimeFrom, Date createdTimeTo);
}
