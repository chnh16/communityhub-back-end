package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.PollingAnswerDao;
import com.lawencon.community.dao.PollingChoiceDao;
import com.lawencon.community.model.PollingChoice;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.pollinganswer.PojoPollingAnswerGetCountRes;
import com.lawencon.community.pojo.pollingchoice.PojoPollingChoiceGetAllRes;

@Service
public class PollingService {
	private final PollingChoiceDao pollingChoiceDao;
	private final PollingAnswerDao pollingAnswerDao;

	public PollingService(final PollingChoiceDao pollingChoiceDao, final PollingAnswerDao pollingAnswerDao) {
		this.pollingChoiceDao = pollingChoiceDao;
		this.pollingAnswerDao = pollingAnswerDao;
	}

	public PollingChoice insertPollingChoice(final PollingChoice pollingChoice) {
		PollingChoice insertChoice = null;
		ConnHandler.begin();
		insertChoice = pollingChoiceDao.insert(pollingChoice);
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
