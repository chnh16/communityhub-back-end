package com.lawencon.community.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.MembershipDao;
import com.lawencon.community.model.Membership;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.membership.PojoMembershipInsertReq;
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
	
	public Membership insert (final Membership data) {
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

		membershipUpdate = membershipDao.getById(Long.valueOf(data.getId())).get() ;
		ConnHandler.getManager().detach(membershipUpdate);
		membershipUpdate.setIsActive(data.getIsActive());
		membershipUpdate.setUpdatedAt(LocalDateTime.now());
		membershipUpdate.setUpdatedBy(principalService.getAuthPrincipal());
		membershipUpdate = membershipDao.update(data);

		return membershipUpdate;
	}
	
	public Optional<Membership> getById(final Long id) {
		return membershipDao.getById(id);
	}

	public List<Membership> getAll() {
		return membershipDao.getAll();
	}
	
	public boolean deleteById(final Long id) {
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
	
	
	public PojoInsertRes insert(final PojoMembershipInsertReq data) {
		final Membership membership = new Membership();
		final String generateId = Generate.generateCode(5);
		
		membership.setMembershipCode(generateId);
		membership.setMembershipName(data.getMembershipName());;
		
		final Membership membershipInsert = membershipDao.insert(membership);
		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(Long.valueOf(membershipInsert.getId()));
		pojoInsertRes.setMessage("Success");
		return pojoInsertRes;
	}

}
