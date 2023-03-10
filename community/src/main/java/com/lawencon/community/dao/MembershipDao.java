package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.Membership;

@Repository
public class MembershipDao extends MasterDao<Membership>{

	@Override
	public Optional<Membership> getById(final String id) {
		final Membership res = getById(Membership.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Membership getRefById(final String id) {
		final Membership res = getByIdRef(Membership.class, id);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Membership> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM membership ").append(" WHERE is_active = true");
		final List<Membership> res = em().createNativeQuery(toStr(str), Membership.class).getResultList();
		return res;
	}

	@Override
	public Membership update(final Membership data) {
		final Membership res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(Membership.class, id);
	}

	@Override
	public Membership insert(final Membership data) {
		final Membership res = saveAndFlush(data);
		return res;
	}

	@Override
	public Membership getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(Membership.class, id);
	}
	

}
