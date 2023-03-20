package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Position;

@Repository
public class PositionDao extends MasterDao<Position>{
	
	@Override
	public Optional<Position> getById(final String id) {
		final Position res = getById(Position.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Position getRefById(final String id) {
		final Position res = getByIdRef(Position.class, id);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Position> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_position ").append(" WHERE is_active = true");
		final List<Position> res = em().createNativeQuery(toStr(str), Position.class).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public Optional<Position> getPositionById(final String id) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_position p ")
			.append(" WHERE p.id = :id AND p.is_active = true");
		final List<Position> res = em().createNativeQuery(toStr(str), Position.class)
				.setParameter("id", id)
				.getResultList();
		return Optional.ofNullable(res.get(0));
	}
	
	@SuppressWarnings("unchecked")
	public List<Position> getPosition(final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_position p ")
			.append(" WHERE p.is_active = true");
		final List<Position> res = em().createNativeQuery(toStr(str).toString(), Position.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		return res;
	}

	public int getTotalPosition() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(p.id) FROM t_position p ")
			.append(" WHERE p.is_active = true");
		
		final int totalPosition = Integer.valueOf(ConnHandler.getManager().createNativeQuery(toStr(str)
				.toString())
				.getSingleResult().toString());
		return totalPosition;
	}
	
	@Override
	public Position update(final Position data) {
		final Position res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(Position.class, id);
	}

	@Override
	public Position insert(final Position data) {
		final Position res = saveAndFlush(data);
		return res;
	}

	@Override
	public Position getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(Position.class, id);
	}

}
