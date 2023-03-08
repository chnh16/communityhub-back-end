package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.PollingDetail;

@Repository
public class PollingDetailDao extends MasterDao<PollingDetail>{

	@Override
	Optional<PollingDetail> getById(Long id) {
		final PollingDetail res = getById(PollingDetail.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	Optional<PollingDetail> getRefById(Long id) {
		final PollingDetail res = getByIdRef(PollingDetail.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	List<PollingDetail> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM polling_detail")
			.append(" WHERE is_active = true");
		final List<PollingDetail> res = em().createNativeQuery(toStr(str), PollingDetail.class).getResultList();
		return res;
	}

	@Override
	PollingDetail update(PollingDetail data) {
		final PollingDetail res = saveAndFlush(data);
		return res;
	}

	@Override
	PollingDetail insert(PollingDetail data) {
		final PollingDetail res = saveAndFlush(data);
		return res;
	}

	@Override
	boolean delete(Long id) {
		return deleteById(PollingDetail.class, id);
	}

	
	
	

}
