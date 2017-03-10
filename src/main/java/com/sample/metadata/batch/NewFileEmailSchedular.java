package com.sample.metadata.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sample.metadata.service.IFileMetadataService;

@Component
public class NewFileEmailSchedular {

	@Autowired
	private IFileMetadataService fileMetadataService;

	/**
	 * We can configure the scheduler time here. for now, we have enabled only 5
	 * seconds internal, but it would be increased as 45 mins on the Prod
	 * server.
	 */
	@Scheduled(fixedRate = 5000)
	public void emailNotifier() {

		System.out.println("*********** Starting email notifier for New Files **********");

		this.fileMetadataService.checkForNewfiles();
	}
}