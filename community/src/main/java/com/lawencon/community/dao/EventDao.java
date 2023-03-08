package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.Event;

@Repository
public class EventDao extends MasterDao<Event>{

	@Override
	Optional<Event> getById(Long id) {
		final Event res = getById(Event.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	Optional<Event> getRefById(Long id) {
		final Event res = getByIdRef(Event.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	List<Event> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_event")
		.append(" WHERE is_active = true");
		final List<Event> res = em().createNativeQuery(toStr(str), Event.class).getResultList();
		return res;
	}

	@Override
	Event update(Event data) {
		final Event res = saveAndFlush(data);
		return res;
	}

	@Override
	Event insert(Event data) {
		final Event res = saveAndFlush(data);
		return res;
	}

	@Override
	boolean delete(Long id) {
		return deleteById(Event.class, id);
	}
	
	@SuppressWarnings("unchecked")
	List<Event> getByCategoryId (Long categoryId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_event e ")
		.append("WHERE e.category_id = :categoryId");
		final List<Event> res = em().createNativeQuery(toStr(str), Event.class).setParameter("categoryId", categoryId).getResultList();
		return res;
	}

}
