package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;

@Repository
public abstract class MasterDao<T> extends AbstractJpaDao{

	protected EntityManager em() {
		return ConnHandler.getManager();
	}
	
	protected String toStr(final StringBuilder str) {
		return str.toString();
	}
	
	abstract Optional<T> getById(String id);
	
	abstract Optional<T> getRefById(String id);
	
	abstract T getByIdAndDetach(Long id);
	
	abstract List<T> getAll();
	
	abstract T update(T data);
	
	abstract T insert(T data);
	
	abstract boolean delete(Long id);

}
