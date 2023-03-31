package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.PostFile;

@Repository
public class PostFileDao extends BasePostDao<PostFile>{

	@Override
	public PostFile insert(final PostFile data) {
		final PostFile res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(PostFile.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<PostFile> getAllPostFile(final String postId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM post_file pf ")
			.append(" INNER JOIN post p ON p.id = pf.post_id ")
			.append(" INNER JOIN file f ON f.id = pf.file_id ")
			.append("WHERE pf.post_id = :postId AND pf.is_active = true ");
		final List<PostFile> res = em().createNativeQuery(toStr(str), PostFile.class)
				.setParameter("postId", postId)
				.getResultList();
		return res;
	}

	@Override
	PostFile getByIdAndDetach(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
