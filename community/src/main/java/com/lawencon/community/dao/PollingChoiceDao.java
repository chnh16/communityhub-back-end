package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.PollingChoice;

@Repository
public class PollingChoiceDao extends MasterDao<PollingChoice>{

	@Override
	public Optional<PollingChoice> getById(final Long id) {
		final PollingChoice res = getById(PollingChoice.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<PollingChoice> getRefById(final Long id) {
		final PollingChoice res = getByIdRef(PollingChoice.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PollingChoice> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM polling_answer pa")
			.append(" WHERE is_active = true");
		final List<PollingChoice> res = em().createNativeQuery(toStr(str), PollingChoice.class).getResultList();
		return res;
	}

	@Override
	public PollingChoice update(final PollingChoice data) {
		final PollingChoice res = saveAndFlush(data);
		return res;
	}

	@Override
	public PollingChoice insert(final PollingChoice data) {
		final PollingChoice res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(PollingChoice.class, id);
	}

	
	
	
	
	
	

}