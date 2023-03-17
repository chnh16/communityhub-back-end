package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.Industry;

@Repository
public class IndustryDao extends MasterDao<Industry>{
	
	@Override
	public Optional<Industry> getById(final String id) {
		final Industry res = getById(Industry.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Industry getRefById(final String id) {
		final Industry res = getByIdRef(Industry.class, id);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Industry> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM industry ").append(" WHERE is_active = true");
		final List<Industry> res = em().createNativeQuery(toStr(str), Industry.class).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public Optional<Industry> getIndustryById(final String id) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM industry i ")
			.append(" WHERE i.id = :id AND i.is_active = true");
		final List<Industry> res = em().createNativeQuery(toStr(str), Industry.class)
				.setParameter("id", id)
				.getResultList();
		return Optional.ofNullable(res.get(0));
	}

	@Override
	public Industry update(final Industry data) {
		final Industry res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(Industry.class, id);
	}
	
	@Override
	public Industry insert(final Industry data) {
		final Industry res = saveAndFlush(data);
		return res;
	}

	@Override
	public Industry getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(Industry.class, id);
	}

}
