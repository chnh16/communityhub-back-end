package com.lawencon.community.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.model.PostType;

public class PostTypeDaoImpl extends BaseDaoImpl implements PostTypeDao{
	
	
	@Override
	public Optional<PostType> getById(final Long id) {
		final PostType res = getById(PostType.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<PostType> getRefById(final Long id) {
		final PostType res = getByIdRef(PostType.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostType> getAll() {
		final String sql = "SELECT * FROM post_type" 
				+ " WHERE is_active = true";
		final List<PostType> res = em().createNativeQuery(sql, PostType.class).getResultList();
		return res;
	}

	@Override
	public PostType update(final PostType data) {
		PostType res = em().merge(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(PostType.class, id);
	}

}
