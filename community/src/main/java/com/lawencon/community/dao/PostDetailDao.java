package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.PostDetail;

@Repository
public class PostDetailDao extends MasterDao<PostDetail>{

	@Override
	public Optional<PostDetail> getById(final String id) {
		final PostDetail res = getById(PostDetail.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public PostDetail getRefById(final String id) {
		final PostDetail res = getByIdRef(PostDetail.class, id);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostDetail> getAll() {
		final StringBuilder str =  new StringBuilder();
		str.append("SELECT * FROM post_detail ").append(" WHERE is_active = true ");
		final List<PostDetail> res = em().createNativeQuery(toStr(str), PostDetail.class).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<PostDetail> getByPostId (final String postId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM post_detail pd ")
		.append("WHERE pd.post_id = :postId");
		final List<PostDetail> res = em().createNativeQuery(toStr(str), PostDetail.class).setParameter("postId", postId).getResultList();
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
	boolean delete(final String id) {
		return deleteById(PostDetail.class, id);
	}

	@Override
	public PostDetail getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(PostDetail.class, id);
	}

}
