package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.PollingChoice;

@Repository
public class PollingChoiceDao extends MasterDao<PollingChoice>{

	@Override
	public Optional<PollingChoice> getById(final String id) {
		final PollingChoice res = getById(PollingChoice.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public PollingChoice getRefById(final String id) {
		final PollingChoice res = getByIdRef(PollingChoice.class, id);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PollingChoice> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM polling_choice pc ")
			.append(" WHERE pc.is_active = true");
		final List<PollingChoice> res = em().createNativeQuery(toStr(str), PollingChoice.class).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<PollingChoice> getChoiceByDetail(final String detailId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM polling_choice pc ")
		.append(" WHERE pc.polling_detail_id = :detailId AND pc.is_active = true");
		final List<PollingChoice> res = em().createNativeQuery(toStr(str), PollingChoice.class)
				.setParameter("detailId", detailId)
				.getResultList();
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
	public boolean delete(final String id) {
		return deleteById(PollingChoice.class, id);
	}

	@Override
	public PollingChoice getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(PollingChoice.class, id);
	}

	
	
	
	
	
	

}
