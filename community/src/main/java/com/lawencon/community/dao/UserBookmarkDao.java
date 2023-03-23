package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.UserBookmark;

@Repository
public class UserBookmarkDao extends BasePostDao<UserBookmark> {

	@Override
	public UserBookmark insert(final UserBookmark data) {
		final UserBookmark res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(UserBookmark.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserBookmark> getAllByUser(final String userId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM user_bookmark ")
			.append("WHERE user_id = :userId AND is_active = true ");
		final List<UserBookmark> res = em().createNativeQuery(toStr(str), UserBookmark.class)
				.setParameter("userId", userId)
				.getResultList();
		return res;
	}
	
	public Long getCount(final String postId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(ub.post_id) FROM user_bookmark ub ")
		.append("INNER JOIN post p ON p.id = ub.post_id ")
		.append("WHERE ub.post_id = :postId ");
		
		Long countLike = null;
		countLike = Long.valueOf(ConnHandler.getManager().createNativeQuery(toStr(str).toString())
				.setParameter("postId", postId)
				.getSingleResult().toString());
		
		return countLike;
	}
	
	public Optional<UserBookmark> getBookmarkByPostId(final String postId, final String userId) {
		UserBookmark userBookmark = null;
		try {
			final StringBuilder str = new StringBuilder();
			str.append("SELECT ub.id FROM user_bookmark ub").append(" WHERE ub.post_id = :postId")
					.append(" AND ub.user_id = :userId").append(" AND ub.is_active = TRUE");
			final Object res = em().createNativeQuery(toStr(str)).setParameter("postId", postId)
					.setParameter("userId", userId).getSingleResult();
			if (res != null) {
				userBookmark = new UserBookmark();
				final Object[] objArr = (Object[]) res;
				userBookmark.setId(objArr[0].toString());
			}
		} catch(final Exception e){
			e.printStackTrace();
		}
		return Optional.ofNullable(userBookmark);
	}

}
