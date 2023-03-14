package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.Role;
import com.lawencon.community.model.User;

@Repository
public class RoleDao extends MasterDao<Role> {

	@Override
	public Optional<Role> getById(final String id) {
		final Role res = getById(Role.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Role getRefById(final String id) {
		final Role res = getById(Role.class, id);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM role ").append(" WHERE is_active = true");
		final List<Role> res = em().createNativeQuery(toStr(str), Role.class).getResultList();
		return res;
	}

	@Override
	public Role update(final Role data) {
		final Role res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(Role.class, id);
	}

	@Override
	public Role insert(final Role data) {
		final Role res = saveAndFlush(data);
		return res;
	}

	@Override
	public Role getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(Role.class, id);
	}

	public Optional<Role> getByRoleCode(final String roleCode) {
		final StringBuilder str = new StringBuilder();
		Role role = null;
		try {
			str.append("SELECT r.id, r.created_by, r.updated_by, r.created_at, r.updated_at, r.ver, r.is_active FROM t_role r")
				.append(" WHERE r.role_code = :roleCode")
				.append(" AND r.is_active = TRUE");
			final Object result = em().createNativeQuery(toStr(str)).setParameter("roleCode", roleCode).getSingleResult();
			if (result != null) {

				role = new Role();
				final Object[] objArr = (Object[]) result;

				role.setId(objArr[0].toString());

				role.setCreatedBy(objArr[1].toString());
				if (objArr[2] != null) {
					role.setUpdatedBy(objArr[2].toString());
				}

				role.setCreatedAt(Timestamp.valueOf(objArr[3].toString()).toLocalDateTime());

				if (objArr[4] != null) {
					role.setUpdatedAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				}

				role.setVersion(Integer.valueOf(objArr[5].toString()));
				role.setIsActive(Boolean.valueOf(objArr[6].toString()));
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(role);
	}

}
