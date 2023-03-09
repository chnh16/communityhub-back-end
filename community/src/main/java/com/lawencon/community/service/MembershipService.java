package com.lawencon.community.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.MembershipDao;
import com.lawencon.community.model.Membership;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.membership.PojoMembershipGetAllRes;
import com.lawencon.community.pojo.membership.PojoMembershipInsertReq;
import com.lawencon.community.pojo.membership.PojoMembershipUpdateReq;
import com.lawencon.community.util.Generate;
import com.lawencon.security.principal.PrincipalService;

@Service
public class MembershipService {

	private final MembershipDao membershipDao;
	private final PrincipalService principalService;

	public MembershipService(final MembershipDao membershipDao, final PrincipalService principalService) {
		this.membershipDao = membershipDao;
		this.principalService = principalService;
	}

	public Membership insert(final Membership data) {
		Membership membershipInsert = null;

		try {
			ConnHandler.begin();
			membershipInsert = membershipDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return membershipInsert;
	}

	public Membership update(final Membership data) {
		Membership membershipUpdate = null;

		membershipUpdate = membershipDao.getById(data.getId()).get();
		ConnHandler.getManager().detach(membershipUpdate);
		membershipUpdate.setIsActive(data.getIsActive());
		membershipUpdate.setUpdatedAt(LocalDateTime.now());
		membershipUpdate = membershipDao.update(data);

		return membershipUpdate;
	}

	public Optional<Membership> getById(final String id) {
		return membershipDao.getById(id);
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

		membership.setMembershipCode(generateId);
		membership.setMembershipName(data.getMembershipName());
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
		final Membership membership = getByIdAndDetach(data.getId());

		membership.setMembershipName(data.getMembershipName());

		membership.setDuration(data.getDuration());

		membership.setAmount(data.getAmount());

		membership.setVersion(data.getVer());

		final Membership membershipUpdate = update(membership);
		final PojoUpdateRes pojoUpdate = new PojoUpdateRes();
		pojoUpdate.setVer(membershipUpdate.getVersion());
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

}
