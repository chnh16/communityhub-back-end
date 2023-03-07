package com.lawencon.community.dao.impl;

import java.util.List;
import java.util.Optional;

import com.lawencon.community.dao.RoleDao;
import com.lawencon.community.model.Role;

public class RoleDaoImpl extends BaseDaoImpl implements RoleDao {

	@Override
	public Optional<Role> getById(final Long id) {
		final Role res = getById(Role.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<Role> getRefById(final Long id) {
		final Role res = getById(Role.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAll() {
		final String sql = "SELECT * FROM role" + " WHERE is_active = true";
		final List<Role> res = em().createNativeQuery(sql, Role.class).getResultList();
		return res;
	}

	@Override
	public Role update(final Role data) {
		final Role res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(Role.class, id);
	}

	@Override
	public Role insert(final Role data) {
		final Role res = saveAndFlush(data);
		return res;
	}

}
