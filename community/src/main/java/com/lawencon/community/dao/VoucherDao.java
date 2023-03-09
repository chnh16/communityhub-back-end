package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import com.lawencon.community.model.Voucher;

public class VoucherDao extends MasterDao<Voucher>{

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
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM voucher ").append(" WHERE is_active = true");
		final List<Voucher> res = em().createNativeQuery(toStr(str), Voucher.class).getResultList();
		return res;
	}

	@Override
	public Voucher update(final Voucher data) {
		final Voucher res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(Voucher.class, id);
	}

	@Override
	public Voucher insert(final Voucher data) {
		final Voucher res = saveAndFlush(data);
		return res;
	}

	@Override
	public Voucher getByIdAndDetach(final Long id) {
		return super.getByIdAndDetach(Voucher.class, id);
	}

}
