package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.EventFile;

@Repository
public class EventFileDao extends BasePostDao<EventFile>{

	@Override
	public EventFile insert(final EventFile data) {
		final EventFile res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(EventFile.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<EventFile> getByEventId (final String eventId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM event_file ef ")
		.append("WHERE ef.event_id = :eventId");
		final List<EventFile> res = em().createNativeQuery(toStr(str), EventFile.class).setParameter("eventId", eventId).getResultList();
		return res;
		
		
	}

}
