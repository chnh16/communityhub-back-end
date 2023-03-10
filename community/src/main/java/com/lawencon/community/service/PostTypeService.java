package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.model.PostType;
import com.lawencon.community.pojo.posttype.PojoPostTypeGetAllRes;

@Service
public class PostTypeService {
	private final PostTypeDao postTypeDao;
	
	public PostTypeService(final PostTypeDao postTypeDao) {
		this.postTypeDao = postTypeDao;
	}
	
	public List<PostType> getAll() {
		return postTypeDao.getAll();
	}
	
	public List<PojoPostTypeGetAllRes> getAllRes() {
		final List<PojoPostTypeGetAllRes> postTypeGetAllRes = new ArrayList<>();
		final List<PostType> postTypes = getAll();
		
		for(int i =0 ; i <postTypes.size();i++) {
			final PojoPostTypeGetAllRes pojoPostType = new PojoPostTypeGetAllRes();
			
			pojoPostType.setId(postTypes.get(i).getId());
			pojoPostType.setTypeCode(postTypes.get(i).getTypeCode());
			pojoPostType.setTypeName(postTypes.get(i).getTypeName());
			
			postTypeGetAllRes.add(pojoPostType);
			
		}
		return postTypeGetAllRes;
	}

}
