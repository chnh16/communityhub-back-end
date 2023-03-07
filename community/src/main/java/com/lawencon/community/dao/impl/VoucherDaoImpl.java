package com.lawencon.community.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.VoucherDao;
import com.lawencon.community.model.Voucher;

public class VoucherDaoImpl extends AbstractJpaDao implements VoucherDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Override
	public Optional<Voucher> getById(final Long id) {
		final Voucher res = getById(Voucher.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<Voucher> getRefById(final Long id) {
		final Voucher res = getByIdRef(Voucher.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Voucher> getAll() {
		final String sql = "SELECT * FROM voucher" 
				+ " WHERE is_active = true";
		final List<Voucher> res = em().createNativeQuery(sql, Voucher.class).getResultList();
		return res;
	}

	@Override
	public Voucher update(final Voucher data) {
		final Voucher res = em().merge(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(Voucher.class, id);
	}

}
