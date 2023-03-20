package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.PollingAnswerDao;
import com.lawencon.community.dao.PollingChoiceDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.PollingAnswer;
import com.lawencon.community.model.PollingChoice;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.pollinganswer.PojoPollingAnswerGetCountRes;
import com.lawencon.community.pojo.pollinganswer.PojoPollingAnswerInsertReq;
import com.lawencon.community.pojo.pollingchoice.PojoPollingChoiceGetAllRes;

@Service
public class PollingService {
	private final PollingChoiceDao pollingChoiceDao;
	private final PollingAnswerDao pollingAnswerDao;
	private final UserDao userDao;

	public PollingService(final PollingChoiceDao pollingChoiceDao,
			final PollingAnswerDao pollingAnswerDao, final UserDao userDao) {
		this.pollingChoiceDao = pollingChoiceDao;
		this.pollingAnswerDao = pollingAnswerDao;
		this.userDao = userDao;
	}

	public PollingChoice insertPollingChoice(final PollingChoice pollingChoice) {
		PollingChoice insertChoice = null;
		ConnHandler.begin();
		insertChoice = pollingChoiceDao.insert(insertChoice);
		ConnHandler.commit();
		return insertChoice;
	}

	public List<PojoPollingChoiceGetAllRes> getAllChoice(final String postId) {
		final List<PojoPollingChoiceGetAllRes> pojos = new ArrayList<>();
		final List<PollingChoice> choices = pollingChoiceDao.getChoiceByPost(postId);
		for (int i = 0; i < choices.size(); i++) {
			final PojoPollingChoiceGetAllRes pojo = new PojoPollingChoiceGetAllRes();
			final PollingChoice choice = choices.get(i);
			pojo.setChoiceContent(choice.getChoiceContent());
			pojo.setPollingChoiceId(choice.getId());

			pojos.add(pojo);
		}
		return pojos;
	}

//	public List<PojoPollingDetailGetAllRes> getAllDetail(final String postId, final String detailId) {
//		final List<PojoPollingDetailGetAllRes> listPojoPollingDetail = new ArrayList<>();
//		final List<PollingChoice> listChoices = pollingChoiceDao.getChoiceByPost(postId);
//		
//		for (int i = 0; i < listPollingDetail.size(); i++) {
//			final PojoPollingDetailGetAllRes pojoPollingDetail = new PojoPollingDetailGetAllRes();
//			pojoPollingDetail.setId(listPollingDetail.get(i).getId());
//			pojoPollingDetail.setPostId(listPollingDetail.get(i).getPost().getId());
//			pojoPollingDetail.setPollingQuestion(listPollingDetail.get(i).getPollingQuestion());
//			
//			final List<PojoPollingChoiceGetAllRes> listPojoChoice = new ArrayList<>();
//			
//			for(int j = 0; j < listChoices.size(); j++) {
//				final PojoPollingChoiceGetAllRes pojoChoice = new PojoPollingChoiceGetAllRes();
//				
//				if(listChoices.get(j) != null) {
//					if(listPollingDetail.get(i).getId() == listChoices.get(j).getPollingDetail().getId()) {
//						pojoChoice.setPollingDetailId(detailId);
//						pojoChoice.setPollingChoiceId(listChoices.get(j).getId());
//						pojoChoice.setChoiceContent(listChoices.get(j).getChoiceContent());
//						
//						listPojoChoice.add(pojoChoice);
//					}
//				}
//			}
//			pojoPollingDetail.setListChoice(listPojoChoice);
//			listPojoPollingDetail.add(pojoPollingDetail);
//		}
//		return listPojoPollingDetail;
//	}

	public boolean deletePollingById(final String id) {
		boolean pollingDelete = false;

		try {
			ConnHandler.begin();
			pollingDelete = pollingChoiceDao.deleteById(PollingChoice.class, id);
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
