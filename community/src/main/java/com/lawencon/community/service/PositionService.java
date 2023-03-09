package com.lawencon.community.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.model.Position;
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

		positionUpdate = positionDao.getById(Long.valueOf(data.getId())).get();
		ConnHandler.getManager().detach(positionUpdate);
		positionUpdate.setIsActive(data.getIsActive());
		positionUpdate.setUpdatedAt(LocalDateTime.now());
		positionUpdate.setUpdatedBy(principalService.getAuthPrincipal());
		positionUpdate = positionDao.update(data);
		return positionUpdate;
	}
	
	public Optional<Position> getById(final Long id) {
		return positionDao.getById(id);
	}

	public List<Position> getAll() {
		return positionDao.getAll();
	}
	
	public boolean deleteById(final Long id) {
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

}
