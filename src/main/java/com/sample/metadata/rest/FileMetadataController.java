package com.sample.metadata.rest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sample.metadata.core.transfer.FileMetadataDO;
import com.sample.metadata.service.IFileMetadataService;

/**
 * The REST API controller to expose api for file Metadata management. It
 * basically exposes api to create and search metadata.
 */
@RestController
@RequestMapping("/metadata")
public class FileMetadataController {

	@Autowired
	IFileMetadataService fileMetadataService;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String home() {
		return "This is File Metadata Management";
	}

	/**
	 * Api to create File Metadata. It is assumed that the request body carries
	 * the file metadata in JSON format.
	 * 
	 * @param fileMetadataDO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse create(@RequestBody FileMetadataDO fileMetadataDO) {

		System.out.println(fileMetadataDO);
		ApiResponse apiResponse = new ApiResponse();

		try {
			this.fileMetadataService.createMetadata(fileMetadataDO);
		} catch (Exception ex) {
			apiResponse.markAsFailed();
			apiResponse.setResult(ex.getMessage());
		}

		return apiResponse;

	}

	/**
	 * Api to download the File.
	 * 
	 * @param request
	 * @param response
	 * @param filename
	 */
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public void downloadFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("filename") String filename) {

		String rootPath = System.getProperty("catalina.home");
		File dir = new File(rootPath + File.separator + "tmpFiles");
		File serverFile = new File(dir.getAbsolutePath() + File.separator + filename);

		if (serverFile.isFile() && serverFile.exists()) {

			try {

				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename=" + filename);

				Path path = FileSystems.getDefault().getPath(dir.getPath(), filename);
				Files.copy(path, response.getOutputStream());
				response.getOutputStream().flush();

			} catch (Exception ex) {
				ex.printStackTrace();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse uploadFileHandler(@RequestParam("filename") String filename, @RequestParam("size") Long size,
			@RequestParam("title") String title, @RequestParam("director") String director,
			@RequestParam("actor") String actor, @RequestParam("singer") String singer,
			@RequestParam("file") MultipartFile file) {

		ApiResponse apiResponse = new ApiResponse();

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + filename);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				System.out.println("Server File Location=" + serverFile.getAbsolutePath());

				FileMetadataDO fileMetadataDO = new FileMetadataDO();
				fileMetadataDO.setActor(actor);
				fileMetadataDO.setDirector(director);
				fileMetadataDO.setFilename(filename);
				fileMetadataDO.setSinger(singer);
				fileMetadataDO.setSize(size);
				fileMetadataDO.setTitle(title);

				this.fileMetadataService.createMetadata(fileMetadataDO);

			} catch (Exception e) {

				apiResponse.markAsFailed();
				apiResponse.setResult(e.getMessage());
			}
		}

		return apiResponse;
	}

	/**
	 * Api to search the File Metadata. It is assumed that the request body
	 * carries the file metadata in JSON format.
	 * 
	 * @param fileMetadataDO
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse search(@RequestBody FileMetadataDO fileMetadataDO) {
		List<FileMetadataDO> result = this.fileMetadataService.search(fileMetadataDO);
		return new ApiResponse(result);
	}

}
