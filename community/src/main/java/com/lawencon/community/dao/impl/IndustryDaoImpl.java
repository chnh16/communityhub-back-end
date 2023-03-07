package com.lawencon.community.dao.impl;

import java.util.List;
import java.util.Optional;

import com.lawencon.community.dao.MasterDao;
import com.lawencon.community.model.Industry;

public class IndustryDaoImpl extends BaseDaoImpl implements MasterDao<Industry>{
	
	@Override
	public Optional<Industry> getById(final Long id) {
		final Industry res = getById(Industry.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<Industry> getRefById(final Long id) {
		final Industry res = getByIdRef(Industry.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Industry> getAll() {
		final String sql = "SELECT * FROM industry" 
				+ " WHERE is_active = true";
		final List<Industry> res = em().createNativeQuery(sql, Industry.class).getResultList();
		return res;
	}

	@Override
	public Industry update(final Industry data) {
		final Industry res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(Industry.class, id);
	}

	@Override
	public Industry insert(final Industry data) {
		final Industry res = saveAndFlush(data);
		return res;
	}

}
