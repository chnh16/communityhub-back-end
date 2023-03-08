package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.ArticleFile;

@Repository
public class ArticleFileDao extends BasePostDao<ArticleFile>{

	@Override
	ArticleFile insert(ArticleFile data) {
		final ArticleFile res = saveAndFlush(data);
		return res;
	}

	@Override
	boolean delete(Long id) {
		return deleteById(ArticleFile.class, id);
	}
	
	@SuppressWarnings("unchecked")
	List<ArticleFile> getByArticleId (Long articleId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM article_file af ")
		.append("WHERE af.article_id = :articleId");
		final List<ArticleFile> res = em().createNativeQuery(toStr(str), ArticleFile.class).setParameter("articleId", articleId).getResultList();
		return res;
	}
}
