package com.lawencon.community.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.dao.EventDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.model.Event;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Membership;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.event.PojoEventReqInsert;
import com.lawencon.community.pojo.membership.PojoMembershipInsertReq;
import com.lawencon.community.util.Generate;

@Service
public class EventService {
	
	private EventDao eventDao;
	private CategoryDao categoryDao;
	private FileDao fileDao;
	
	public EventService(EventDao eventDao, CategoryDao categoryDao, FileDao fileDao) {
		this.eventDao = eventDao;
		this.categoryDao = categoryDao;
		this.fileDao = fileDao;
	}
	
	public Event insert(final Event data) {
		Event eventInsert = null;

		try {
			ConnHandler.begin();
			eventInsert = eventDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eventInsert;
	}
	
	public Event update(final Event data) {
		Event eventUpdate = null;

		try {
			ConnHandler.begin();
			eventUpdate = eventDao.update(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return eventUpdate;
	}

	public Optional<Event> getById(final String id) {
		return eventDao.getById(id);
	}

	public List<Event> getAll() {
		return eventDao.getAll();
	}

	public boolean deleteById(final String id) {
		boolean eventDelete = false;

		try {
			ConnHandler.begin();
			eventDelete = eventDao.deleteById(Event.class, id);
			ConnHandler.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return eventDelete;

	}

	public Event getByIdAndDetach(final String id) {
		return eventDao.getByIdAndDetach(Event.class, id);
	}
	
	public PojoInsertRes insert(final PojoEventReqInsert data) {
		final Event event = new Event();
		final String generateId = Generate.generateCode(5);

		event.setEventCode(generateId);
		event.setEventName(data.getEventName());
		event.setProvider(data.getProvider());
		event.setLocationName(data.getLocationName());
		event.setStartDate(data.getStartDate());
		event.setEndDate(data.getEndDate());
		event.setPrice(data.getPrice());
		
		final Category category = categoryDao.getByIdRef(Category.class ,data.getCategoryId());
		category.setId(data.getCategoryId());
		event.setCategory(category);
		
		final File file = fileDao.getByIdRef(File.class, data.getFileId());
		file.setId(data.getFileId());
		event.setFile(file);

		Event eventInsert = null;
		eventInsert = insert(event);

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(eventInsert.getId());
		pojoInsertRes.setMessage("Success");
		return pojoInsertRes;
	}

}
