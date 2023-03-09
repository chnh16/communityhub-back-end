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
		return Optional.ofNullable(super.getById(User.class, id));
	}

	@Override
	public Optional<User> getRefById(final Long id) {
		return Optional.ofNullable(super.getByIdRef(User.class, id));
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
			final String sql = " SELECT tu.id, tu.email, tu.password_user, tu.role_id, tr.role_code ,tu.profile_id, tu.created_by, tu.updated_by, tu.created_at, tu.updated_at, tu.ver, tu.is_active, p.full_name "
					+ "FROM t_user tu INNER JOIN t_role tr ON tu.role_id = tr.id "
					+ "INNER JOIN profile p ON tu.profile_id = p.id "
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
				user.setPasswordUser(objArr[2].toString());

				role.setId(objArr[3].toString());
				role.setRoleCode(objArr[4].toString());
				user.setRole(role);

				profile.setId(objArr[5].toString());
				profile.setFullName(objArr[12].toString());
				user.setProfile(profile);

				user.setCreatedBy(objArr[6].toString());
				if (objArr[7] != null) {
					user.setUpdatedBy(objArr[7].toString());
				}
				
				user.setCreatedAt(Timestamp.valueOf(objArr[8].toString()).toLocalDateTime());
				
				if (objArr[9] != null) {
					user.setUpdatedAt(Timestamp.valueOf(objArr[9].toString()).toLocalDateTime());
				}

				profile.setVersion(Integer.valueOf(objArr[10].toString()));
				profile.setIsActive(Boolean.valueOf(objArr[11].toString()));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Optional.ofNullable(user);
	}

	@Override
	public User getByIdAndDetach(final Long id) {
		return super.getByIdAndDetach(User.class, id);
	}

}
