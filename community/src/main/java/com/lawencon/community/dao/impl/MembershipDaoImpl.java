package com.lawencon.community.dao.impl;

import java.util.List;
import java.util.Optional;

import com.lawencon.community.dao.MembershipDao;
import com.lawencon.community.model.Membership;

public class MembershipDaoImpl extends BaseDaoImpl implements MembershipDao{

	@Override
	public Optional<Membership> getById(final Long id) {
		final Membership res = getById(Membership.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<Membership> getRefById(final Long id) {
		final Membership res = getByIdRef(Membership.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Membership> getAll() {
		final String sql = "SELECT * FROM membership" 
				+ " WHERE is_active = true";
		final List<Membership> res = em().createNativeQuery(sql, Membership.class).getResultList();
		return res;
	}

	@Override
	public Membership update(final Membership data) {
		final Membership res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(Membership.class, id);
	}

	@Override
	public Membership insert(final Membership data) {
		final Membership res = saveAndFlush(data);
		return res;
	}
	

}
