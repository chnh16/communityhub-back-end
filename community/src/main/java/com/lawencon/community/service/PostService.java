package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostType;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.post.PojoPostGetAllRes;
import com.lawencon.community.pojo.post.PojoPostInsertReq;
import com.lawencon.community.pojo.post.PojoPostUpdateReq;
import com.lawencon.security.principal.PrincipalServiceImpl;

public class PostService extends PrincipalServiceImpl {
	
	private final PostDao postDao;
	private final UserDao userDao;
	private final PostTypeDao postTypeDao;
	private final CategoryDao categoryDao;
	
	public PostService(final PostDao postDao, final UserDao userDao, final PostTypeDao postTypeDao, final CategoryDao categoryDao) {
		this.postDao = postDao;
		this.userDao = userDao;
		this.postTypeDao = postTypeDao;
		this.categoryDao = categoryDao;
	}
	
	public Post insertPost(final Post data) {
		Post postInsert = null;
		try {
			ConnHandler.begin();
			postInsert = postDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postInsert;
	}
	
	public PojoInsertRes insertPost(final PojoPostInsertReq data) {
		final Post post = new Post();
		
		final User user = userDao.getRefById(getAuthPrincipal());
		post.setUser(user);
		
		post.setPostTitle(data.getPostTitle());
		post.setPostContent(data.getPostContent());
		
		final PostType postType = postTypeDao.getRefById(data.getPostTypeId());
		post.setPostType(postType);
		
		final Category category = categoryDao.getRefById(data.getCategoryId());
		post.setCategory(category);
		
		post.setIsActive(true);
		
		final Post postInsert = insertPost(post);
		
		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(postInsert.getId());
		pojoInsertRes.setMessage("Berhasil Menambah Data");
		return pojoInsertRes;
	}
	
	public Post updatePost(final Post data) {
		Post postUpdate = null;
		try {
			ConnHandler.begin();
			postUpdate = postDao.update(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postUpdate;
	}
	
	public PojoUpdateRes updatePost(final PojoPostUpdateReq data) {
		Post postUpdate = null;
		
		postUpdate = getByIdAndDetach(data.getId());
		
		final Post post = postUpdate;
				
		if(data.getPostTitle() != null) {
			post.setPostTitle(data.getPostTitle());
		}
		
		if(data.getPostContent() != null) {
			post.setPostContent(data.getPostContent());
		}
		
		if(data.getPostTypeId() != null) {
			final PostType postType = postTypeDao.getRefById(data.getPostTypeId());
			post.setPostType(postType);			
		}
				
		if(data.getCategoryId() != null) {
			final Category category = categoryDao.getRefById(data.getCategoryId());
			post.setCategory(category); 			
		}
		
		postUpdate = updatePost(post);
		
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		pojoUpdateRes.setVer(postUpdate.getVersion());
		pojoUpdateRes.setMessage("Berhasil Mengubah Data");
		
		return pojoUpdateRes;
	}
	
	public boolean deleteById(final String id) {
		boolean postDelete = false;

		try {
			ConnHandler.begin();
			postDelete = postDao.deleteById(Post.class, id);
			ConnHandler.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return postDelete;
	}
	
	public PojoDeleteRes deleteRes(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deleteById(id);
		res.setMessage("Berhasil Menghapus Data");
		return res;
	}
	
	public Optional<Post> getById(final String id){
		return postDao.getById(id);
	}
	
	public Post getByIdAndDetach(final String id) {
		return postDao.getByIdAndDetach(Post.class, id);
	}
	
	public Post getRefById(final String id){
		return postDao.getRefById(id);
	}
	
	public List<Post> getAll(){
		return postDao.getAll();
	}
	
	public List<PojoPostGetAllRes> getAllPost(){
		final List<PojoPostGetAllRes> listPojoPost = new ArrayList<>();
		
		final List<Post> listPost = getAll();
		
		for(int i = 0 ; i < listPost.size(); i++) {
			final PojoPostGetAllRes pojoPost = new PojoPostGetAllRes();
			
			pojoPost.setId(listPost.get(i).getId());
			pojoPost.setUserId(listPost.get(i).getUser().getId());
			pojoPost.setFullName(listPost.get(i).getUser().getProfile().getFullName());
			pojoPost.setPostTitle(listPost.get(i).getPostTitle());
			pojoPost.setPostContent(listPost.get(i).getPostContent());
			pojoPost.setCategoryId(listPost.get(i).getCategory().getId());
			pojoPost.setCategoryName(listPost.get(i).getCategory().getCategoryName());
			pojoPost.setPostTypeId(listPost.get(i).getPostType().getId());
			pojoPost.setTypeName(listPost.get(i).getPostType().getTypeName());
			
			listPojoPost.add(pojoPost);
		}
		
		return listPojoPost;
	}
	
}
