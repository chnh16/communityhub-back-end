package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.community.model.PostType;

public class PostTypeDao extends MasterDao<PostType>{
	
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
		final PostType res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(PostType.class, id);
	}

	@Override
	public PostType insert(final PostType data) {
		final PostType res = saveAndFlush(data);
		return res;
	}

}
