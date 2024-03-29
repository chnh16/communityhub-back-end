package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.TypeProductEnum;
import com.lawencon.community.dao.MembershipDao;
import com.lawencon.community.dao.TypeProductDao;
import com.lawencon.community.model.Membership;
import com.lawencon.community.model.TypeProduct;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.membership.PojoMembershipGetAllRes;
import com.lawencon.community.pojo.membership.PojoMembershipGetAllResData;
import com.lawencon.community.pojo.membership.PojoMembershipInsertReq;
import com.lawencon.community.pojo.membership.PojoMembershipUpdateReq;
import com.lawencon.community.util.Generate;

@Service
public class MembershipService extends ValidationService<Membership>{
	
	@Autowired
	private MembershipDao membershipDao;
	
	@Autowired
	private TypeProductDao typeProductDao;
	

	public Membership insert(final Membership data) {
		Membership membershipInsert = null;

		try {
			ConnHandler.begin();
			valNotNullable(data);
			valMaxLength(data);
			membershipInsert = membershipDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return membershipInsert;
	}

	public Membership update(final Membership data) {
		Membership membershipUpdate = null;

		try {
			ConnHandler.begin();
			valNotNullable(data);
			valMaxLength(data);
			
			membershipUpdate = membershipDao.update(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return membershipUpdate;
	}

	public Optional<Membership> getById(final String id) {
		return membershipDao.getById(id);
	}
	
	public Membership getRefById(final String id) {
		return membershipDao.getRefById(id);
	}

	public List<Membership> getAll() {
		return membershipDao.getAll();
	}

	public boolean deleteById(final String id) {
		boolean membershipDelete = false;

		try {
			ConnHandler.begin();
			membershipDelete = membershipDao.deleteById(Membership.class, id);
			ConnHandler.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return membershipDelete;

	}

	public Membership getByIdAndDetach(final String id) {
		return membershipDao.getByIdAndDetach(Membership.class, id);
	}

	public PojoInsertRes insert(final PojoMembershipInsertReq data) {
		final Membership membership = new Membership();
		final String generateId = Generate.generateCode(5);
		final TypeProduct typeProduct = typeProductDao.getTypeByCode(TypeProductEnum.MEMBERSHIP.getTypeCode()).get();
		membership.setMembershipCode(generateId);
		membership.setMembershipName(data.getMembershipName());
		membership.setTypeProduct(typeProduct);
		membership.setDuration(data.getDuration());
		membership.setAmount(data.getAmount());

		Membership membershipInsert = null;
		membershipInsert = insert(membership);

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(membershipInsert.getId());
		pojoInsertRes.setMessage("Success");
		return pojoInsertRes;
	}

	public PojoUpdateRes update(final PojoMembershipUpdateReq data) {
		Membership membershipIpdate = null;
	
		membershipIpdate = getByIdAndDetach(data.getId());
		
		final Membership membership = membershipIpdate;

		membership.setMembershipName(data.getMembershipName());

		membership.setDuration(data.getDuration());

		membership.setAmount(data.getAmount());

		membership.setVersion(data.getVer());
		
		membershipIpdate = update(membership);

		final PojoUpdateRes pojoUpdate = new PojoUpdateRes();
		pojoUpdate.setVer(data.getVer());
		pojoUpdate.setMessage("Updated");
		return pojoUpdate;

	}

	public List<PojoMembershipGetAllRes> getAllRes() {
		final List<PojoMembershipGetAllRes> pojos = new ArrayList<>();
		final List<Membership> res = getAll();

		for (int i = 0; i < res.size(); i++) {
			final PojoMembershipGetAllRes pojo = new PojoMembershipGetAllRes();
			final Membership membership = res.get(i);

			ConnHandler.getManager().detach(membership);
			pojo.setId(membership.getId());
			pojo.setMembershipCode(membership.getMembershipCode());
			pojo.setMembershipName(membership.getMembershipName());
			pojo.setDuration(membership.getDuration());
			pojo.setAmount(membership.getAmount());
			pojo.setVer(membership.getVersion());
			pojos.add(pojo);
		}
		return pojos;
	}

	public PojoDeleteRes delete(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deleteById(id);
		res.setMessage("Berhasil Dihapus");
		return res;
	}
	
	public PojoMembershipGetAllRes getMembershipById(final String id) {
		final Optional<Membership> membership = membershipDao.getMembershipById(id);
		
		final PojoMembershipGetAllRes pojoMembership = new PojoMembershipGetAllRes();
		pojoMembership.setId(membership.get().getId());
		pojoMembership.setMembershipCode(membership.get().getMembershipCode());
		pojoMembership.setMembershipName(membership.get().getMembershipName());
		pojoMembership.setDuration(membership.get().getDuration());
		pojoMembership.setAmount(membership.get().getAmount());
		pojoMembership.setVer(membership.get().getVersion());
		
		return pojoMembership;
	}
	
	public PojoMembershipGetAllResData getMembership(final Integer limit, final Integer offset) {
		final List<PojoMembershipGetAllRes> pojos = new ArrayList<>();
		final List<Membership> res = membershipDao.getMembership(limit, offset);

		for (int i = 0; i < res.size(); i++) {
			final PojoMembershipGetAllRes pojo = new PojoMembershipGetAllRes();
			final Membership membership = res.get(i);

			ConnHandler.getManager().detach(membership);
			pojo.setId(membership.getId());
			pojo.setMembershipCode(membership.getMembershipCode());
			pojo.setMembershipName(membership.getMembershipName());
			pojo.setDuration(membership.getDuration());
			pojo.setAmount(membership.getAmount());
			pojo.setVer(membership.getVersion());
			pojos.add(pojo);
		}
		
		final PojoMembershipGetAllResData pojoMembershipData = new PojoMembershipGetAllResData();
		pojoMembershipData.setData(pojos);
		pojoMembershipData.setTotal(membershipDao.getTotalMembership());
		return pojoMembershipData;
	}

	@Override
	void valNotNullable(Membership data) {
		if (data.getMembershipCode().length() == 0) {
			throw new RuntimeException("Kode Membership Kosong");
		}
		if (data.getMembershipName().length() == 0) {
			throw new RuntimeException("Nama Membership Kosong");
		}
		
		if (data.getAmount() == null) {
			throw new RuntimeException("Amount Kosong");
		}
		
		if (data.getDuration() == null) {
			throw new RuntimeException("Duration Kosong");
		}
		
	}

	@Override
	void valIdNull(Membership data) {
		if (data.getId() == null) {
			throw new RuntimeException("ID kosong");
		}
		
	}

	@Override
	void valIdNotNull(Membership data) {
		if (data.getId() != null) {
			throw new RuntimeException("ID harus kosong");
		}
		
	}

	@Override
	void valBkNull(Membership data) {
			
	}

	@Override
	void valFkNull(Membership data) {
		
	}

	@Override
	void valMaxLength(Membership data) {
		if(data.getMembershipName().length() > 30) {
			throw new RuntimeException("Nama Membership terlalu panjang");
		}
		
	}

	@Override
	void valIdPresent(Membership data) {
		final Optional<Membership> res = getById(data.getId());
		if(res.isPresent()) {
			throw new RuntimeException("ID sudah ada di database");
		}
	}
}
