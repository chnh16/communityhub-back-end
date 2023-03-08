package com.lawencon.community.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.dao.RoleDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;


@Service
public class UserService {
	
	private final UserDao userDao;
	private final FileDao fileDao;
	private final ProfileDao profileDao;
	private final RoleDao roleDao;
	private final PrincipalService principalService;
	

	public UserService(UserDao userDao, FileDao fileDao, RoleDao roleDao, ProfileDao profileDao, PrincipalService principalService
			) {
		this.userDao = userDao;
		this.fileDao = fileDao;
		this.profileDao = profileDao;
		this.principalService = principalService;
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
			ConnHandler.rollback();
		}
		return userInsert;
		
	}

	
	public User update(final User data) {
		User userUpdate = null;

		userUpdate = userDao.getById(Long.valueOf(data.getId())).get();
		ConnHandler.getManager().detach(userUpdate);
		userUpdate.setIsActive(data.getIsActive());
		userUpdate.setUpdatedAt(LocalDateTime.now());
		userUpdate.setUpdatedBy(principalService.getAuthPrincipal());
		userUpdate = userDao.update(data);
		return userUpdate;
	}

	
	public Optional<User> getById(final Long id) {
		return userDao.getById(id);
	}

	
	public List<User> getAll() {
		return userDao.getAll();
	}

	
	public boolean deleteById(final Long id) {
		boolean userDelete = false;

		try {
			ConnHandler.begin();
			userDelete = userDao.deleteById(User.class, id);
			ConnHandler.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			ConnHandler.rollback();
		}

		return userDelete;
	}
	
	public UserDetails loadByUsername(String email) throws UsernameNotFoundException{
		final Optional<User> user = userDao.getByEmail(email);
		if(user.isPresent()) {
			return new org.springframework.security.core.userdetails.User(email, user.get().getPasswordUser(), new ArrayList<>());
		}
		
		throw new UsernameNotFoundException("Email dna Password tidak ditemukan");
	}
	

}
