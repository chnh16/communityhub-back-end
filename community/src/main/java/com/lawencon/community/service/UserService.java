package com.lawencon.community.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.RoleList;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.dao.RoleDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Industry;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.user.PojoUserRegisterReq;

@Service
public class UserService implements UserDetailsService {

	private final UserDao userDao;
	private final FileDao fileDao;
	private final ProfileDao profileDao;
	private final RoleDao roleDao;
	private final PositionService positionService;
	private final IndustryService industryService;

	public UserService(UserDao userDao, FileDao fileDao, RoleDao roleDao, ProfileDao profileDao, PositionService positionService, IndustryService industryService) {
		this.userDao = userDao;
		this.fileDao = fileDao;
		this.profileDao = profileDao;
//		this.encoder = encoder;
		this.roleDao = roleDao;
		this.positionService = positionService;
		this.industryService = industryService;

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
	
	public User saveNoLogin(final User data) {
		final Supplier<String> idFunc = () -> RoleList.SYSTEM.getRoleName();
		return null;
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
	
	public User getByRefId(final String id) {
		return userDao.getByIdRef(User.class, id);
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
	
	public PojoInsertRes register(final PojoUserRegisterReq data) {
		final PojoInsertRes pojo = new PojoInsertRes();
		File fileInsert = null;
		User userInsert = null;
		Profile profileInsert = null;
		final User user = new User();
		final Profile profile = new Profile();
		final Position position = positionService.getRefById(data.getProfile().getPositionId());
		final Industry industry = industryService.getRefById(data.getProfile().getIndustryId());
		profile.setFullName(data.getProfile().getFullName());
		profile.setCountry(data.getProfile().getCountry());
		profile.setProvince(data.getProfile().getProvince());
		profile.setCity(data.getProfile().getCity());
		profile.setBalance(BigDecimal.ZERO);
		profile.setCompany(data.getProfile().getCompany());
		profile.setPosition(position);
		profile.setIndustry(industry);
		profile.setPostalCode(data.getProfile().getPostalCode());
		profile.setNoHandphone(data.getProfile().getPhoneNumber());
		if(data.getProfile().getFile() != null) {
			final File file = new File();
			file.setFileName(data.getProfile().getFile().getFileName());
			file.setFileContent(data.getProfile().getFile().getFileContent());
			file.setFileExtension(data.getProfile().getFile().getFileExtension());
			file.setCreatedAt(LocalDateTime.now());
			fileInsert = fileDao.insert(file);
		}
		profile.setFile(fileInsert);
		profileInsert = profileDao.insert(profile);
		user.setEmail(data.getEmail());
		user.setPasswordUser(data.getPassword());
		user.setProfile(profileInsert);
		userInsert = insert(user);
		pojo.setId(userInsert.getId());
		pojo.setMessage("Registrasi Berhasil");
		return pojo;
	}

}
