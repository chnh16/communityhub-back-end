package com.lawencon.community.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.model.Profile;

public class ProfileDaoImpl extends AbstractJpaDao implements ProfileDao {
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Override
	public Optional<Profile> getById(final Long id) {
		final Profile res = getById(Profile.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<Profile> getRefById(final Long id) {
		final Profile res = getByIdRef(Profile.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Profile> getAll() {
		final String sql = "SELECT * FROM position" 
				+ " WHERE is_active = true";
		final List<Profile> res = em().createNativeQuery(sql, Profile.class).getResultList();
		return res;
	}

	@Override
	public Profile update(final Profile data) {
		Profile res = em().merge(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(Profile.class, id);
	}

}
