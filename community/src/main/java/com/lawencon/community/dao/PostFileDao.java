package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.PostFile;

@Repository
public class PostFileDao extends BasePostDao<PostFile>{

	@Override
	public PostFile insert(PostFile data) {
		final PostFile res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(Long id) {
		return deleteById(PostFile.class, id);
	}
	
	

}