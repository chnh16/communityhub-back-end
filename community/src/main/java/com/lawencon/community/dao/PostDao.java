package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.Post;

@Repository
public class PostDao extends MasterDao<Post> {

	@Override
	public Optional<Post> getById(final String id) {
		final Post res = getById(Post.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Post getRefById(final String id) {
		final Post res = getByIdRef(Post.class, id);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getAll() {
		final StringBuilder str = new StringBuilder();

		str.append("SELECT * FROM post p ").append("WHERE p.is_active = true ");

		final List<Post> res = em().createNativeQuery(toStr(str), Post.class).getResultList();
		return res;
	}

	@Override
	public Post update(final Post data) {
		final Post res = saveAndFlush(data);
		return res;
	}

	@Override
	public Post insert(final Post data) {
		final Post res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(Post.class, id);
	}

	@Override
	public Post getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(Post.class, id);
	}

}
