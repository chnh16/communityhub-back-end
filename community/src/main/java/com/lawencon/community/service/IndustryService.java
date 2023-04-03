package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.model.Industry;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.industry.PojoIndustryGetAllRes;
import com.lawencon.community.pojo.industry.PojoIndustryGetAllResData;
import com.lawencon.community.pojo.industry.PojoIndustryInsertReq;
import com.lawencon.community.pojo.industry.PojoIndustryUpdateReq;
import com.lawencon.community.util.Generate;

@Service
public class IndustryService{
	
	@Autowired
	private IndustryDao industryDao;
	@PersistenceContext
	private EntityManager em;

	private void valIdNull(final Industry data) {
		if (data.getId() != null) {
			throw new RuntimeException("ID harus kosong");
		}
	}

	private void valIdNotNull(final Industry data) {
		if (data.getId() == null) {
			throw new RuntimeException("ID kosong");
		}
	}

	private void valNotNullable(final Industry data) {
		if (data.getIndustryName().length() == 0) {
			throw new RuntimeException("Nama Industry Kosong");
		}
	}
	
	private void valMaxLength(Industry data) {
		if(data.getIndustryName().length() > 30) {
			throw new RuntimeException("Nama Industry terlalu panjang");
		}
	}
	
	
	
	
	public Industry insertIndustry(final Industry data) {
		Industry industryInsert = null;
		try {
			ConnHandler.begin();
			valIdNull(data);
			valNotNullable(data);
			valMaxLength(data);
			industryInsert = industryDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return industryInsert;
	}
	
	public PojoInsertRes insertIndustry(final PojoIndustryInsertReq data) {
		final Industry industry = new Industry();
		
		final String generateId = Generate.generateCode(5);
		
		industry.setIndustryCode(generateId);
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
			valIdNotNull(data);
			valMaxLength(data);
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
		
		if(data.getIndustryCode() != null) {
			industry.setIndustryCode(data.getIndustryCode());			
		}
		
		if(data.getIndustryName() != null) {
			industry.setIndustryName(data.getIndustryName());			
		}
		
		industryUpdate = updateIndustry(industry);
		
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		pojoUpdateRes.setVer(industryUpdate.getVersion());
		pojoUpdateRes.setMessage("Berhasil Mengubah Data");
		
		return pojoUpdateRes;
	}
	
	public Optional<Industry> getById(final String id){
		return industryDao.getById(id);
	}
	
	public Industry getByIdAndDetach(final String id) {
		return industryDao.getByIdAndDetach(Industry.class, id);
	}
	
	public Industry getRefById(final String id){
		return industryDao.getRefById(id);
	}
	
	public List<Industry> getAll(){
		return industryDao.getAll();
	}
	
	public boolean deleteById(final String id) {
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
	
	public PojoDeleteRes deleteRes(final String id) {
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
			pojoIndustry.setId(listIndustry.get(i).getId());
			pojoIndustry.setIndustryCode(listIndustry.get(i).getIndustryCode());
			pojoIndustry.setIndustryName(listIndustry.get(i).getIndustryName());
			pojoIndustry.setVer(listIndustry.get(i).getVersion());
			listPojoIndustry.add(pojoIndustry);
		}
		return listPojoIndustry;
	}
	
	public PojoIndustryGetAllRes getIndustryById(final String id) {
		final Optional<Industry> industry = industryDao.getIndustryById(id);
		
		final PojoIndustryGetAllRes pojoIndustry = new PojoIndustryGetAllRes();
		pojoIndustry.setId(industry.get().getId());
		pojoIndustry.setIndustryCode(industry.get().getIndustryCode());
		pojoIndustry.setIndustryName(industry.get().getIndustryName());
		pojoIndustry.setVer(industry.get().getVersion());
		
		return pojoIndustry;
	}
	
	public PojoIndustryGetAllResData getIndustry(final Integer limit, final Integer offset){
		final List<PojoIndustryGetAllRes> listPojoIndustry = new ArrayList<>();
		final List<Industry> listIndustry = industryDao.getIndustry(limit, offset);
		for(int i = 0; i < listIndustry.size(); i++) {
			final PojoIndustryGetAllRes pojoIndustryGetAll = new PojoIndustryGetAllRes();
			pojoIndustryGetAll.setId(listIndustry.get(i).getId());
			pojoIndustryGetAll.setIndustryCode(listIndustry.get(i).getIndustryCode());
			pojoIndustryGetAll.setIndustryName(listIndustry.get(i).getIndustryName());
			pojoIndustryGetAll.setVer(listIndustry.get(i).getVersion());
			listPojoIndustry.add(pojoIndustryGetAll); 
		}
		
		final PojoIndustryGetAllResData pojoIndustryData = new PojoIndustryGetAllResData();
		pojoIndustryData.setData(listPojoIndustry);
		pojoIndustryData.setTotal(industryDao.getTotalIndustry());	
		
		return pojoIndustryData;
	}
}
