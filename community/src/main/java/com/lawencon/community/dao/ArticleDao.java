package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Article;

@Repository
public class ArticleDao extends MasterDao<Article>{

	@Override
	public Optional<Article> getById(final String id) {
		return Optional.ofNullable(super.getById(Article.class, id));
	}

	@Override
	public Article getRefById(final String id) {
		return super.getByIdRef(Article.class, id);
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
	
	@SuppressWarnings("unchecked")
	public Optional<Article> getArticleById(final String id) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM article a ")
			.append(" WHERE a.id = :id AND a.is_active = true");
		final List<Article> res = em().createNativeQuery(toStr(str), Article.class)
				.setParameter("id", id)
				.getResultList();
		return Optional.ofNullable(res.get(0));
	}

	@SuppressWarnings("unchecked")
	public List<Article> getAllArticle(final Integer limit, final Integer offset) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM article a ")
			.append(" WHERE a.is_active = true");
		final List<Article> res = em().createNativeQuery(toStr(str).toString(), Article.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		return res;
	}
	
	public int getTotalArticle() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(a.id) FROM article a ")
			.append(" WHERE a.is_active =true ");
		final int totalCategory = Integer.valueOf(ConnHandler.getManager().createNativeQuery(toStr(str)
				.toString())
				.getSingleResult().toString());
		return totalCategory;
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
	boolean delete(final String id) {
		return deleteById(Article.class, id);
	}

	@Override
	public Article getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(Article.class, id);
	}
	
	

	
	
	
	
	
	

}
