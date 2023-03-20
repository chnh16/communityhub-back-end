package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Event;

@Repository
public class EventDao extends MasterDao<Event>{

	@Override
	public Optional<Event> getById(final String id) {
		final Event res = getById(Event.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Event getRefById(final String id) {
		final Event res = getByIdRef(Event.class, id);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Event> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_event")
		.append(" WHERE is_active = TRUE");
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
	public boolean delete(final String id) {
		return deleteById(Event.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> getByCategoryId (String id){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_event e ")
		.append("WHERE e.category_id = :id")
		.append(" AND e.is_active = TRUE");
		final List<Event> res = em().createNativeQuery(toStr(str), Event.class).setParameter("id", id).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> getByPriceAsc(){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_event")
		.append(" WHERE is_active = TRUE")
		.append(" ORDER BY price ASC");
		final List<Event> res = em().createNativeQuery(toStr(str), Event.class).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> getByPriceDesc(){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_event")
		.append(" WHERE is_active = TRUE")
		.append(" ORDER BY price DESC");
		final List<Event> res = em().createNativeQuery(toStr(str), Event.class).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> getEvent(final Integer limit, final Integer offset) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM event e ")
			.append(" WHERE c.is_active = true");
		final List<Event> res = em().createNativeQuery(toStr(str).toString(), Event.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		return res;
	}
	
	public int getTotalEvent() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(e.id) FROM event e ")
			.append(" WHERE e.is_active = true");
		
		final int totalEvent = Integer.valueOf(ConnHandler.getManager().createNativeQuery(toStr(str)
				.toString())
				.getSingleResult().toString());
		return totalEvent;
	}
	
	@SuppressWarnings("unchecked")
	public Optional<Event> getArticleById(final String id) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_event e ")
			.append(" WHERE e.id = :id AND e.is_active = true");
		final List<Event> res = em().createNativeQuery(toStr(str), Event.class)
				.setParameter("id", id)
				.getResultList();
		return Optional.ofNullable(res.get(0));
	}

	@Override
	public Event getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(Event.class, id);
	}

}
