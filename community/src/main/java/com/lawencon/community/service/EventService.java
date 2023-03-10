package com.lawencon.community.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.dao.EventDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.model.Event;
import com.lawencon.community.model.File;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.event.PojoEventReqInsert;
import com.lawencon.community.util.Generate;
import com.lawencon.security.principal.PrincipalService;

@Service
public class EventService {
	
	private EventDao eventDao;
	private CategoryDao categoryDao;
	private FileDao fileDao;
	private UserDao userDao;
	
	
	@Inject
	private PrincipalService principalService;
	
	public EventService(EventDao eventDao, CategoryDao categoryDao, FileDao fileDao, PrincipalService principalService, UserDao userDao) {
		this.eventDao = eventDao;
		this.categoryDao = categoryDao;
		this.fileDao = fileDao;
		this.principalService = principalService;
		this.userDao = userDao;
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
	
	public Event getRefById(final String id) {
		return eventDao.getRefById(id);
	}
	
	public PojoInsertRes insert(final PojoEventReqInsert data) {
		final Event event = new Event();
		final String generateId = Generate.generateCode(5);
		
		final User user = userDao.getById(principalService.getAuthPrincipal()).get();
		user.setId(principalService.getAuthPrincipal());
		event.setUser(user);

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
		
		final File fileInsert = new File();
		fileInsert.setFileName(data.getFile().getFileName());
		fileInsert.setFileExtension(data.getFile().getFileExtension());
		fileInsert.setFileContent(data.getFile().getFileContent());
		
		ConnHandler.begin();
		final File file = fileDao.insert(fileInsert);
		ConnHandler.commit();
		event.setFile(file);

		Event eventInsert = null;
		eventInsert = insert(event);

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(eventInsert.getId());
		pojoInsertRes.setMessage("Success");
		return pojoInsertRes;
	}

}
