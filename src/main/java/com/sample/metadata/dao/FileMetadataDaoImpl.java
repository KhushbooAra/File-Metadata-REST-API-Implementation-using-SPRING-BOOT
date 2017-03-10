package com.sample.metadata.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sample.metadata.core.transfer.FileMetadataDO;
import com.sample.metadata.domain.FileMetadata;

@Component
public class FileMetadataDaoImpl implements FileMetadataDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void save(FileMetadata fileMetadata) {
		sessionFactory.getCurrentSession().save(fileMetadata);

	}

	public List<FileMetadata> serachMetadata(FileMetadataDO fileMetadataDO) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileMetadata.class);

		if (fileMetadataDO.getActor() != null) {
			criteria.add(Restrictions.ilike("actor", fileMetadataDO.getActor()));
		}

		if (fileMetadataDO.getDirector() != null) {
			criteria.add(Restrictions.ilike("director", fileMetadataDO.getDirector()));
		}

		if (fileMetadataDO.getSinger() != null) {
			criteria.add(Restrictions.ilike("singer", fileMetadataDO.getSinger()));
		}

		if (fileMetadataDO.getFilename() != null) {
			criteria.add(Restrictions.ilike("filename", fileMetadataDO.getFilename()));
		}

		criteria.setMaxResults(100);

		return criteria.list();
	}

	public List<FileMetadata> searchNewFiles(Date createdTimeFrom, Date createdTimeTo) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileMetadata.class);

		criteria.add(Restrictions.ge("createdTime", createdTimeFrom));
		criteria.add(Restrictions.le("createdTime", createdTimeTo));

		return criteria.list();
	}

}
