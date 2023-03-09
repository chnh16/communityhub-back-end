package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.model.Industry;
import com.lawencon.community.pojo.industry.PojoIndustryGetAllRes;
import com.lawencon.security.principal.PrincipalServiceImpl;

@Service
public class IndustryService extends PrincipalServiceImpl{
	
	private final IndustryDao industryDao;
	@PersistenceContext
	private EntityManager em;
	
	public IndustryService(final IndustryDao industryDao) {
		this.industryDao = industryDao;
	}

	
	public Industry insertIndustry(final Industry data) {
		Industry industryInsert = null;
		try {
			ConnHandler.begin();
			industryInsert = industryDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return industryInsert;
	}
	
	public Industry updateIndustry(final Industry data) {
		Industry industryUpdate = null;
		try {
			ConnHandler.begin();
			industryUpdate = industryDao.update(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return industryUpdate;
	}
	
	public Optional<Industry> getById(final Long id){
		return industryDao.getById(id);
	}
	
	public boolean deleteById(final Long id) {
		boolean industryDelete = false;

		try {
			ConnHandler.begin();
			industryDelete = industryDao.deleteById(Industry.class, id);
			ConnHandler.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return industryDelete;
	}
	
	public List<PojoIndustryGetAllRes> getAllIndustry(){
		final List<PojoIndustryGetAllRes> listPojoIndustry = new ArrayList<>();
		
		final List<Industry> listIndustry = industryDao.getAll();
		for(int i = 0; i < listIndustry.size(); i++) {
			final PojoIndustryGetAllRes pojoIndustry =  new PojoIndustryGetAllRes();
			pojoIndustry.setId(Long.valueOf(listIndustry.get(i).getId()));
			pojoIndustry.setIndustryCode(listIndustry.get(i).getIndustryCode());
			pojoIndustry.setIndustryName(listIndustry.get(i).getIndustryName());
			
			listPojoIndustry.add(pojoIndustry);
		}
		return listPojoIndustry;
	}
}
