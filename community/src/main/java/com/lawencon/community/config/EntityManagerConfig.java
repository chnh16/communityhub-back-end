package com.lawencon.community.config;

import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;

public class EntityManagerConfig {
	
	private static final ThreadLocal<EntityManager> THREAD_LOCAL_EM = new ThreadLocal<>();

	public static EntityManager getEm(final SessionFactory factory) {
		if (THREAD_LOCAL_EM.get() != null) {
			return THREAD_LOCAL_EM.get();
		} else {
			final EntityManager em = factory.createEntityManager();
			THREAD_LOCAL_EM.set(em);
			return em;
		}
	}

}
