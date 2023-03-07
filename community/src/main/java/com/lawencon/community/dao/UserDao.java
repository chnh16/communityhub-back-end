package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.User;

@Repository
public class UserDao extends MasterDao<User>{

	@Override
	public Optional<User> getById(final Long id) {
		final User res = getById(User.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<User> getRefById(final Long id) {
		final User res = getByIdRef(User.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {
		final String sql = "SELECT * FROM user" + " WHERE is_active = true";
		final List<User> res = em().createNativeQuery(sql, User.class).getResultList();
		return res;
	}

	@Override
	public User update(final User data) {
		final User res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(User.class, id);
	}

	@Override
	public User insert(final User data) {
		final User res = saveAndFlush(data);
		return res;
	}

}
