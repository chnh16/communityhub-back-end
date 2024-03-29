package com.lawencon.community.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.RoleList;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.dao.RegisterVerificationDao;
import com.lawencon.community.dao.RoleDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.EmailDetails;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Industry;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.RegisterVerification;
import com.lawencon.community.model.Role;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.user.PojoProfileUpdateReq;
import com.lawencon.community.pojo.user.PojoUserChangePasswordReq;
import com.lawencon.community.pojo.user.PojoUserGetUserProfileRes;
import com.lawencon.community.pojo.user.PojoUserRegisterReq;
import com.lawencon.community.pojo.user.PojoVerificationCodeUpdateReq;
import com.lawencon.community.pojo.user.PojoVerificationReq;
import com.lawencon.community.util.Generate;
import com.lawencon.security.principal.PrincipalService;

@Service
public class UserService implements UserDetailsService, Runnable {

	@Autowired
	private UserDao userDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private ProfileDao profileDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private RegisterVerificationDao registerVerificationDao;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private PositionService positionService;

	@Autowired
	private IndustryService industryService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PrincipalService principalService;
	private EmailDetails emailDetails;

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

	public User getByRefId(final String id) {
		return userDao.getByIdRef(User.class, id);
	}

	public Optional<User> getUserSystem(final String roleCode) {
		return userDao.getUserSystem(roleCode);
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
		Profile profileInsert = null;

		final User getUserSystem = userDao.getUserSystem(RoleList.SYSTEM.getRoleCode()).get();
		final Role getRole = roleDao.getByRoleCode(RoleList.MEMBER.getRoleCode()).get();

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

		if (data.getProfile().getFile() != null) {
			final File file = new File();
			file.setFileName(data.getProfile().getFile().getFileName());
			file.setFileContent(data.getProfile().getFile().getFileContent());
			file.setFileExtension(data.getProfile().getFile().getFileExtension());
			file.setCreatedAt(LocalDateTime.now());
			ConnHandler.begin();
			fileInsert = fileDao.saveNoLogin(file, () -> getUserSystem.getId());
			ConnHandler.commit();

		}
		profile.setFile(fileInsert);

		ConnHandler.begin();
		profileInsert = profileDao.saveNoLogin(profile, () -> getUserSystem.getId());
		ConnHandler.commit();

		user.setEmail(data.getEmail());
		user.setPasswordUser(encoder.encode(data.getPasswordUser()));
		user.setRole(getRole);
		user.setIsVerified(false);
		user.setProfile(profileInsert);

		ConnHandler.begin();
		final User userInsert = userDao.saveNoLogin(user, () -> getUserSystem.getId());
		ConnHandler.commit();

		final RegisterVerification registerVerification = new RegisterVerification();
		final String generatePass = Generate.generateCode(8);
		registerVerification.setEmail(data.getEmail());
		registerVerification.setCodeVerifcation(generatePass);
		registerVerification.setExpired(LocalDateTime.now().plusMinutes(5));

		ConnHandler.begin();
		registerVerificationDao.saveNoLogin(registerVerification, () -> getUserSystem.getId());
		ConnHandler.commit();

		try {
			emailService.sendMail(registerVerification, userInsert);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pojo.setId(userInsert.getId());
		pojo.setMessage("Registrasi Berhasil");
		return pojo;
	}

	public PojoUpdateRes verification(final PojoVerificationReq data) {
		final User getUserSystem = userDao.getUserSystem(RoleList.SYSTEM.getRoleCode()).get();
		PojoUpdateRes pojoUpdate = null;
		final Optional<RegisterVerification> verification = registerVerificationDao.getVerification(data.getEmail(),
				data.getCodeVerification());
		if (verification.isEmpty()) {
			throw new RuntimeException("Kode verifikasi anda tidak cocok");
		}

		pojoUpdate = new PojoUpdateRes();
		final Optional<User> user = userDao.getIdByEmail(data.getEmail());
		User userVerification = getByIdAndDetach(user.get().getId());
		if (userVerification.getIsVerified()) {
			throw new RuntimeException("Sudah pernah melakukan verifikasi");
		}

		User userUpdate = null;

		ConnHandler.begin();
		userVerification.setIsVerified(true);
		userUpdate = userDao.saveNoLogin(userVerification, () -> getUserSystem.getId());
		ConnHandler.commit();
		pojoUpdate.setVer(userUpdate.getVersion());
		pojoUpdate.setMessage("Berhasil melakukan verifikasi");

		return pojoUpdate;
	}

	public PojoUpdateRes generateNewCode(final PojoVerificationCodeUpdateReq email) {
		final User getUserSystem = userDao.getUserSystem(RoleList.SYSTEM.getRoleCode()).get();
		final PojoUpdateRes pojo = new PojoUpdateRes();
		final Optional<RegisterVerification> verification = registerVerificationDao.getIdByEmail(email.getEmail());
		final Optional<User> userId = userDao.getIdByEmail(email.getEmail());
		final RegisterVerification registerVerif = registerVerificationDao.getByIdAndDetach(RegisterVerification.class, verification.get().getId());
		final User user = getByRefId(userId.get().getId());
		final String generatePass = Generate.generateCode(8);

		registerVerif.setEmail(email.getEmail());
		registerVerif.setCodeVerifcation(generatePass);
		registerVerif.setExpired(LocalDateTime.now().plusMinutes(5));

		RegisterVerification verificationInsert = null;
		ConnHandler.begin();
		verificationInsert = registerVerificationDao.saveNoLogin(registerVerif, () -> getUserSystem.getId());
		ConnHandler.commit();

		try {
			emailService.sendMail(registerVerif, user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		pojo.setVer(verificationInsert.getVersion());
		pojo.setMessage("Kode baru berhasil dikirim ke email anda");
		return pojo;
	}

	public PojoInsertRes createAdmin(final PojoUserRegisterReq data) {
		final PojoInsertRes pojo = new PojoInsertRes();
		File fileInsert = null;
		Profile profileInsert = null;

		final Role getRole = roleDao.getByRoleCode(RoleList.ADMIN.getRoleCode()).get();

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

		if (data.getProfile().getFile() != null) {
			final File file = new File();
			file.setFileName(data.getProfile().getFile().getFileName());
			file.setFileContent(data.getProfile().getFile().getFileContent());
			file.setFileExtension(data.getProfile().getFile().getFileExtension());
			file.setCreatedAt(LocalDateTime.now());
			ConnHandler.begin();
			fileInsert = fileDao.insert(file);
			ConnHandler.commit();

		}
		profile.setFile(fileInsert);
		ConnHandler.begin();
		profileInsert = profileDao.insert(profile);

		user.setEmail(data.getEmail());
		user.setPasswordUser(encoder.encode(data.getPasswordUser()));
		user.setRole(getRole);
		user.setIsVerified(true);
		user.setProfile(profileInsert);

		final User userInsert = userDao.insert(user);

		ConnHandler.commit();

		emailDetails.setMsgBody("Halo " + data.getProfile().getFullName() + "\nAkun anda " + " Email : "
				+ data.getEmail() + "\nPassword : " + data.getPasswordUser() + "\n TerimaKasih");
		emailDetails.setRecipient(userInsert.getEmail());
		emailDetails.setSubject("Berhasil ");

		final Runnable runnable = () -> {
			emailService.sendSimpleMail(emailDetails);
		};

		final Thread thread = new Thread(runnable);
		thread.start();

		pojo.setId(userInsert.getId());
		pojo.setMessage("Create Akun Admin Berhasil");
		return pojo;
	}

	public PojoUserGetUserProfileRes getUserProfile() {
		final User user = getByRefId(principalService.getAuthPrincipal());
		final PojoUserGetUserProfileRes pojo = new PojoUserGetUserProfileRes();

		pojo.setId(user.getProfile().getId());
		pojo.setEmail(user.getEmail());
		pojo.setFullName(user.getProfile().getFullName());
		pojo.setCountry(user.getProfile().getCountry());
		pojo.setProvince(user.getProfile().getProvince());
		pojo.setCity(user.getProfile().getCity());
		pojo.setPhoneNumber(user.getProfile().getNoHandphone());
		pojo.setPostalCode(user.getProfile().getPostalCode());
		pojo.setCompany(user.getProfile().getCompany());
		pojo.setPremiumUntil(user.getProfile().getPremiumUntil());
		pojo.setPositionId(user.getProfile().getPosition().getPositionName());
		pojo.setIndustryId(user.getProfile().getIndustry().getIndustryName());
		pojo.setBalance(user.getProfile().getBalance());
		if(user.getProfile().getFile() != null) {			
			pojo.setFile(user.getProfile().getFile().getId());
		}

		return pojo;
	}

	public PojoUpdateRes updateProfile(final PojoProfileUpdateReq data) {
		Profile profileUpdate = null;

		User user = getByRefId(principalService.getAuthPrincipal());

		profileUpdate = profileDao.getByIdAndDetach(user.getProfile().getId());
		final Profile profile = profileUpdate;

		final Position position = positionService.getRefById(data.getPositionId());
		final Industry industry = industryService.getRefById(data.getIndustryId());

		profile.setFullName(data.getFullName());
		profile.setCountry(data.getCountry());
		profile.setProvince(data.getProvince());
		profile.setCity(data.getCity());
		profile.setNoHandphone(data.getPhoneNumber());
		profile.setPostalCode(data.getPostalCode());
		profile.setCompany(data.getCompany());
		position.setId(data.getPositionId());
		profile.setPosition(position);
		industry.setId(data.getIndustryId());
		profile.setIndustry(industry);
		if(data.getFile() != null) {			
			final File fileUpdate = new File();
			fileUpdate.setFileName(data.getFile().getFileName());
			fileUpdate.setFileExtension(data.getFile().getFileExtension());
			fileUpdate.setFileContent(data.getFile().getFileContent());
			
			ConnHandler.begin();
			final File file = fileDao.update(fileUpdate);
			ConnHandler.commit();
			profile.setFile(file);
		}

		ConnHandler.begin();
		final Profile userProfileUpdate = profileDao.update(profile);
		ConnHandler.commit();

		final PojoUpdateRes pojoUpdateReq = new PojoUpdateRes();
		pojoUpdateReq.setVer(userProfileUpdate.getVersion());
		pojoUpdateReq.setMessage("Succes");
		return pojoUpdateReq;
	}

	public PojoUpdateRes changePass(final PojoUserChangePasswordReq data) {
		User userUpdate = null;
		userUpdate = getByIdAndDetach(principalService.getAuthPrincipal());
		User user = userUpdate;

		if (!encoder.matches(data.getPasswordUser(), user.getPasswordUser())) {
			throw new RuntimeException("Password Lama Anda Salah");
		}

		if (!(data.getNewPassword().equals(data.getConfirmPassword()))) {
			throw new RuntimeException("Confirm Password Anda Salah");
		}

		user.setPasswordUser(encoder.encode(data.getConfirmPassword()));

		ConnHandler.begin();
		userDao.update(user);
		ConnHandler.commit();

		final PojoUpdateRes pojoUpdateReq = new PojoUpdateRes();
		pojoUpdateReq.setVer(user.getVersion());
		pojoUpdateReq.setMessage("Succes");
		return pojoUpdateReq;
	}

	@Override
	public void run() {
		emailService.sendSimpleMail(emailDetails);
	}

}
