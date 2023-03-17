package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.category.PojoCategoryGetAllRes;
import com.lawencon.community.pojo.category.PojoCategoryInsertReq;
import com.lawencon.community.pojo.category.PojoCategoryUpdateReq;

@Service
public class CategoryService {
	
	private final CategoryDao categoryDao;
	
	public CategoryService(final CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	private void valIdNull(final Category data) {
		if (data.getId() != null) {
			throw new RuntimeException("ID harus kosong");
		}
	}

	private void valIdNotNull(final Category data) {
		if (data.getId() == null) {
			throw new RuntimeException("ID kosong");
		}
	}

	private void valNotNullable(final Category data) {
		if (data.getCategoryCode() == null) {
			throw new RuntimeException("Kode Kategori Kosong");
		}
		if (data.getCategoryName() == null) {
			throw new RuntimeException("Nama Kategori Kosong");
		}
	}
	
	public Category insertCategory(final Category data) {
		Category categoryInsert = null;
		try {
			ConnHandler.begin();
			valIdNull(data);
			valNotNullable(data);
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
		
		category.setIsActive(true);
		
		Category categoryInsert = null;
		categoryInsert = insertCategory(category);
		
		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(categoryInsert.getId());
		pojoInsertRes.setMessage("Berhasil Menambah Data");
		return pojoInsertRes;
	}
	
	public Category updateIndustry(final Category data) {
		Category categoryUpdate = null;
		valIdNotNull(data);
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
		
		if(data.getCategoryCode() != null) {
			category.setCategoryCode(data.getCategoryCode());			
		}
		
		if(data.getCategoryName() != null) {
			category.setCategoryName(data.getCategoryName());			
		}
		
		categoryUpdate = updateIndustry(category);
		
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		pojoUpdateRes.setVer(categoryUpdate.getVersion());
		pojoUpdateRes.setMessage("Berhasil Mengubah Data");
		
		return pojoUpdateRes;
	}
	
	public boolean deleteById(final String id) {
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
	
	public PojoDeleteRes deleteRes(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deleteById(id);
		res.setMessage("Berhasil Menghapus Data");
		return res;
	}
	
	public Optional<Category> getById(final String id){
		return categoryDao.getById(id);
	}
	
	public Category getByIdAndDetach(final String id) {
		return categoryDao.getByIdAndDetach(Category.class, id);
	}
	
	public Category getRefById(final String id){
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
			pojoCategoryGetAll.setId(listCategory.get(i).getId());
			pojoCategoryGetAll.setCategoryCode(listCategory.get(i).getCategoryCode());
			pojoCategoryGetAll.setCategoryName(listCategory.get(i).getCategoryName());
			pojoCategoryGetAll.setVer(listCategory.get(i).getVersion());
			listPojoCategory.add(pojoCategoryGetAll);
		}
		return listPojoCategory;
	}
	
	public List<PojoCategoryGetAllRes> getCategoryById(final String id){
		final List<PojoCategoryGetAllRes> listPojoCategory = new ArrayList<>();
		
		final List<Category> listCategory = categoryDao.getCategoryById(id);
		for(int i = 0; i < listCategory.size(); i++) {
			final PojoCategoryGetAllRes pojoCategoryGetAll = new PojoCategoryGetAllRes();
			pojoCategoryGetAll.setId(listCategory.get(i).getId());
			pojoCategoryGetAll.setCategoryCode(listCategory.get(i).getCategoryCode());
			pojoCategoryGetAll.setCategoryName(listCategory.get(i).getCategoryName());
			pojoCategoryGetAll.setVer(listCategory.get(i).getVersion());
			listPojoCategory.add(pojoCategoryGetAll);
		}
		return listPojoCategory;
	}
}
