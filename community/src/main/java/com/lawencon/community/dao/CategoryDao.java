package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.Category;

@Repository
public class CategoryDao extends MasterDao<Category>{
	
	@Override
	public Optional<Category> getById(final Long id) {
		return Optional.ofNullable(super.getById(Category.class, id));
	}

	@Override
	public Optional<Category> getRefById(final Long id) {
		return Optional.ofNullable(super.getByIdRef(Category.class, id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM category")
			.append(" WHERE is_active = true");
		final List<Category> res = em().createNativeQuery(toStr(str), Category.class).getResultList();
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

	@Override
	public Category getByIdAndDetach(final Long id) {
		return super.getByIdAndDetach(Category.class, id);
	}

}
