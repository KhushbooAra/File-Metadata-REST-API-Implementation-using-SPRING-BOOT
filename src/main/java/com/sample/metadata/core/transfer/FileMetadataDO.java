package com.sample.metadata.core.transfer;

import java.sql.Date;

public class FileMetadataDO {

	private long idfilemetadata;
	private String filename;
	private Long size;
	private String title;
	private String director;
	private String actor;
	private String singer;

	private Date createdTimeFrom = null;
	private Date createdTimeTo = null;

	public long getIdfilemetadata() {
		return idfilemetadata;
	}

	public void setIdfilemetadata(long idfilemetadata) {
		this.idfilemetadata = idfilemetadata;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public Date getCreatedTimeFrom() {
		return createdTimeFrom;
	}

	public void setCreatedTimeFrom(Date createdTimeFrom) {
		this.createdTimeFrom = createdTimeFrom;
	}

	public Date getCreatedTimeTo() {
		return createdTimeTo;
	}

	public void setCreatedTimeTo(Date createdTimeTo) {
		this.createdTimeTo = createdTimeTo;
	}

	@Override
	public String toString() {
		return "FileMetadataDO [idfilemetadata=" + idfilemetadata + ", filename=" + filename + ", size=" + size
				+ ", title=" + title + ", director=" + director + ", actor=" + actor + ", singer=" + singer + "]";
	}

}
