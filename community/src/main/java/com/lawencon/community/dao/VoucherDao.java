package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.Transaction;
import com.lawencon.community.model.Voucher;

@Repository
public class VoucherDao extends MasterDao<Voucher> {

	@Override
	public Optional<Voucher> getById(final String id) {
		final Voucher res = getById(Voucher.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Voucher getRefById(final String id) {
		final Voucher res = getByIdRef(Voucher.class, id);
		return res;
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
	public boolean delete(final String id) {
		return deleteById(Voucher.class, id);
	}

	@Override
	public Voucher insert(final Voucher data) {
		final Voucher res = saveAndFlush(data);
		return res;
	}

	@Override
	public Voucher getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(Voucher.class, id);
	}

	public Optional<Voucher> getByVoucherCode(final String voucherCode) {
		final StringBuilder str = new StringBuilder();
		Voucher voucher = null;
		
		try {
			final String sql = "SELECT v.id "
			+ "FROM voucher v"
			+ " WHERE v.voucher_code = :voucherCode AND v.is_active = true";
			
			final Object result = em().createNativeQuery(sql, Voucher.class)
					.setParameter("voucherCode", voucherCode).getSingleResult();
			
			if (result != null) {
				voucher = new Voucher();
				final Object[] objArr = (Object[]) result;
				
				voucher.setId(objArr[0].toString());
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Optional.ofNullable(voucher);
	}

}
