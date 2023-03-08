package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.PostDetail;

@Repository
public class PostDetailDao extends MasterDao<PostDetail>{

	@Override
	public Optional<PostDetail> getById(final Long id) {
		final PostDetail res = getById(PostDetail.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<PostDetail> getRefById(final Long id) {
		final PostDetail res = getByIdRef(PostDetail.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostDetail> getAll() {
		final String sql = "SELECT * FROM post_detail "
				+ " WHERE is_active = true ";
		final List<PostDetail> res = em().createNativeQuery(sql, PostDetail.class).getResultList();
		return res;
	}

	@Override
	public PostDetail update(final PostDetail data) {
		final PostDetail res = saveAndFlush(data);
		return res;
	}

	@Override
	public PostDetail insert(final PostDetail data) {
		final PostDetail res = saveAndFlush(data);
		return res;
	}

	@Override
	boolean delete(final Long id) {
		return deleteById(PostDetail.class, id);
	}

}