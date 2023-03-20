package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.PostType;

@Repository
public class PostTypeDao extends MasterDao<PostType> {

	@Override
	public Optional<PostType> getById(final String id) {
		final PostType res = getById(PostType.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public PostType getRefById(final String id) {
		final PostType res = getByIdRef(PostType.class, id);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostType> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM post_type ").append(" WHERE is_active = true");
		final List<PostType> res = em().createNativeQuery(toStr(str), PostType.class).getResultList();
		return res;
	}

	@Override
	public PostType update(final PostType data) {
		final PostType res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(PostType.class, id);
	}

	@Override
	public PostType insert(final PostType data) {
		final PostType res = saveAndFlush(data);
		return res;
	}

	@Override
	public PostType getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(PostType.class, id);
	}

	public Optional<PostType> getByPostTypeCode(final String postTypeCode) {
		PostType postType = null;

		try {
			final StringBuilder str = new StringBuilder();
			str.append("SELECT pt.id, pt.created_by, pt.updated_by, pt.created_at, pt.updated_at, pt.ver, pt.is_active")
					.append(" FROM post_type pt")
					.append(" WHERE pt.type_code = :postTypeCode")
					.append(" AND pt.is_active = TRUE");
			final Object res = em().createNativeQuery(toStr(str)).setParameter("postTypeCode", postTypeCode).getSingleResult();
			
			if(res != null) {
				postType = new PostType();
				final Object[] objArr = (Object[]) res;
				
				postType.setId(objArr[0].toString());
				postType.setCreatedBy(objArr[1].toString());
				if(objArr[2] != null) {
					postType.setUpdatedBy(objArr[2].toString());
				}
				postType.setCreatedAt(Timestamp.valueOf(objArr[3].toString()).toLocalDateTime());
				if(objArr[4] != null) {
					postType.setUpdatedAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				}
				postType.setVersion(Integer.valueOf(objArr[5].toString()));
				postType.setIsActive(Boolean.valueOf(objArr[6].toString()));
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(postType);
	}

}
