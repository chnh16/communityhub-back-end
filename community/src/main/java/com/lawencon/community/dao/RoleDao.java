package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.Role;

@Repository
public class RoleDao extends MasterDao<Role>{

	@Override
	public Optional<Role> getById(final String id) {
		final Role res = getById(Role.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Role getRefById(final String id) {
		final Role res = getById(Role.class, id);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM role ").append(" WHERE is_active = true");
		final List<Role> res = em().createNativeQuery(toStr(str), Role.class).getResultList();
		return res;
	}

	@Override
	public Role update(final Role data) {
		final Role res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(Role.class, id);
	}

	@Override
	public Role insert(final Role data) {
		final Role res = saveAndFlush(data);
		return res;
	}

	@Override
	public Role getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(Role.class, id);
	}

}
