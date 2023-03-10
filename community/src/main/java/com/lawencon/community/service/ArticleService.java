package com.lawencon.community.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.ArticleDao;
import com.lawencon.community.dao.MembershipDao;
import com.lawencon.community.model.Article;
import com.lawencon.community.model.Membership;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.article.PojoArticleInsertReq;
import com.lawencon.community.pojo.membership.PojoMembershipInsertReq;
import com.lawencon.community.util.Generate;

@Service
public class ArticleService {
	
private final ArticleDao articleDao;
	

	public ArticleService(final ArticleDao articleDao) {
		this.articleDao = articleDao;
		
	}

	public Article insert(final Article data) {
		Article articleInsert = null;

		try {
			ConnHandler.begin();
			articleInsert = articleDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return articleInsert;
	}

	public Article update(final Article data) {
		Article articleUpdate = null;

		try {
			ConnHandler.begin();
			articleUpdate = articleDao.update(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return articleUpdate;
	}

	public Optional<Article> getById(final String id) {
		return articleDao.getById(id);
	}

	public List<Article> getAll() {
		return articleDao.getAll();
	}

	public boolean deleteById(final String id) {
		boolean articleDelete = false;

		try {
			ConnHandler.begin();
			articleDelete = articleDao.deleteById(Article.class, id);
			ConnHandler.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return articleDelete;

	}

	public Article getByIdAndDetach(final String id) {
		return articleDao.getByIdAndDetach(Article.class, id);
	}
	
	public PojoInsertRes insert(final PojoArticleInsertReq data) {
		final Article article = new Article();
		
		article.setArticleTitle(data.getArticleTitle());;
		article.setArticleContent(data.getArticleContent());

		Article articleInsert = null;
		articleInsert = insert(article);

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(articleInsert.getId());
		pojoInsertRes.setMessage("Success");
		return pojoInsertRes;
	}

}
