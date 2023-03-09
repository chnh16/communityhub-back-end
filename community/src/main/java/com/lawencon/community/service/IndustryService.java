package com.lawencon.community.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.model.Industry;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.industry.PojoIndustryGetAllRes;
import com.lawencon.community.pojo.industry.PojoIndustryInsertReq;
import com.lawencon.community.pojo.industry.PojoIndustryUpdateReq;

@Service
public class IndustryService{
	
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
	
	public PojoInsertRes insertIndustry(final PojoIndustryInsertReq data) {
		final Industry industry = new Industry();
		
		industry.setIndustryCode(data.getIndustryCode());
		industry.setIndustryName(data.getIndustryName());
		

		industry.setIsActive(true);
		
		final Industry industryInsert = insertIndustry(industry);
		
		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(industryInsert.getId());
		pojoInsertRes.setMessage("Berhasil Menambah Data");
		return pojoInsertRes;
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
	
	public PojoUpdateRes updateIndustry(final PojoIndustryUpdateReq data) {
		Industry industryUpdate = null;
		
		industryUpdate = getByIdAndDetach(data.getId());
		
		final Industry industry = industryUpdate;
		
		industry.setIndustryCode(data.getIndustryCode());
		industry.setIndustryName(data.getIndustryName());
		industry.setUpdatedAt(LocalDateTime.now());
		
		industryUpdate = updateIndustry(industry);
		
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		pojoUpdateRes.setVer(data.getVer());
		pojoUpdateRes.setMessage("Berhasil Mengubah Data");
		
		return pojoUpdateRes;
	}
	
	public Optional<Industry> getById(final Long id){
		return industryDao.getById(id);
	}
	
	public Industry getByIdAndDetach(final Long id) {
		return industryDao.getByIdAndDetach(Industry.class, id);
	}
	
	public Optional<Industry> getRefById(final Long id){
		return industryDao.getRefById(id);
	}
	
	public List<Industry> getAll(){
		return industryDao.getAll();
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
	
	public PojoDeleteRes deleteRes(final Long id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deleteById(id);
		res.setMessage("Berhasil Menghapus Data");
		return res;
	}
	
	public List<PojoIndustryGetAllRes> getAllIndustry(){
		final List<PojoIndustryGetAllRes> listPojoIndustry = new ArrayList<>();
		
		final List<Industry> listIndustry = getAll();
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
