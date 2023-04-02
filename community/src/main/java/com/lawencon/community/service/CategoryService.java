package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.category.PojoCategoryGetAllResData;
import com.lawencon.community.pojo.category.PojoCategoryGetAllRes;
import com.lawencon.community.pojo.category.PojoCategoryInsertReq;
import com.lawencon.community.pojo.category.PojoCategoryUpdateReq;
import com.lawencon.community.util.Generate;

@Service
public class CategoryService extends ValidationService<Category> {

	@Autowired
	private CategoryDao categoryDao;

	public Category insertCategory(final Category data) {
		Category categoryInsert = null;
		try {
			ConnHandler.begin();
		
			valNotNullable(data);
			valMaxLength(data);
			categoryInsert = categoryDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoryInsert;
	}

	public PojoInsertRes insertCategory(final PojoCategoryInsertReq data) {
		final Category category = new Category();
		
		final String generateId = Generate.generateCode(5);

		category.setCategoryCode(generateId);
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
		
		try {
			ConnHandler.begin();
			valNotNullable(data);
			valMaxLength(data);
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

		if (data.getCategoryCode() != null) {
			category.setCategoryCode(data.getCategoryCode());
		}

		if (data.getCategoryName() != null) {
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

	public Optional<Category> getById(final String id) {
		return categoryDao.getById(id);
	}

	public Category getByIdAndDetach(final String id) {
		return categoryDao.getByIdAndDetach(Category.class, id);
	}

	public Category getRefById(final String id) {
		return categoryDao.getRefById(id);
	}

	public List<Category> getAll() {
		return categoryDao.getAll();
	}

	public int getTotalCategory() {
		return categoryDao.getTotalCategory();
	}

	public List<PojoCategoryGetAllResData> getAllCategory() {
		final List<PojoCategoryGetAllResData> listPojoCategory = new ArrayList<>();

		final List<Category> listCategory = getAll();
		for (int i = 0; i < listCategory.size(); i++) {
			final PojoCategoryGetAllResData pojoCategoryGetAll = new PojoCategoryGetAllResData();
			pojoCategoryGetAll.setId(listCategory.get(i).getId());
			pojoCategoryGetAll.setCategoryCode(listCategory.get(i).getCategoryCode());
			pojoCategoryGetAll.setCategoryName(listCategory.get(i).getCategoryName());
			pojoCategoryGetAll.setVer(listCategory.get(i).getVersion());
			listPojoCategory.add(pojoCategoryGetAll);
		}
		return listPojoCategory;
	}

	public PojoCategoryGetAllRes getCategory(final Integer limit, final Integer offset) {
		final List<PojoCategoryGetAllResData> listPojoCategory = new ArrayList<>();
		final List<Category> listCategory = categoryDao.getAllCategory(limit, offset);
		for (int i = 0; i < listCategory.size(); i++) {
			final PojoCategoryGetAllResData pojoCategoryGetAll = new PojoCategoryGetAllResData();
			pojoCategoryGetAll.setId(listCategory.get(i).getId());
			pojoCategoryGetAll.setCategoryCode(listCategory.get(i).getCategoryCode());
			pojoCategoryGetAll.setCategoryName(listCategory.get(i).getCategoryName());
			pojoCategoryGetAll.setVer(listCategory.get(i).getVersion());
			listPojoCategory.add(pojoCategoryGetAll);
		}

		final PojoCategoryGetAllRes pojoCategoryData = new PojoCategoryGetAllRes();
		pojoCategoryData.setData(listPojoCategory);
		pojoCategoryData.setTotal(categoryDao.getTotalCategory());

		return pojoCategoryData;
	}

	public PojoCategoryGetAllResData getCategoryById(final String id) {

		final Optional<Category> listCategory = categoryDao.getCategoryById(id);
		final PojoCategoryGetAllResData pojoCategoryGetAll = new PojoCategoryGetAllResData();
		pojoCategoryGetAll.setId(listCategory.get().getId());
		pojoCategoryGetAll.setCategoryCode(listCategory.get().getCategoryCode());
		pojoCategoryGetAll.setCategoryName(listCategory.get().getCategoryName());
		pojoCategoryGetAll.setVer(listCategory.get().getVersion());

		return pojoCategoryGetAll;
	}

	@Override
	void valBkNull(Category data) {
		if(data.getCategoryCode() == null) {
			throw new RuntimeException("BK Kosong");
		}
		
	}

	@Override
	void valFkNull(Category data) {
		
		
	}

	@Override
	void valMaxLength(Category data) {
		if(data.getCategoryCode().length() > 5) {
			
			throw new RuntimeException("Kode terlalu panjang");
			
		}
		if(data.getCategoryName().length() > 30) {
			throw new RuntimeException("Category terlalu panjang");
		}
	}

	@Override
	void valIdPresent(Category data) {
		final Optional<Category> res = getById(data.getId());
		if(res.isPresent()) {
			throw new RuntimeException("ID sudah ada di database");
		}
		
	}
	
	void valIdNull(final Category data) {
		if (data.getId() == null) {
			throw new RuntimeException("ID kosong");
		}
	}

	void valIdNotNull(final Category data) {
		if (data.getId() != null) {
			throw new RuntimeException("ID harus kosong");
		}
	}
	
	void valNotNullable(final Category data) {
		if (data.getCategoryCode() == null) {
			throw new RuntimeException("Kode Kategori Kosong");
		}
		if (data.getCategoryName().length() == 0) {
			throw new RuntimeException("Nama Kategori Kosong");
		}
	}

}
