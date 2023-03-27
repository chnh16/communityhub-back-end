package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.PollingAnswer;
import com.lawencon.community.model.PollingChoice;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.pollinganswer.PojoPollingAnswerGetCountRes;

@Repository
public class PollingAnswerDao extends BasePostDao<PollingAnswer> {

	@Override
	public PollingAnswer insert(final PollingAnswer data) {
		final PollingAnswer res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(PollingAnswer.class, id);
	}

	public Optional<PollingAnswer> getByPostIdAndUserId(final String postId, final String userId) {
		PollingAnswer pollingAnswer = null;
		try {
			final StringBuilder str = new StringBuilder();
			str.append(
					"SELECT pa.id, pa.user_id, pa.post_id, pa.polling_choice_id FROM polling_answer pa")
					.append(" WHERE pa.user_id = :userId").append(" AND pa.post_id = :postId")
					.append(" AND pa.is_active = TRUE");
			final Object res = em().createNativeQuery(toStr(str)).setParameter("userId", userId)
					.setParameter("postId", postId).getSingleResult();
			if (res != null) {
				pollingAnswer = new PollingAnswer();
				final Object[] objArr = (Object[]) res;
				final User user = new User();
				user.setId(objArr[1].toString());
				final Post post = new Post();
				post.setId(objArr[2].toString());
				final PollingChoice pollingChoice = new PollingChoice();
				pollingChoice.setId(objArr[3].toString());
				pollingAnswer.setId(objArr[0].toString());
				pollingAnswer.setUser(user);
				pollingAnswer.setPost(post);
				pollingAnswer.setPollingChoice(pollingChoice);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(pollingAnswer);
	}

	@SuppressWarnings("unchecked")
	public List<PollingAnswer> getByUserId(final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM polling_answer pa ").append("WHERE pa.user_id = :userId");
		final List<PollingAnswer> res = em().createNativeQuery(toStr(str), PollingAnswer.class)
				.setParameter("userId", userId).getResultList();
		return res;
	}

	public Long getCount(final String detailId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(pa.polling_choice_id) FROM polling_answer pa ")
				.append(" INNER JOIN polling_choice pc ON pc.id = pa.polling_choice_id ")
				.append(" WHERE pc.polling_detail_id = :detailId");

		Long countPoll = null;
		countPoll = Long.valueOf(ConnHandler.getManager().createNativeQuery(toStr(str).toString())
				.setParameter("detailId", detailId).getSingleResult().toString());

		return countPoll;
	}

	@SuppressWarnings("unchecked")
	public List<PojoPollingAnswerGetCountRes> getCountByChoiceId(final String detailId) {
		final List<PojoPollingAnswerGetCountRes> listPollingAnswer = new ArrayList<>();

		try {
			final StringBuilder str = new StringBuilder();
			str.append("SELECT pc.id, COUNT(pa.polling_choice_id) FROM polling_answer pa ")
					.append(" INNER JOIN polling_choice pc ON pc.id = pa.polling_choice_id ").append("GROUP BY pc.id ")
					.append(" HAVING pc.polling_detail_id = :detailId");

			final List<Object> result = em().createNativeQuery(toStr(str)).setParameter("detailId", detailId)
					.getResultList();

			if (result != null) {
				for (final Object objs : result) {
					final Object[] obj = (Object[]) objs;

					final PojoPollingAnswerGetCountRes pollingChoice = new PojoPollingAnswerGetCountRes();
					pollingChoice.setPollingChoiceId(obj[0].toString());
					pollingChoice.setCountPollAnswer(Long.valueOf(obj[1].toString()));

					listPollingAnswer.add(pollingChoice);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listPollingAnswer;
	}

}
