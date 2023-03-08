package com.lawencon.community.dao;

import javax.persistence.EntityManager;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;

public abstract class BasePostDao<T> extends AbstractJpaDao {
	protected EntityManager em() {
		return ConnHandler.getManager();
	}

	abstract T insert(T data);

	abstract boolean delete(Long id);

	
}
