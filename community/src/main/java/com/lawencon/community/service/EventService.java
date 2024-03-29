package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.TypeProductEnum;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.dao.EventDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.TypeProductDao;
import com.lawencon.community.dao.UserEventDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.model.Event;
import com.lawencon.community.model.File;
import com.lawencon.community.model.TypeProduct;
import com.lawencon.community.model.User;
import com.lawencon.community.model.UserEvent;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.event.PojoEventGetAllResData;
import com.lawencon.community.pojo.event.PojoEventReqInsert;
import com.lawencon.community.pojo.event.PojoEventReqUpdate;
import com.lawencon.community.pojo.event.PojoEventResGetAll;
import com.lawencon.community.pojo.userevent.PojoUserEventGetByUserIdRes;
import com.lawencon.community.pojo.userevent.PojoUserEventInsertReq;
import com.lawencon.community.util.Generate;
import com.lawencon.security.principal.PrincipalService;

@Service
public class EventService extends ValidationService<Event>{

	@Autowired
	private EventDao eventDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private TypeProductDao typeProductDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserEventDao userEventDao;

	@Inject
	private PrincipalService principalService;

	public Event insert(final Event data) {
		Event eventInsert = null;

		try {
			ConnHandler.begin();
			valNotNullable(data);
			valMaxLength(data);
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
			valNotNullable(data);
			valMaxLength(data);
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
		final TypeProduct typeProduct = typeProductDao.getTypeByCode(TypeProductEnum.EVENT.getTypeCode()).get();
		final User user = userService.getByRefId(principalService.getAuthPrincipal());
		user.setId(principalService.getAuthPrincipal());
		event.setUser(user);

		event.setEventCode(generateId);
		event.setEventName(data.getEventName());
		event.setProvider(data.getProvider());
		event.setTypeProduct(typeProduct);
		event.setLocationName(data.getLocationName());
		event.setStartDate(data.getStartDate());
		event.setEndDate(data.getEndDate());
		event.setPrice(data.getPrice());

		final Category category = categoryDao.getByIdRef(Category.class, data.getCategoryId());
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

	public List<PojoEventResGetAll> getAllEvent(String category, String price, Integer limit, Integer offset) {
		final List<PojoEventResGetAll> pojos = new ArrayList<>();
		List<Event> res = new ArrayList<>();
		final Category categoryId = categoryDao.getRefById(category);
		
		
		if(category.isEmpty() && price.isEmpty()) {
			res = eventDao.getEvent(limit, offset);
		}else if(price.equals("ASC")){
			res = eventDao.getByPriceAsc();
		}else if(price.equals("DESC")){
			res = eventDao.getByPriceDesc();
		}else if(category.equals(categoryId.getId())) {
			res = eventDao.getByCategoryId(categoryId.getId(), limit, offset);
		}
		
		for (int i = 0; i < res.size(); i++) {
			final PojoEventResGetAll pojo = new PojoEventResGetAll();
			final Event event = res.get(i);

			ConnHandler.getManager().detach(event);
			pojo.setFileId(event.getFile().getId());
			pojo.setId(event.getId());
			pojo.setEventCode(event.getEventCode());
			pojo.setEventName(event.getEventName());
			pojo.setProvider(event.getProvider());
			pojo.setLocationName(event.getLocationName());
			pojo.setCategoryId(event.getCategory().getCategoryName());
			
			//LocalDate localDate = event.getStartDate().toLocalDate();
			
			pojo.setStartDate(event.getStartDate());
			pojo.setEndDate(event.getEndDate());
			pojo.setPrice(event.getPrice());
			pojo.setVer(event.getVersion());

			pojos.add(pojo);
		}
		return pojos;
	}
	
	public List<PojoEventResGetAll> getMyEvent(String category, String price, Integer limit, Integer offset) {
		final List<PojoEventResGetAll> pojos = new ArrayList<>();
		List<Event> res = new ArrayList<>();
		final Category categoryId = categoryDao.getRefById(category);
		
		
		if(category.isEmpty() && price.isEmpty()) {
			res = eventDao.getMyEvent(principalService.getAuthPrincipal(),limit, offset);
		}else if(price.equals("ASC")){
			res = eventDao.getByPriceAscMyEvent(principalService.getAuthPrincipal(),limit, offset);
		}else if(price.equals("DESC")){
			res = eventDao.getByPriceDescMyEvent(principalService.getAuthPrincipal(),limit, offset);
		}else if(category.equals(categoryId.getId())) {
			res = eventDao.getByCategoryIdMyEvent(categoryId.getId(),principalService.getAuthPrincipal() ,limit, offset);
		}
		
		for (int i = 0; i < res.size(); i++) {
			final PojoEventResGetAll pojo = new PojoEventResGetAll();
			final Event event = res.get(i);

			ConnHandler.getManager().detach(event);
			pojo.setFileId(event.getFile().getId());
			pojo.setId(event.getId());
			pojo.setEventCode(event.getEventCode());
			pojo.setEventName(event.getEventName());
			pojo.setProvider(event.getProvider());
			pojo.setLocationName(event.getLocationName());
			pojo.setCategoryId(event.getCategory().getCategoryName());
			
			pojo.setStartDate(event.getStartDate());
			pojo.setEndDate(event.getEndDate());
			pojo.setPrice(event.getPrice());
			pojo.setVer(event.getVersion());

			pojos.add(pojo);
		}
		return pojos;
	}

	public PojoUpdateRes update(final PojoEventReqUpdate data) {
		Event eventUpdate = null;

		eventUpdate = getByIdAndDetach(data.getId());

		final Event event = eventUpdate;

		event.setEventName(data.getEventName());
		event.setProvider(data.getProvider());
		event.setLocationName(data.getLocationName());
		event.setStartDate(data.getStartDate());
		event.setEndDate(data.getEndDate());
		event.setPrice(data.getPrice());
		event.setVersion(data.getVer());

		final Category category = categoryDao.getByIdRef(Category.class, data.getCategoryId());
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

		eventUpdate = update(event);

		final PojoUpdateRes pojoUpdate = new PojoUpdateRes();
		pojoUpdate.setVer(data.getVer());
		pojoUpdate.setMessage("Updated");
		return pojoUpdate;

	}

	public PojoDeleteRes delete(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deleteById(id);
		res.setMessage("Event Berhasil Dihapus");
		return res;
	}
	
	
	/** User Event Service */
	
	public PojoInsertRes insertUserEvent(final PojoUserEventInsertReq data) {
		final UserEvent userEvent = new UserEvent();
		
		final User user = userService.getByRefId(principalService.getAuthPrincipal());
		user.setId(principalService.getAuthPrincipal());
		userEvent.setUser(user);

		final Event event = eventDao.getByIdRef(Event.class, data.getEventId());
		event.setId(data.getEventId());
		userEvent.setEvent(event);

		UserEvent eventInsert = null;
		ConnHandler.begin();
		eventInsert = userEventDao.insert(userEvent);
		ConnHandler.commit();

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(eventInsert.getId());
		pojoInsertRes.setMessage("Anda Berhasil Membeli Event");
		return pojoInsertRes;
	}
	
	public List<PojoUserEventGetByUserIdRes> getByUserId(String category, String price, Integer limit, Integer offset) {
		final List<PojoUserEventGetByUserIdRes> pojos = new ArrayList<>();

		List<UserEvent> res = new ArrayList<>();
		
		final Category categoryId = categoryDao.getRefById(category);
		
		
		if(category.isEmpty() && price.isEmpty()) {
			res = userEventDao.getByUserId(principalService.getAuthPrincipal(),limit, offset);
		}else if(price.equals("ASC")){
			res = userEventDao.getByPriceAsc(principalService.getAuthPrincipal(),limit, offset);
		}else if(price.equals("DESC")){
			res = userEventDao.getByPriceDesc(principalService.getAuthPrincipal(),limit, offset);
		}else if(category.equals(categoryId.getId())) {
			res = userEventDao.getByCategoryId(categoryId.getId(),principalService.getAuthPrincipal() ,limit, offset);
		}
		
		for (int i = 0; i < res.size(); i++) {
			final PojoUserEventGetByUserIdRes pojo = new PojoUserEventGetByUserIdRes();
			final UserEvent userEvent = res.get(i);

			ConnHandler.getManager().detach(userEvent);
			
			pojo.setId(userEvent.getEvent().getId());
			
			pojo.setUserId(userEvent.getUser().getProfile().getFullName());
			
			pojo.setFileId(userEvent.getEvent().getFile().getId());
			pojo.setEventCode(userEvent.getEvent().getEventCode());
			pojo.setEventName(userEvent.getEvent().getEventName());
			pojo.setProvider(userEvent.getEvent().getProvider());
			pojo.setLocationName(userEvent.getEvent().getLocationName());
			pojo.setCategoryId(userEvent.getEvent().getCategory().getCategoryName());
			
			pojo.setStartDate(userEvent.getEvent().getStartDate());
			pojo.setEndDate(userEvent.getEvent().getEndDate());
			pojo.setPrice(userEvent.getEvent().getPrice());
			pojo.setVer(userEvent.getEvent().getVersion());

			pojos.add(pojo);
		}

		return pojos;
	}
	
	public PojoDeleteRes deleteUserEvent(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		ConnHandler.begin();
		userEventDao.delete(id);
		ConnHandler.commit();
		res.setMessage("Berhasil Dihapus");
		return res;
	}
	
	public PojoEventResGetAll getByEventId(final String id) {
		final Optional<Event> event = eventDao.getEventById(id);
		final PojoEventResGetAll pojoEventResGetAll = new PojoEventResGetAll();
		pojoEventResGetAll.setId(event.get().getId());
		pojoEventResGetAll.setUserId(event.get().getUser().getId());
		pojoEventResGetAll.setEventName(event.get().getEventName());
		pojoEventResGetAll.setEventCode(event.get().getEventCode());
		pojoEventResGetAll.setProvider(event.get().getProvider());
		pojoEventResGetAll.setLocationName(event.get().getLocationName());
		pojoEventResGetAll.setStartDate(event.get().getStartDate());
		pojoEventResGetAll.setEndDate(event.get().getEndDate());
		pojoEventResGetAll.setPrice(event.get().getPrice());
		pojoEventResGetAll.setCategoryId(event.get().getCategory().getCategoryName());
		pojoEventResGetAll.setFileId(event.get().getFile().getId());
		pojoEventResGetAll.setVer(event.get().getVersion());
		return pojoEventResGetAll;
	}
	
	public PojoEventGetAllResData getEventPage(final Integer limit, final Integer offset) {
		final List<PojoEventResGetAll> pojos = new ArrayList<>();
		final List<Event> res = eventDao.getEvent(limit, offset);

		for (int i = 0; i < res.size(); i++) {
			final PojoEventResGetAll pojo = new PojoEventResGetAll();
			final Event event = res.get(i);

			ConnHandler.getManager().detach(event);
			pojo.setFileId(event.getFile().getFileName());
			pojo.setId(event.getId());
			pojo.setEventCode(event.getEventCode());
			pojo.setEventName(event.getEventName());
			pojo.setProvider(event.getProvider());
			pojo.setLocationName(event.getLocationName());
			pojo.setCategoryId(event.getCategory().getCategoryName());
			pojo.setStartDate(event.getStartDate());
			pojo.setEndDate(event.getEndDate());
			pojo.setPrice(event.getPrice());
			pojo.setVer(event.getVersion());

			pojos.add(pojo);
		}
		
		final PojoEventGetAllResData pojoEventData = new PojoEventGetAllResData();
		pojoEventData.setData(pojos);
		pojoEventData.setTotal(eventDao.getTotalEvent());
		
		return pojoEventData;
	}

	@Override
	void valNotNullable(Event data) {
		
		if (data.getEventName().length() == 0) {
			throw new RuntimeException("Nama Event Kosong");
		}
		
		if (data.getProvider().length() == 0) {
			throw new RuntimeException("Provider Kosong");
		}
		
		if (data.getLocationName().length() == 0) {
			throw new RuntimeException("Trainer Kosong");
		}
		
		if (data.getStartDate() == null) {
			throw new RuntimeException("Tanggal Start Kosong");
		}
		
		if (data.getEndDate() == null) {
			throw new RuntimeException("Tanggal End Kosong");
		}
		
		if (data.getPrice() == null) {
			throw new RuntimeException("Price Kosong");
		}
		
		if (data.getCategory() == null) {
			throw new RuntimeException("Category Kosong");
		}
	}

	@Override
	void valIdNull(Event data) {
		if (data.getId() == null) {
			throw new RuntimeException("ID kosong");
		}
	}

	@Override
	void valIdNotNull(Event data) {
		if (data.getId() != null) {
			throw new RuntimeException("ID harus kosong");
		}	
	}

	@Override
	void valBkNull(Event data) {
		
		
	}

	@Override
	void valFkNull(Event data) {
	
		
	}

	@Override
	void valMaxLength(Event data) {
		if(data.getEventName().length() > 100) {
			throw new RuntimeException("Name Course terlalu panjang");
		}
		
		if(data.getProvider().length() > 50) {
			throw new RuntimeException("Name Provider terlalu panjang");
		}
		
		if(data.getLocationName().length() > 50) {
			throw new RuntimeException("Name Lokasi terlalu panjang");
		}
		
	}

	@Override
	void valIdPresent(Event data) {
		final Optional<Event> res = getById(data.getId());
		if(res.isPresent()) {
			throw new RuntimeException("ID sudah ada di database");
		}
		
	}

}
