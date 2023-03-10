package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.ArticleDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.model.Article;
import com.lawencon.community.model.File;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.article.PojoArticleInsertReq;
import com.lawencon.community.pojo.article.PojoArticleResGetAll;
import com.lawencon.community.pojo.article.PojoArticleUpdateReq;
import com.lawencon.security.principal.PrincipalService;

@Service
public class ArticleService {
	
private final ArticleDao articleDao;
private final UserService userService;
private final PrincipalService principalService;
private final FileDao fileDao;
	

	public ArticleService(final ArticleDao articleDao, UserService userService, PrincipalService principalService, FileDao fileDao) {
		this.articleDao = articleDao;
		this.userService = userService;
		this.principalService = principalService;
		this.fileDao = fileDao;
		
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
		
		final User user = userService.getByRefId(principalService.getAuthPrincipal());
		user.setId(principalService.getAuthPrincipal());
		article.setUser(user);
		
		final File fileInsert = new File();
		fileInsert.setFileName(data.getPhotoId().getFileName());
		fileInsert.setFileExtension(data.getPhotoId().getFileExtension());
		fileInsert.setFileContent(data.getPhotoId().getFileContent());

		ConnHandler.begin();
		final File file = fileDao.insert(fileInsert);
		ConnHandler.commit();
		article.setPhoto(file);
		
		article.setArticleTitle(data.getArticleTitle());;
		article.setArticleContent(data.getArticleContent());

		Article articleInsert = null;
		articleInsert = insert(article);

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(articleInsert.getId());
		pojoInsertRes.setMessage("Success");
		return pojoInsertRes;
	}
	
	public List<PojoArticleResGetAll> getAllRes() {
		final List<PojoArticleResGetAll> pojos = new ArrayList<>();
		final List<Article> res = getAll();

		for (int i = 0; i < res.size(); i++) {
			final PojoArticleResGetAll pojo = new PojoArticleResGetAll();
			final Article article = res.get(i);

			ConnHandler.getManager().detach(article);
			
			pojo.setId(article.getId());
			pojo.setPhotoId(article.getPhoto().getId());
			pojo.setArticleTitle(article.getArticleTitle());
			pojo.setArticleContent(article.getArticleContent());
			pojo.setVer(article.getVersion());

			pojos.add(pojo);
		}
		return pojos;
	}
	
	public PojoUpdateRes update(final PojoArticleUpdateReq data) {
		Article articleUpdate = null;

		articleUpdate = getByIdAndDetach(data.getId());

		final Article article = articleUpdate;

		article.setArticleTitle(data.getArticleTitle());
		article.setArticleContent(data.getArticleContent());
		
		article.setVersion(data.getVer());

		final File fileInsert = new File();
		fileInsert.setFileName(data.getPhotoId().getFileName());
		fileInsert.setFileExtension(data.getPhotoId().getFileExtension());
		fileInsert.setFileContent(data.getPhotoId().getFileContent());

		ConnHandler.begin();
		final File file = fileDao.insert(fileInsert);
		ConnHandler.commit();
		article.setPhoto(file);

		articleUpdate = update(article);

		final PojoUpdateRes pojoUpdate = new PojoUpdateRes();
		pojoUpdate.setVer(data.getVer());
		pojoUpdate.setMessage("Updated");
		return pojoUpdate;

	}

	public PojoDeleteRes delete(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deleteById(id);
		res.setMessage("Article Berhasil Dihapus");
		return res;
	}
	
	
	
	

}
