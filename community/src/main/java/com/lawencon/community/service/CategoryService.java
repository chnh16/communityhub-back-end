package com.lawencon.community.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.category.PojoCategoryGetAllRes;
import com.lawencon.community.pojo.category.PojoCategoryInsertReq;
import com.lawencon.community.pojo.category.PojoCategoryUpdateReq;
import com.lawencon.security.principal.PrincipalServiceImpl;

public class CategoryService extends PrincipalServiceImpl {
	
	private final CategoryDao categoryDao;
	@PersistenceContext
	private EntityManager em;
	
	public CategoryService(final CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	public Category insertCategory(final Category data) {
		Category categoryInsert = null;
		try {
			ConnHandler.begin();
			categoryInsert = categoryDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoryInsert;
	}
	
	public PojoInsertRes insertCategory(final PojoCategoryInsertReq data) {
		final Category category = new Category();
		
		category.setCategoryCode(data.getCategoryCode());
		category.setCategoryName(data.getCategoryName());
		
		category.setCreatedBy(getAuthPrincipal());
		category.setIsActive(true);
		
		final Category categoryInsert = insertCategory(category);
		
		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(Long.valueOf(categoryInsert.getId()));
		pojoInsertRes.setMessage("Berhasil Menambah Data");
		return pojoInsertRes;
	}
	
	public Category updateIndustry(final Category data) {
		Category categoryUpdate = null;
		try {
			ConnHandler.begin();
			categoryUpdate = categoryDao.update(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoryUpdate;
	}
	
	public PojoUpdateRes updateIndustry(final PojoCategoryUpdateReq data) {
		Category categoryUpdate = null;
		
		categoryUpdate = getByIdAndDetach(data.getId());
		
		final Category category = categoryUpdate;
		
		category.setCategoryCode(data.getCategoryCode());
		category.setCategoryName(data.getCategoryName());
		category.setUpdatedAt(LocalDateTime.now());
		
		categoryUpdate = updateIndustry(category);
		
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		pojoUpdateRes.setVer(data.getVer());
		pojoUpdateRes.setMessage("Berhasil Mengubah Data");
		
		return pojoUpdateRes;
	}
	
	public boolean deleteById(final Long id) {
		boolean categoryDelete = false;

		try {
			ConnHandler.begin();
			categoryDelete = categoryDao.deleteById(Category.class, id);
			ConnHandler.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return categoryDelete;
	}
	
	public PojoDeleteRes deleteRes(final Long id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deleteById(id);
		res.setMessage("Berhasil Menghapus Data");
		return res;
	}
	
	public Optional<Category> getById(final Long id){
		return categoryDao.getById(id);
	}
	
	public Category getByIdAndDetach(final Long id) {
		return categoryDao.getByIdAndDetach(Category.class, id);
	}
	
	public Optional<Category> getRefById(final Long id){
		return categoryDao.getRefById(id);
	}
	
	public List<Category> getAll(){
		return categoryDao.getAll();
	}
	
	public List<PojoCategoryGetAllRes> getAllCategory(){
		final List<PojoCategoryGetAllRes> listPojoCategory = new ArrayList<>();
		
		final List<Category> listCategory = getAll();
		for(int i = 0; i < listCategory.size(); i++) {
			final PojoCategoryGetAllRes pojoCategoryGetAll = new PojoCategoryGetAllRes();
			pojoCategoryGetAll.setId(Long.valueOf(listCategory.get(i).getId()));
			pojoCategoryGetAll.setCategoryCode(listCategory.get(i).getCategoryCode());
			pojoCategoryGetAll.setCategoryName(listCategory.get(i).getCategoryName());
			
			listPojoCategory.add(pojoCategoryGetAll);
		}
		return listPojoCategory;
	}
}
