package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.Position;

@Repository
public class PositionDao extends MasterDao<Position>{
	
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
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM position ").append(" WHERE is_active = true");
		final List<Position> res = em().createNativeQuery(toStr(str), Position.class).getResultList();
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
