package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.UserLike;

@Repository
public class UserLikeDao extends BasePostDao<UserLike> {

	@Override
	public UserLike insert(final UserLike data) {
		final UserLike res = saveAndFlush(data);
		return res;
	}

	@SuppressWarnings("unchecked")
	public List<UserLike> getLikeByUserId(final String userId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM user_like ul ").append("WHERE ul.user_id = :userId");
		final List<UserLike> res = em().createNativeQuery(toStr(str), UserLike.class).setParameter("userId", userId)
				.getResultList();
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(UserLike.class, id);
	}

	public boolean deleteUserLikeById(final String userId, final String postId) {
		try {
			final StringBuilder str = new StringBuilder();
			str.append("DELETE FROM user_like ul ").append("WHERE ul.user_id = :user AND ul.post_id = :post ");
			final int rs = em().createNativeQuery(toStr(str)).setParameter("user", userId).setParameter("post", postId)
					.executeUpdate();

			return rs > 0;
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public Long getCount(final String postId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(ul.post_id) FROM user_like ul ").append("INNER JOIN post p ON p.id = ul.post_id ")
				.append("WHERE ul.post_id = :postId ");

		Long countLike = null;
		countLike = Long.valueOf(ConnHandler.getManager().createNativeQuery(toStr(str).toString())
				.setParameter("postId", postId).getSingleResult().toString());

		return countLike;
	}

	public Optional<UserLike> getLikeByPostId(final String postId, final String userId) {
		UserLike userLike = null;
		try {
			final StringBuilder str = new StringBuilder();
			str.append("SELECT ul.id FROM user_like ul").append(" WHERE ul.post_id = :postId")
					.append(" AND ul.user_id = :userId").append(" AND ul.is_active = TRUE");
			final Object res = em().createNativeQuery(toStr(str)).setParameter("postId", postId)
					.setParameter("userId", userId).getSingleResult();
			if (res != null) {
				userLike = new UserLike();
				final String objArr = (String) res;
				userLike.setId(objArr);
			}
		} catch(final Exception e){
			e.printStackTrace();
		}
		return Optional.ofNullable(userLike);
	}

}
