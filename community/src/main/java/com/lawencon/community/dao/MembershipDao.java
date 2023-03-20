package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
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
	
	@SuppressWarnings("unchecked")
	public Optional<Membership> getMembershipById(final String id) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM membership m")
			.append(" WHERE m.id = :id AND m.is_active = true");
		final List<Membership> res = em().createNativeQuery(toStr(str), Membership.class)
				.setParameter("id", id)
				.getResultList();
		return Optional.ofNullable(res.get(0));
	}

	@SuppressWarnings("unchecked")
	public List<Membership> getMembership(final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM membership m ")
			.append(" WHERE m.is_active = true");
		final List<Membership> res = em().createNativeQuery(toStr(str).toString(), Membership.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		return res;
	}

	public int getTotalMembership() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(m.id) FROM membership m ")
			.append(" WHERE m.is_active = true");
		
		final int totalMembership = Integer.valueOf(ConnHandler.getManager().createNativeQuery(toStr(str)
				.toString())
				.getSingleResult().toString());
		return totalMembership;
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
