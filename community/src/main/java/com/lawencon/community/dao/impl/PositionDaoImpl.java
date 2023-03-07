package com.lawencon.community.dao.impl;

import java.util.List;
import java.util.Optional;

import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.model.Position;

public class PositionDaoImpl extends BaseDaoImpl implements PositionDao{
	
	@Override
	public Optional<Position> getById(final Long id) {
		final Position res = getById(Position.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<Position> getRefById(final Long id) {
		final Position res = getByIdRef(Position.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Position> getAll() {
		final String sql = "SELECT * FROM position" 
				+ " WHERE is_active = true";
		final List<Position> res = em().createNativeQuery(sql, Position.class).getResultList();
		return res;
	}

	@Override
	public Position update(final Position data) {
		final Position res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(Position.class, id);
	}

	@Override
	public Position insert(final Position data) {
		final Position res = saveAndFlush(data);
		return res;
	}

}
