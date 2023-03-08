package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.Event;

@Repository
public class EventDao extends MasterDao<Event>{

	@Override
	public Optional<Event> getById(final Long id) {
		final Event res = getById(Event.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<Event> getRefById(final Long id) {
		final Event res = getByIdRef(Event.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Event> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_event")
		.append(" WHERE is_active = true");
		final List<Event> res = em().createNativeQuery(toStr(str), Event.class).getResultList();
		return res;
	}

	@Override
	public Event update(final Event data) {
		final Event res = saveAndFlush(data);
		return res;
	}

	@Override
	public Event insert(final Event data) {
		final Event res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(Event.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> getByCategoryId (final Long categoryId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_event e ")
		.append("WHERE e.category_id = :categoryId");
		final List<Event> res = em().createNativeQuery(toStr(str), Event.class).setParameter("categoryId", categoryId).getResultList();
		return res;
	}

}
