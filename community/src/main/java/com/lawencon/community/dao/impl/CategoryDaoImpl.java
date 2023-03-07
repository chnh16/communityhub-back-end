package com.lawencon.community.dao.impl;

import java.util.List;
import java.util.Optional;

import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.model.Category;

public class CategoryDaoImpl extends BaseDaoImpl implements CategoryDao{
	
	@Override
	public Optional<Category> getById(final Long id) {
		Category res = getById(Category.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<Category> getRefById(final Long id) {
		Category res = getByIdRef(Category.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAll() {
		final String sql = "SELECT * FROM category" 
				+ " WHERE is_active = true";
		final List<Category> res = em().createNativeQuery(sql, Category.class).getResultList();
		return res;
	}

	@Override
	public Category update(final Category data) {
		Category res = em().merge(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(Category.class, id);
	}

}
