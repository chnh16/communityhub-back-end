package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.model.Position;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.position.PojoPosistionGetAllResData;
import com.lawencon.community.pojo.position.PojoPositionGetAllRes;
import com.lawencon.community.pojo.position.PojoPositionInsertReq;
import com.lawencon.community.pojo.position.PojoPositionUpdateReq;

@Service
public class PositionService extends ValidationService<Position> {

	@Autowired
	private final PositionDao positionDao;

	public PositionService(PositionDao positionDao) {
		this.positionDao = positionDao;
	}

	public Position insert(final Position data) {
		Position positionInsert = null;
		
		try {
			ConnHandler.begin();
			
			
			valMaxLength(data);
			valNotNullable(data);
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
			valIdNull(data);
			valMaxLength(data);
			valNotNullable(data);
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

	public PojoUpdateRes update(final PojoPositionUpdateReq data) {
		final Position position = getByIdAndDetach(data.getId());

		position.setPositionName(data.getPositionName());

		position.setVersion(data.getVer());

		final Position positionUpdate = update(position);
		final PojoUpdateRes pojoUpdate = new PojoUpdateRes();
		pojoUpdate.setVer(positionUpdate.getVersion());
		pojoUpdate.setMessage("Updated");
		return pojoUpdate;
	}

	public List<PojoPositionGetAllRes> getAllRes() {
		final List<PojoPositionGetAllRes> pojos = new ArrayList<>();
		final List<Position> res = getAll();

		for (int i = 0; i < res.size(); i++) {
			final PojoPositionGetAllRes pojo = new PojoPositionGetAllRes();
			final Position position = res.get(i);

			ConnHandler.getManager().detach(position);
			pojo.setId(position.getId());
			pojo.setPositionName(res.get(i).getPositionName());
			pojo.setVer(position.getVersion());
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

	public PojoPositionGetAllRes getPositionById(final String id) {
		final Optional<Position> position = positionDao.getPositionById(id);

		final PojoPositionGetAllRes pojoPosition = new PojoPositionGetAllRes();
		pojoPosition.setId(position.get().getId());
		pojoPosition.setPositionName(position.get().getPositionName());
		pojoPosition.setVer(position.get().getVersion());
		return pojoPosition;
	}

	public PojoPosistionGetAllResData getPositioAllResData(final Integer limit, final Integer offset) {
		final List<PojoPositionGetAllRes> pojos = new ArrayList<>();
		final List<Position> res = positionDao.getPosition(limit, offset);

		for (int i = 0; i < res.size(); i++) {
			final PojoPositionGetAllRes pojo = new PojoPositionGetAllRes();
			final Position position = res.get(i);

			ConnHandler.getManager().detach(position);
			pojo.setId(position.getId());
			pojo.setPositionName(res.get(i).getPositionName());
			pojo.setVer(position.getVersion());
			pojos.add(pojo);
		}

		final PojoPosistionGetAllResData pojoPositionData = new PojoPosistionGetAllResData();
		pojoPositionData.setData(pojos);
		pojoPositionData.setTotal(positionDao.getTotalPosition());

		return pojoPositionData;
	}

	@Override
	void valIdNull(Position data) {
		if(data.getId() == null) {
			throw new RuntimeException("ID kosong");
		}

	}

	@Override
	void valIdNotNull(Position data) {
		if(data.getId() != null) {
			throw new RuntimeException("ID harus kosong");
		}
	}

	@Override
	void valBkNull(Position data) {
		
	}

	@Override
	void valFkNull(Position data) {
		
		
	}

	@Override
	void valMaxLength(Position data) {
		if(data.getPositionName().length() > 30) {
			throw new RuntimeException("Input terlalu panjang");
		}
	}


	@Override
	void valIdPresent(Position data) {
		final Optional<Position> res = getById(data.getId());
		if(res.isPresent()) {
			throw new RuntimeException("ID sudah ada di database");
		}
		
	}

	@Override
	void valNotNullable(Position data) {
		if(data.getPositionName() == null) {
			throw new RuntimeException("Tidak boleh kosong");
		}
	}
}
