package com.lawencon.community.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.VoucherDao;
import com.lawencon.community.model.Event;
import com.lawencon.community.model.Transaction;
import com.lawencon.community.model.Voucher;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.event.PojoEventResGetByCategoryId;
import com.lawencon.community.pojo.voucher.PojoVoucherGetAllRes;
import com.lawencon.community.pojo.voucher.PojoVoucherGetByVoucherCodeRes;
import com.lawencon.community.pojo.voucher.PojoVoucherInsertReq;
import com.lawencon.community.pojo.voucher.PojoVoucherUpdateReq;

@Service
public class VoucherService {
	private final VoucherDao voucherDao;

	public VoucherService(final VoucherDao voucherDao) {
		this.voucherDao = voucherDao;
	}

	private void valIdNull(final Voucher data) {
		if (data.getId() != null) {
			throw new RuntimeException("ID harus kosong");
		}
	}

	private void valIdNotNull(final Voucher data) {
		if (data.getId() == null) {
			throw new RuntimeException("ID kosong");
		}
	}

	private void valNotNullable(final Voucher data) {
		if (data.getVoucherCode() == null) {
			throw new RuntimeException("Voucher Code Kosong");
		}
		if (data.getExpiredDate() == null) {
			throw new RuntimeException("Expired Date Kosong");
		}
		if (data.getAmount() == null) {
			throw new RuntimeException("Nominal Voucher Kosong");
		}
	}

	public List<Voucher> getAll() {
		return voucherDao.getAll();
	}
	
	public Voucher getRefById(final String id) {
		return voucherDao.getRefById(id);
	}

	
	
	public Voucher getByIdAndDetach(final String id) {
		return voucherDao.getByIdAndDetach(Voucher.class, id);
	}

	public Voucher insert(final Voucher data) {
		Voucher voucherInsert = null;
		try {
			ConnHandler.begin();
			valIdNull(data);
			valNotNullable(data);
			data.setCreatedAt(LocalDateTime.now());
			data.setIsActive(true);
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

		final Optional<Voucher> res = voucherDao.getById(data.getId());
		if (res.isEmpty()) {
			throw new RuntimeException("Voucher tidak ditemukan");
		}
		final Voucher voucher = res.get();
		try {
			ConnHandler.getManager().detach(voucherUpdate);
			ConnHandler.begin();
			voucher.setAmount(data.getAmount());
			voucher.setVoucherCode(data.getVoucherCode());
			voucher.setExpiredDate(data.getExpiredDate());
			voucher.setUpdatedAt(LocalDateTime.now());
			voucherUpdate = voucherDao.update(voucher);
			ConnHandler.commit();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return voucherUpdate;
	}

	public boolean deleteById(final String id) {
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

	public List<PojoVoucherGetAllRes> getAllRes() {
		final List<PojoVoucherGetAllRes> pojos = new ArrayList<>();
		final List<Voucher> res = getAll();
		for (int i = 0; i < res.size(); i++) {
			final PojoVoucherGetAllRes pojo = new PojoVoucherGetAllRes();
			final Voucher vcr = res.get(i);
			ConnHandler.getManager().detach(vcr);
			pojo.setId(vcr.getId());
			pojo.setVoucherCode(vcr.getVoucherCode());
			pojo.setAmount(vcr.getAmount());
			pojo.setExpiredDate(vcr.getExpiredDate());
			pojos.add(pojo);
		}
		return pojos;
	}
	
	public Optional<Voucher> getByVoucherCode(final String vocherCode) {	
		return voucherDao.getByVoucherCode(vocherCode);
	}

	public PojoInsertRes insertRes(final PojoVoucherInsertReq data) {
		final PojoInsertRes res = new PojoInsertRes();
		final Voucher insert = new Voucher();
		insert.setVoucherCode(data.getVoucherCode());
		insert.setAmount(data.getAmount());
		insert.setExpiredDate(data.getExpiredDate());
		try {
			Voucher vcrInsert = null;
			vcrInsert = insert(insert);
			res.setMessage("Kode voucher : " + vcrInsert.getVoucherCode() + " berhasil dibuat");
			res.setId(vcrInsert.getId());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public PojoUpdateRes updateRes(final PojoVoucherUpdateReq data) {
		final PojoUpdateRes res = new PojoUpdateRes();
		final Voucher vcr = getByIdAndDetach(data.getId());
		vcr.setVoucherCode(data.getVoucherCode());
		vcr.setAmount(data.getAmount());
		vcr.setExpiredDate(data.getExpiredDate());
		try {
			Voucher update = null;
			ConnHandler.begin();
			update = update(vcr);
			ConnHandler.commit();
			res.setVer(update.getVersion());
			res.setMessage("Voucher berhasil di update");
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public PojoDeleteRes deleteRes(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deleteById(id);
		res.setMessage("Berhasil menghapus");
		return res;
	}

}
