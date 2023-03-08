package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;

public abstract class MasterDao<T> extends AbstractJpaDao{

	protected EntityManager em() {
		return ConnHandler.getManager();
	}
	
	abstract Optional<T> getById(Long id);
	
	abstract Optional<T> getRefById(Long id);
	
	abstract List<T> getAll();
	
	abstract T update(T data);
	
	abstract T insert(T data);
	
	abstract boolean delete(Long id);

}
