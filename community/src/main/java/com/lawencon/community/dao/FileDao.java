package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.File;

@Repository
public class FileDao extends MasterDao<File>{
	
	@Override
	public Optional<File> getById(final String id) {
		final File res = getById(File.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public File getRefById(final String id) {
		final File res = getByIdRef(File.class, id);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<File> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM file ").append(" WHERE is_active = true");
		final List<File> res = em().createNativeQuery(toStr(str), File.class).getResultList();
		return res;
	}

	@Override
	public File update(final File data) {
		final File res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(File.class, id);
	}

	@Override
	public File insert(final File data) {
		final File res = saveAndFlush(data);
		return res;
	}

	@Override
	public File getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(File.class, id);
	}

}
