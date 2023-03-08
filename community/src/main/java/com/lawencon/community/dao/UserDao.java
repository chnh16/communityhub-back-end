package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.Profile;
import com.lawencon.community.model.Role;
import com.lawencon.community.model.User;


@Repository
public class UserDao extends MasterDao<User>{

	@Override
	public Optional<User> getById(final Long id) {
		final User res = getById(User.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<User> getRefById(final Long id) {
		final User res = getByIdRef(User.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM user ").append(" WHERE is_active = true");
		final List<User> res = em().createNativeQuery(toStr(str), User.class).getResultList();
		return res;
	}

	@Override
	public User update(final User data) {
		final User res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(User.class, id);
	}

	@Override
	public User insert(final User data) {
		final User res = saveAndFlush(data);
		return res;
	}
	
	public Optional<User> getByEmail(final String email) {
		User user = null;

		try {
			final String sql = " SELECT tu.id, tu.email, tu.password_user, tu.role_id, tr.role_code ,tu.profile_id, tu.created_by, tu.updated_by, tu.created_at, tu.updated_at, tu.ver, tu.is_active "
					+ "FROM t_user tu INNER JOIN t_role tr ON tu.role_id = tr.id "
					+ "WHERE tu.email = :email AND tu.is_active = TRUE ";

			final Object result = em().createNativeQuery(sql).setParameter("email", email)
					.getSingleResult();

			if (result != null) {

				final Role role = new Role();
				final Profile profile = new Profile();

				user = new User();
				final Object[] objArr = (Object[]) result;

				user.setId(objArr[0].toString());

				user.setEmail(objArr[1].toString());

				role.setId(objArr[2].toString());
				role.setRoleCode(objArr[3].toString());
				user.setRole(role);

				profile.setId(objArr[4].toString());
				user.setProfile(profile);

				user.setCreatedBy(objArr[5].toString());
				if (objArr[6] != null) {
					user.setUpdatedBy(objArr[6].toString());
				}
				
				user.setCreatedAt(Timestamp.valueOf(objArr[7].toString()).toLocalDateTime());
				
				if (objArr[8] != null) {
					user.setUpdatedAt(Timestamp.valueOf(objArr[8].toString()).toLocalDateTime());
				}

				profile.setVersion(Integer.valueOf(objArr[9].toString()));
				profile.setIsActive(Boolean.valueOf(objArr[10].toString()));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Optional.ofNullable(user);
	}

}
