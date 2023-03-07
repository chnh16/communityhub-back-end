package com.lawencon.community.dao.impl;

import javax.persistence.EntityManager;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;

public class BaseDaoImpl extends AbstractJpaDao{

	protected EntityManager em() {
		return ConnHandler.getManager();
	}

}
