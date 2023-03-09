package com.lawencon.community.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;

@Repository
public abstract class BasePostDao<T> extends AbstractJpaDao {
	protected EntityManager em() {
		return ConnHandler.getManager();
	}

	protected String toStr(final StringBuilder str) {
		return str.toString();
	}
	
	abstract T insert(T data);

	abstract boolean delete(String id);
}
