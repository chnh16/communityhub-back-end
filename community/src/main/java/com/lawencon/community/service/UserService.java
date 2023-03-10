package com.lawencon.community.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.dao.RoleDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.User;

@Service
public class UserService implements UserDetailsService {

	private final UserDao userDao;
	private final FileDao fileDao;
	private final ProfileDao profileDao;
	private final RoleDao roleDao;

	public UserService(UserDao userDao, FileDao fileDao, RoleDao roleDao, ProfileDao profileDao) {
		this.userDao = userDao;
		this.fileDao = fileDao;
		this.profileDao = profileDao;
//		this.encoder = encoder;
		this.roleDao = roleDao;

	}

	public User insert(final User data) {
		User userInsert = null;
		try {
			ConnHandler.begin();
			userInsert = userDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInsert;

	}

	public User update(final User data) {
		User userUpdate = null;

		final Optional<User> res = userDao.getById(data.getId());

		final User user = res.get();
		try {
			ConnHandler.getManager().detach(userUpdate);
			user.setUpdatedAt(LocalDateTime.now());
			userUpdate = userDao.update(user);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return userUpdate;
	}

	public Optional<User> getById(final String id) {
		return userDao.getById(id);
	}

	public List<User> getAll() {
		return userDao.getAll();
	}

	public boolean deleteById(final String id) {
		boolean userDelete = false;

		try {
			ConnHandler.begin();
			userDelete = userDao.deleteById(User.class, id);
			ConnHandler.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userDelete;
	}

	public Optional<User> getByEmail(final String email) {
		return userDao.getByEmail(email);
	}
	
	public User getByIdAndDetach(final String id) {
		return userDao.getByIdAndDetach(id);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		final Optional<User> user = userDao.getByEmail(email);
		if (user.isPresent()) {
			return new org.springframework.security.core.userdetails.User(email, user.get().getPasswordUser(),
					new ArrayList<>());
		}

		throw new UsernameNotFoundException("Email dan Password tidak ditemukan");
	}

}
