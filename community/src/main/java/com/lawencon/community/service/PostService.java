package com.lawencon.community.service;

import java.util.List;
import java.util.Optional;

import com.lawencon.community.dao.PostDao;
import com.lawencon.community.model.Post;

public class PostService {
	
	private final PostDao postDao;
	
	public PostService(final PostDao postDao) {
		this.postDao = postDao;
	}
	
	public Optional<Post> getById(final String id){
		return postDao.getById(id);
	}
	
	public Post getByIdAndDetach(final String id) {
		return postDao.getByIdAndDetach(Post.class, id);
	}
	
	public Optional<Post> getRefById(final String id){
		return postDao.getRefById(id);
	}
	
	public List<Post> getAll(){
		return postDao.getAll();
	}
	
	
}
