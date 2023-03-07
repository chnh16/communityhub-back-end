package com.lawencon.community.dao.impl;

import java.util.List;
import java.util.Optional;

import com.lawencon.community.dao.MembershipDao;
import com.lawencon.community.model.Membership;

public class MembershipDaoImpl extends BaseDaoImpl implements MembershipDao{

	@Override
	public Optional<Membership> getById(Long id) {
		final Membership res = getById(Membership.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<Membership> getRefById(Long id) {
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
	public Membership update(Membership data) {
		Membership res = em().merge(data);
		return res;
	}

	@Override
	public boolean delete(Long id) {
		return deleteById(Membership.class, id);
	}
	

}
