package com.lawencon.community.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.community.dao.FileDao;
import com.lawencon.community.model.File;

@Service
public class FileService {
	
	@Autowired
	private FileDao fileDao;


	public Optional<File> getById(final String id) {
		return fileDao.getById(id);
	}

}
