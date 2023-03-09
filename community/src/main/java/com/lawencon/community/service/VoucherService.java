package com.lawencon.community.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.VoucherDao;
import com.lawencon.community.model.Voucher;
import com.lawencon.security.principal.PrincipalServiceImpl;

@Service
public class VoucherService extends PrincipalServiceImpl{
	private final VoucherDao voucherDao;
	
	public VoucherService(final VoucherDao voucherDao) {
		this.voucherDao = voucherDao;
	}
	
	private void valIdNull(final Voucher data) {
		if(data.getId() != null) {
			throw new RuntimeException("ID harus kosong");
		}
	}
	
	private void valIdNotNull(final Voucher data) {
		if(data.getId() == null) {
			throw new RuntimeException("ID kosong");
		}
	}
	
	private void valNotNullable(final Voucher data) {
		if(data.getVoucherCode() == null) {
			throw new RuntimeException("Voucher Code Kosong");
		}
		if(data.getExpiredDate() == null) {
			throw new RuntimeException("Expired Date Kosong");
		}
		if(data.getAmount() == null) {
			throw new RuntimeException("Nominal Voucher Kosong");
		}
	}
	
	public List<Voucher> getAll(){
		return voucherDao.getAll();
	}
	
	public Voucher insert(final Voucher data) {
		Voucher voucherInsert = null;
		try {
			ConnHandler.begin();
			valIdNull(data);
			valNotNullable(data);
			voucherInsert = voucherDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return voucherInsert;
	}
	
	public Voucher update(final Voucher data) {
		Voucher voucherUpdate = null;
		valIdNotNull(data);
		
		final Optional<Voucher> res = voucherDao.getById(Long.valueOf(data.getId()));
		if(res.isEmpty()) {
			throw new RuntimeException("Voucher tidak ditemukan");
		}
		final Voucher voucher = res.get();
		try {
			ConnHandler.getManager().detach(voucherUpdate);		
			ConnHandler.begin();
			voucher.setAmount(data.getAmount());
			voucher.setVoucherCode(data.getVoucherCode());
			voucher.setExpiredDate(data.getExpiredDate());
			voucher.setUpdatedBy(getAuthPrincipal());
			voucher.setUpdatedAt(LocalDateTime.now());
			voucherUpdate = voucherDao.update(voucher);
			ConnHandler.commit();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		
		return voucherUpdate;
	}
	
	public boolean deleteById(final Long id){
		boolean voucherDelete = false;

		try {
			ConnHandler.begin();
			voucherDelete = voucherDao.deleteById(Voucher.class, id);
			ConnHandler.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return voucherDelete;
	}
	
}
