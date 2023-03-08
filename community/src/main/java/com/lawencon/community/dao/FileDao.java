package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.File;

@Repository
public class FileDao extends MasterDao<File>{
	
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
