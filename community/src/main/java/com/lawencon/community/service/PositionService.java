package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.model.Position;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.position.PojoPositionGetAllRes;
import com.lawencon.community.pojo.position.PojoPositionInsertReq;
import com.lawencon.community.pojo.position.PojoPositionUpdateReq;
import com.lawencon.security.principal.PrincipalService;

@Service
public class PositionService {
	
	private final PositionDao positionDao;
	private final PrincipalService principalService;
	
	public PositionService(PositionDao positionDao, PrincipalService principalService) {
		this.positionDao = positionDao;
		this.principalService = principalService;
	}
	
	public Position insert(final Position data) {
		Position positionInsert = null;
		try {
			ConnHandler.begin();
			positionInsert = positionDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return positionInsert;
	}
	
	public Position update(final Position data) {
		Position positionUpdate = null;

		try {
			ConnHandler.begin();
			positionUpdate = positionDao.update(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return positionUpdate;
	}
	
	public Optional<Position> getById(final String id) {
		return positionDao.getById(id);
	}
	
	public Position getRefById(final String id) {
		return positionDao.getRefById(id);
	}

	public List<Position> getAll() {
		return positionDao.getAll();
	}
	
	public boolean deleteById(final String id) {
		boolean positionDelete = false;

		try {
			ConnHandler.begin();
			positionDelete = positionDao.deleteById(Position.class, id);
			ConnHandler.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return positionDelete;
	}
	
	public Position getByIdAndDetach(final String id) {
		return positionDao.getByIdAndDetach(Position.class, id);
	}

	public PojoInsertRes insert(final PojoPositionInsertReq data) {
		final Position position = new Position();

		position.setPositionName(data.getPositionName());
		
		final Position positionInsert = insert(position);
		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(positionInsert.getId());
		pojoInsertRes.setMessage("Success");
		return pojoInsertRes;
	}
	
	public PojoUpdateRes update (final PojoPositionUpdateReq data) {
		final Position position = getByIdAndDetach(data.getId());
		
		position.setPositionName(data.getPositionName());
		
		position.setVersion(data.getVer());
		
		final Position positionUpdate = update(position);
		final PojoUpdateRes pojoUpdate = new PojoUpdateRes();
		pojoUpdate.setVer(positionUpdate.getVersion());
		pojoUpdate.setMessage("Updated");
		return pojoUpdate;	
	}
	
	public List<PojoPositionGetAllRes> getAllRes(){
		final List<PojoPositionGetAllRes> pojos = new ArrayList<>();
		final List<Position> res = getAll();
		
		for(int i = 0; i < res.size(); i++) {
			final PojoPositionGetAllRes pojo = new PojoPositionGetAllRes();
			final Position position = res.get(i);
			
			ConnHandler.getManager().detach(position);
			pojo.setId(position.getId());
			pojo.setPositionName(res.get(i).getPositionName());
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
