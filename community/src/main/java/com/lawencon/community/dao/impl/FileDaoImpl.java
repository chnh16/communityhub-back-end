package com.lawencon.community.dao.impl;

import java.util.List;
import java.util.Optional;

import com.lawencon.community.dao.FileDao;
import com.lawencon.community.model.File;

public class FileDaoImpl extends BaseDaoImpl implements FileDao{
	
	@Override
	public Optional<File> getById(final Long id) {
		final File res = getById(File.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<File> getRefById(final Long id) {
		final File res = getByIdRef(File.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<File> getAll() {
		final String sql = "SELECT * FROM file" 
				+ " WHERE is_active = true";
		final List<File> res = em().createNativeQuery(sql, File.class).getResultList();
		return res;
	}

	@Override
	public File update(final File data) {
		final File res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(File.class, id);
	}

	@Override
	public File insert(final File data) {
		final File res = saveAndFlush(data);
		return res;
	}

}
