package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.Article;

@Repository
public class ArticleDao extends MasterDao<Article>{

	@Override
	public Optional<Article> getById(Long id) {
		final Article res = getById(Article.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<Article> getRefById(Long id) {
		final Article res = getByIdRef(Article.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM article")
			.append(" WHERE is_active = true");
		final List<Article> res = em().createNativeQuery(toStr(str), Article.class).getResultList();
		return res;
	}

	@Override
	public Article update(final Article data) {
		final Article res = saveAndFlush(data);
		return res;
	}

	@Override
	public Article insert(final Article data) {
		final Article res = saveAndFlush(data);
		return res;
	}

	@Override
	boolean delete(final Long id) {
		return deleteById(Article.class, id);
	}
	
	

	
	
	
	
	
	

}
