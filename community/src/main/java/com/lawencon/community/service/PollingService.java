package com.lawencon.community.service;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.PollingDetailDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.model.PollingDetail;
import com.lawencon.community.model.Post;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.pollingdetail.PojoPollingDetailInsertReq;
import com.lawencon.community.pojo.pollingdetail.PojoPollingDetailUpdateReq;

@Service
public class PollingService {

	private PollingDetailDao pollingDetailDao;
	private PostDao postDao;
	
	public PollingService(PollingDetailDao pollingDetailDao, PostDao postDao) {
		this.pollingDetailDao = pollingDetailDao;
		this.postDao = postDao;
	}
	
	public PojoInsertRes insertPollingDetail(final PojoPollingDetailInsertReq data) {
		final PollingDetail pollingDetail = new PollingDetail();
		
		
		final Post post = postDao.getByIdRef(Post.class, data.getPostId());
		post.setId(data.getPostId());
		pollingDetail.setPost(post);
		
		pollingDetail.setPollingQuestion(data.getPollingQuestion());

		PollingDetail eventInsert = null;
		ConnHandler.begin();
		eventInsert = pollingDetailDao.insert(pollingDetail);
		ConnHandler.commit();

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(eventInsert.getId());
		pojoInsertRes.setMessage("Success");
		return pojoInsertRes;
	}
	

	public PojoUpdateRes updatePollingDetail(final PojoPollingDetailUpdateReq data) {
		PollingDetail pollingDetailUpdate = null;
	
		pollingDetailUpdate = pollingDetailDao.getByIdAndDetach(data.getId());
		
		final PollingDetail pollingDetail = pollingDetailUpdate;

		pollingDetail.setPollingQuestion(data.getPollingQuestion());


		pollingDetail.setVersion(data.getVer());
		
		ConnHandler.begin();
		pollingDetailUpdate = pollingDetailDao.update(pollingDetail);
		ConnHandler.commit();

		final PojoUpdateRes pojoUpdate = new PojoUpdateRes();
		pojoUpdate.setVer(data.getVer());
		pojoUpdate.setMessage("Updated");
		return pojoUpdate;

	}
}
