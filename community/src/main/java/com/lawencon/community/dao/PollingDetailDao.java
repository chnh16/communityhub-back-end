package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.PollingDetail;

@Repository
public class PollingDetailDao extends MasterDao<PollingDetail>{

	@Override
	public Optional<PollingDetail> getById(final String id) {
		final PollingDetail res = getById(PollingDetail.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<PollingDetail> getRefById(final String id) {
		final PollingDetail res = getByIdRef(PollingDetail.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PollingDetail> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM polling_detail")
			.append(" WHERE is_active = true");
		final List<PollingDetail> res = em().createNativeQuery(toStr(str), PollingDetail.class).getResultList();
		return res;
	}

	@Override
	public PollingDetail update(final PollingDetail data) {
		final PollingDetail res = saveAndFlush(data);
		return res;
	}

	@Override
	public PollingDetail insert(final PollingDetail data) {
		final PollingDetail res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(PollingDetail.class, id);
	}

	@Override
	public PollingDetail getByIdAndDetach(final Long id) {
		return super.getByIdAndDetach(PollingDetail.class, id);
	}

	
	
	

}
