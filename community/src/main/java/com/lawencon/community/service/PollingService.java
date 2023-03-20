package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.PollingAnswerDao;
import com.lawencon.community.dao.PollingChoiceDao;
import com.lawencon.community.dao.PollingDetailDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.PollingAnswer;
import com.lawencon.community.model.PollingChoice;
import com.lawencon.community.model.PollingDetail;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.pollinganswer.PojoPollingAnswerGetCountRes;
import com.lawencon.community.pojo.pollinganswer.PojoPollingAnswerInsertReq;
import com.lawencon.community.pojo.pollingchoice.PojoPollingChoiceGetAllRes;
import com.lawencon.community.pojo.pollingdetail.PojoPollingDetailGetAllRes;
import com.lawencon.community.pojo.pollingdetail.PojoPollingDetailInsertReq;
import com.lawencon.community.pojo.pollingdetail.PojoPollingDetailUpdateReq;

@Service
public class PollingService {

	private final PollingDetailDao pollingDetailDao;
	private final PostDao postDao;
	private final PollingChoiceDao pollingChoiceDao;
	private final PollingAnswerDao pollingAnswerDao;
	private final UserDao userDao;

	public PollingService(final PollingDetailDao pollingDetailDao, final PostDao postDao,
			final PollingChoiceDao pollingChoiceDao, final PollingAnswerDao pollingAnswerDao, final UserDao userDao) {
		this.pollingDetailDao = pollingDetailDao;
		this.postDao = postDao;
		this.pollingChoiceDao = pollingChoiceDao;
		this.pollingAnswerDao = pollingAnswerDao;
		this.userDao = userDao;
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
		
		for (int i = 0; i < data.getChoices().size(); i++) {
			final PollingChoice pollingChoice = new PollingChoice();

			pollingChoice.setPollingDetail(eventInsert);
			pollingChoice.setChoiceContent(data.getChoices().get(i).getChoiceContent());

			pollingChoice.setIsActive(true);

			PollingChoice pollingChoiceInsert = null;
			ConnHandler.begin();
			pollingChoiceInsert = pollingChoiceDao.insert(pollingChoice);
			ConnHandler.commit();
		}

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(eventInsert.getId());
		pojoInsertRes.setMessage("Success");
		return pojoInsertRes;
	}

	public PojoUpdateRes updatePollingDetail(final PojoPollingDetailUpdateReq data) {
		PollingDetail pollingDetailUpdate = null;

		final PollingDetail pollingDetail = pollingDetailDao.getByIdAndDetach(data.getId());

		if (data.getPollingQuestion() != null) {
			pollingDetail.setPollingQuestion(data.getPollingQuestion());
		}

		if (data.getVer() != null) {
			pollingDetail.setVersion(data.getVer());
		}

		ConnHandler.begin();
		pollingDetailUpdate = pollingDetailDao.update(pollingDetail);
		ConnHandler.commit();
		
		System.out.println(data.getChoices().size());
		for (int i = 0; i < data.getChoices().size(); i++) {
			PollingChoice pollingChoiceUpdate = null;
			pollingChoiceUpdate = pollingChoiceDao.getByIdAndDetach(data.getChoices().get(i).getId());

			final PollingChoice pollingChoice = pollingChoiceUpdate;

			if (data.getChoices().get(i).getChoiceContent() != null) {
				pollingChoice.setChoiceContent(data.getChoices().get(i).getChoiceContent());
			}
			
			if (data.getChoices().get(i).getVer() != null) {
				pollingChoice.setVersion(data.getChoices().get(i).getVer());
			}

			ConnHandler.begin();
			pollingChoiceUpdate = pollingChoiceDao.update(pollingChoice);
			ConnHandler.commit();
		}

		final PojoUpdateRes pojoUpdate = new PojoUpdateRes();
		pojoUpdate.setVer(data.getVer());
		pojoUpdate.setMessage("Updated");
		return pojoUpdate;

	}

