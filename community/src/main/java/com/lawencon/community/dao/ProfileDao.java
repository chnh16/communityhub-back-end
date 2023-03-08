package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.Profile;

@Repository
public class ProfileDao extends MasterDao<Profile>{

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
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM position ").append(" WHERE is_active = true");
		final List<Profile> res = em().createNativeQuery(toStr(str), Profile.class).getResultList();
		return res;
	}

	@Override
	public Profile update(final Profile data) {
		final Profile res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(Profile.class, id);
	}

	@Override
	public Profile insert(final Profile data) {
		final Profile res = saveAndFlush(data);
		return res;
	}

}
