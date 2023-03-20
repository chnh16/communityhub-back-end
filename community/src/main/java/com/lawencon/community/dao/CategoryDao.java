package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Category;

@Repository
public class CategoryDao extends MasterDao<Category>{
	
	@Override
	public Optional<Category> getById(final String id) {
		return Optional.ofNullable(super.getById(Category.class, id));
	}

	@Override
	public Category getRefById(final String id) {
		return super.getByIdRef(Category.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM category")
			.append(" WHERE is_active = true");
		final List<Category> res = em().createNativeQuery(toStr(str), Category.class)
				.getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public Optional<Category> getCategoryById(final String id) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM category c ")
			.append(" WHERE c.id = :id AND c.is_active = true");
		final List<Category> res = em().createNativeQuery(toStr(str), Category.class)
				.setParameter("id", id)
				.getResultList();
		return Optional.ofNullable(res.get(0));
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> getAllCategory(final Integer limit, final Integer offset) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM category c ")
			.append(" WHERE c.is_active = true");
		final List<Category> res = em().createNativeQuery(toStr(str).toString(), Category.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		return res;
	}
	
	public int getTotalCategory() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(c.id) FROM category c ")
			.append(" WHERE is_active = true");
		
		final int totalCategory = Integer.valueOf(ConnHandler.getManager().createNativeQuery(toStr(str)
				.toString())
				.getSingleResult().toString());
		return totalCategory;
	}

	public Category update(final Category data) {
		final Category res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(Category.class, id);
	}

	public Category insert(final Category data) {
		final Category res = saveAndFlush(data);
		return res;
	}

	@Override
	public Category getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(Category.class, id);
	}

}