	public List<PojoPollingDetailGetAllRes> getAllDetail(final String postId, final String detailId) {
		final List<PojoPollingDetailGetAllRes> listPojoPollingDetail = new ArrayList<>();

		final List<PollingDetail> listPollingDetail = pollingDetailDao.getAllByPostId(postId);
		final List<PollingChoice> listChoices = pollingChoiceDao.getChoiceByDetail(detailId);
		
		for (int i = 0; i < listPollingDetail.size(); i++) {
			final PojoPollingDetailGetAllRes pojoPollingDetail = new PojoPollingDetailGetAllRes();
			pojoPollingDetail.setId(listPollingDetail.get(i).getId());
			pojoPollingDetail.setPostId(listPollingDetail.get(i).getPost().getId());
			pojoPollingDetail.setPollingQuestion(listPollingDetail.get(i).getPollingQuestion());
			
			final List<PojoPollingChoiceGetAllRes> listPojoChoice = new ArrayList<>();
			
			for(int j = 0; j < listChoices.size(); j++) {
				final PojoPollingChoiceGetAllRes pojoChoice = new PojoPollingChoiceGetAllRes();
				
				if(listChoices.get(j) != null) {
					if(listPollingDetail.get(i).getId() == listChoices.get(j).getPollingDetail().getId()) {
						pojoChoice.setPollingDetailId(detailId);
						pojoChoice.setPollingChoiceId(listChoices.get(j).getId());
						pojoChoice.setChoiceContent(listChoices.get(j).getChoiceContent());
						
						listPojoChoice.add(pojoChoice);
					}
				}
			}
			pojoPollingDetail.setListChoice(listPojoChoice);
			listPojoPollingDetail.add(pojoPollingDetail);
		}
		return listPojoPollingDetail;
	}

	public boolean deletePollingById(final String id) {
		boolean pollingDelete = false;

		try {
			ConnHandler.begin();
			pollingDelete = pollingDetailDao.deleteById(PollingDetail.class, id);
			ConnHandler.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return pollingDelete;
	}

	public PojoDeleteRes deletePollingRes(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deletePollingById(id);
		res.setMessage("Berhasil Menghapus Data");
		return res;
	}

	public PojoInsertRes insertPollingAnswer(final PojoPollingAnswerInsertReq data) {
		final PollingAnswer pollingAnswer = new PollingAnswer();

		final PollingChoice pollingChoice = pollingChoiceDao.getRefById(data.getPollingChoiceId());
		pollingChoice.setId(data.getPollingChoiceId());
		pollingAnswer.setPollingChoice(pollingChoice);

		final User user = userDao.getRefById(data.getUserId());
		pollingAnswer.setUser(user);

		pollingAnswer.setIsActive(true);

		PollingAnswer pollingAnswerInsert = null;
		ConnHandler.begin();
		pollingAnswerInsert = pollingAnswerDao.insert(pollingAnswer);
		ConnHandler.commit();

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(pollingAnswerInsert.getId());
		pojoInsertRes.setMessage("Success");
		return pojoInsertRes;
	}

	public PojoPollingAnswerGetCountRes getCountAnswer(final String detailId) {
		final PojoPollingAnswerGetCountRes pojoAnswerCount = new PojoPollingAnswerGetCountRes();

		pojoAnswerCount.setCountPollAnswer(pollingAnswerDao.getCount(detailId));

		return pojoAnswerCount;
	}

	public List<PojoPollingAnswerGetCountRes> getAnswerByChoiceId(final String detailId) {

		final List<PojoPollingAnswerGetCountRes> listAnswer = pollingAnswerDao.getCountByChoiceId(detailId);

		final Long totalChoice = pollingAnswerDao.getCount(detailId);

		for (int i = 0; i < listAnswer.size(); i++) {
			final PojoPollingAnswerGetCountRes pojoAnswerCount = listAnswer.get(i);
			float percent = (listAnswer.get(i).getCountPollAnswer() * (100 / totalChoice));
			
			pojoAnswerCount.setPercent(percent);
			
		}
		return listAnswer;
	}
	
}
