package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.community.model.Category;

public class CategoryDao extends MasterDao<Category>{
	
	@Override
	public Optional<Category> getById(final Long id) {
		final Category res = getById(Category.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<Category> getRefById(final Long id) {
		final Category res = getByIdRef(Category.class, id);
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

	public Category update(final Category data) {
		final Category res = saveAndFlush(data);
		return res;
	}

	public boolean delete(final Long id) {
		return deleteById(Category.class, id);
	}

	public Category insert(final Category data) {
		final Category res = saveAndFlush(data);
		return res;
	}

}
