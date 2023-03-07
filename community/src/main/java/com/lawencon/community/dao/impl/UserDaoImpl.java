package com.lawencon.community.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.User;

@Repository
public class UserDaoImpl extends AbstractJpaDao implements UserDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Override
	public Optional<User> getById(final Long id) {
		User res = getById(User.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<User> getRefById(final Long id) {
		User res = getByIdRef(User.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {
		final String sql = "SELECT * FROM user" 
				+ " WHERE is_active = true";
		final List<User> res = em().createNativeQuery(sql, User.class).getResultList();
		return res;
	}

	@Override
	public User update(final User data) {
		User res = em().merge(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(User.class, id);
	}

}
