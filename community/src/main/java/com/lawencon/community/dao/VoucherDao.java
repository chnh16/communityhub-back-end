package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
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
	
	@SuppressWarnings("unchecked")
	public Optional<Voucher> getVoucherById(final String id) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM voucher v  ")
			.append(" WHERE v.id = :id AND v.is_active = true");
		final List<Voucher> res = em().createNativeQuery(toStr(str), Voucher.class)
				.setParameter("id", id)
				.getResultList();
		return Optional.ofNullable(res.get(0));
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Voucher> getVoucher(final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM voucher v ")
			.append(" WHERE v.is_active = true");
		final List<Voucher> res = em().createNativeQuery(toStr(str).toString(), Voucher.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		return res;
	}

	public int getTotalVoucher() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(v.id) FROM voucher v ")
			.append(" WHERE v.is_active = true");
		
		final int totalVoucher = Integer.valueOf(ConnHandler.getManager().createNativeQuery(toStr(str)
				.toString())
				.getSingleResult().toString());
		return totalVoucher;
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
			 str.append("SELECT v.id, voucher_code, expired_date, amount, v.created_by, v.updated_by, v.created_at, v.updated_at, v.ver, v.is_active ")
			.append("FROM voucher v")
			.append(" WHERE v.id = (SELECT id FROM voucher WHERE voucher_code = :voucherCode) ")
			.append("AND expired_date >= NOW() ")
			.append("AND v.is_active = true");
			
			
			final Object result = em().createNativeQuery(toStr(str)).setParameter("voucherCode", voucherCode)
					.getSingleResult();
			
			if (result != null) {
				
				voucher = new Voucher();
				final Object[] objArr = (Object[]) result;
				
				voucher.setId(objArr[0].toString());
				
				voucher.setVoucherCode(objArr[1].toString());
				voucher.setExpiredDate(Timestamp.valueOf(objArr[2].toString()).toLocalDateTime());
				voucher.setAmount( new BigDecimal( (String) objArr[3].toString() ));
				
				voucher.setCreatedBy(objArr[4].toString());
				if (objArr[5] != null) {
					voucher.setUpdatedBy(objArr[5].toString());
				}
				
				voucher.setCreatedAt(Timestamp.valueOf(objArr[6].toString()).toLocalDateTime());
				
				if (objArr[7] != null) {
					voucher.setUpdatedAt(Timestamp.valueOf(objArr[7].toString()).toLocalDateTime());
				}

				voucher.setVersion(Integer.valueOf(objArr[8].toString()));
				voucher.setIsActive(Boolean.valueOf(objArr[9].toString()));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Optional.ofNullable(voucher);
	}

}
