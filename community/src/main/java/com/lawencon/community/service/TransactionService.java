package com.lawencon.community.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.RoleList;
import com.lawencon.community.constant.StatusTransactions;
import com.lawencon.community.constant.TransactionType;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.dao.StatusTransactionDao;
import com.lawencon.community.dao.TransactionDao;
import com.lawencon.community.dao.UserCourseDao;
import com.lawencon.community.dao.UserEventDao;
import com.lawencon.community.dao.VoucherDao;
import com.lawencon.community.model.Course;
import com.lawencon.community.model.Event;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Membership;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.StatusTransaction;
import com.lawencon.community.model.Transaction;
import com.lawencon.community.model.User;
import com.lawencon.community.model.UserCourse;
import com.lawencon.community.model.UserEvent;
import com.lawencon.community.model.Voucher;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.transaction.PojoInsertTransactionReq;
import com.lawencon.community.pojo.transaction.PojoTransactionGetAllRes;
import com.lawencon.community.pojo.transaction.PojoTransactionGetAllResData;
import com.lawencon.community.pojo.transaction.PojoTransactionGetReportIncomeMemberRes;
import com.lawencon.community.pojo.transaction.PojoTransactionGetReportIncomeSuperAdminRes;
import com.lawencon.community.pojo.transaction.PojoTransactionGetReportParticipantSuperAdminRes;
import com.lawencon.community.pojo.transaction.PojoTransactionGetReportRes;
import com.lawencon.community.pojo.transaction.PojoUpdateTransactionReq;
import com.lawencon.community.util.DateUtil;
import com.lawencon.community.util.Generate;
import com.lawencon.security.principal.PrincipalService;

@Service
public class TransactionService extends ValidationService<Transaction>{
	
	@Autowired
	private final TransactionDao transactionDao;
	
	@Autowired
	private final FileDao fileDao;
	
	@Autowired
	private final VoucherDao voucherDao;
	
	@Autowired
	private final ProfileDao profileDao;
	
	@Autowired
	private final UserEventDao userEventDao;
	
	@Autowired
	private final UserCourseDao userCourseDao;
	
	@Autowired
	private final StatusTransactionDao statusTransactionDao;
	
	@Autowired
	private final UserService userService;
	
	@Autowired
	private final EventService eventService;
	
	@Autowired
	private final MembershipService membershipService;
	
	@Autowired
	private final CourseService courseService;
	
	@Autowired
	private final PrincipalService principalService;
	
	@Autowired
	private final VoucherService voucherService;
	
	private static final BigDecimal userShare = new BigDecimal(0.8);
	private static final BigDecimal systemShare = new BigDecimal(0.2);

	private Transaction insert(final Transaction data) {
		Transaction transInsert = null;

		try {
			final Transaction trans = new Transaction();
			ConnHandler.begin();
			trans.setTransactionDate(LocalDateTime.now());
			trans.setGrandTotal(data.getGrandTotal());
			trans.setFile(data.getFile());
			trans.setStatusTransaction(data.getStatusTransaction());
			trans.setTransactionCode(data.getTransactionCode());
			trans.setCreatedAt(LocalDateTime.now());
			trans.setIsApproved(false);
			trans.setUser(data.getUser());
			if (data.getEvent() != null) {
				trans.setEvent(data.getEvent());
			}
			if (data.getCourse() != null) {
				trans.setCourse(data.getCourse());
			}
			if (data.getMembership() != null) {
				trans.setMembership(data.getMembership());
			}
			if (data.getVoucher() != null) {
				trans.setVoucher(data.getVoucher());
			}
			transInsert = transactionDao.insert(trans);
			ConnHandler.commit();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return transInsert;
	}

	public Transaction update(final Transaction data) {
		Transaction transactionUpdate = null;

		try {
			ConnHandler.begin();
			transactionUpdate = transactionDao.update(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return transactionUpdate;
	}

	public Transaction getRefById(final String id) {
		return transactionDao.getRefById(id);
	}

	public boolean deleteById(final String id) {
		boolean eventDelete = false;

		try {
			ConnHandler.begin();
			eventDelete = transactionDao.deleteById(Transaction.class, id);
			ConnHandler.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return eventDelete;

	}

	public Transaction getByIdAndDetach(final String id) {
		return transactionDao.getByIdAndDetach(Transaction.class, id);
	}

	public PojoInsertRes insertRes(final PojoInsertTransactionReq data) {
		BigDecimal grandTotal = null;
		final PojoInsertRes pojo = new PojoInsertRes();
		Transaction transactionInsert = null;
		final String generateCode = Generate.generateCode(8);
		final User user = userService.getByRefId(principalService.getAuthPrincipal());
		final Transaction trans = new Transaction();
		trans.setUser(user);
		trans.setTransactionDate(LocalDateTime.now());

		Float value = (float) 0;

		if (data.getVoucherCode() != null) {
			Voucher voucher = null;
			final Optional<Voucher> optVoucher = voucherDao.getByVoucherCode(data.getVoucherCode());
			if (optVoucher.isPresent()) {
				voucher = voucherService.getRefById(optVoucher.get().getId());
				trans.setVoucher(voucher);
				value = trans.getVoucher().getAmount().floatValue();
			}
		}

		if (data.getEventId() != null) {
			final Event event = eventService.getRefById(data.getEventId());
			trans.setEvent(event);
			grandTotal = event.getPrice().subtract(BigDecimal.valueOf(value));
		}
		if (data.getMembershipId() != null) {
			final Membership membership = membershipService.getRefById(data.getMembershipId());
			trans.setMembership(membership);
			grandTotal = membership.getAmount().subtract(BigDecimal.valueOf(value));
		}
		if (data.getCourseId() != null) {
			final Course course = courseService.getRefById(data.getCourseId());
			trans.setCourse(course);
			grandTotal = course.getPrice().subtract(BigDecimal.valueOf(value));
		}

		final StatusTransaction getStatus = statusTransactionDao
				.getByStatusCode(StatusTransactions.PENDING.getStatusCode()).get();
		trans.setStatusTransaction(getStatus);

		trans.setGrandTotal(grandTotal);
		trans.setIsApproved(false);
		trans.setTransactionCode(generateCode);

		final File fileInsert = new File();
		fileInsert.setFileName(data.getFile().getFileName());
		fileInsert.setFileExtension(data.getFile().getFileExtension());
		fileInsert.setFileContent(data.getFile().getFileContent());

		ConnHandler.begin();
		final File file = fileDao.insert(fileInsert);
		ConnHandler.commit();
		trans.setFile(file);

		transactionInsert = insert(trans);
		pojo.setId(transactionInsert.getId());
		pojo.setMessage("Berhasil");
		return pojo;
	}

	public PojoUpdateRes updateRes(final PojoUpdateTransactionReq data) {
		Transaction transactionUpdate = null;
		transactionUpdate = getByIdAndDetach(data.getId());
		final Transaction transaction = transactionUpdate;
		final PojoUpdateRes pojoUpdate = new PojoUpdateRes();

		if (data.getStatusTransaction().equals(StatusTransactions.APPROVE.getStatusCode())) {
			transaction.setIsApproved(data.getIsApproved());
			final StatusTransaction getStatus = statusTransactionDao
					.getByStatusCode(StatusTransactions.APPROVE.getStatusCode()).get();
			transaction.setStatusTransaction(getStatus);

			transaction.setVersion(data.getVer());
			transactionUpdate = update(transaction);

			if (transactionUpdate.getIsApproved() == true) {
				if (transactionUpdate.getMembership() != null) {
					addSystemBalance(transactionUpdate);
					final User user = userService.getByRefId(principalService.getAuthPrincipal());
					Profile profile = profileDao.getByIdAndDetach(user.getProfile().getId());

					profile.setPremiumUntil(
							LocalDateTime.now().plusDays(transactionUpdate.getMembership().getDuration()));

					ConnHandler.begin();
					profileDao.update(profile);
					ConnHandler.commit();

				} else if (transactionUpdate.getEvent() != null || transactionUpdate.getCourse() != null) {

					if (transactionUpdate.getEvent() != null) {

						final UserEvent userEvent = new UserEvent();
						userEvent.setUser(transaction.getUser());
						userEvent.setEvent(transaction.getEvent());

						ConnHandler.begin();
						userEventDao.insert(userEvent);
						ConnHandler.commit();

					} else if (transactionUpdate.getCourse() != null) {
						final UserCourse userCourse = new UserCourse();
						userCourse.setUser(transaction.getUser());
						userCourse.setCourse(transaction.getCourse());

						ConnHandler.begin();
						userCourseDao.insert(userCourse);
						ConnHandler.commit();
					}

					profitSharing(transactionUpdate);
				}

			}

			pojoUpdate.setVer(data.getVer());
			pojoUpdate.setMessage("Approved");
		} else if (data.getStatusTransaction().equals(StatusTransactions.REJECTED.getStatusCode())) {
			transaction.setIsApproved(data.getIsApproved());
			final StatusTransaction getStatus = statusTransactionDao
					.getByStatusCode(StatusTransactions.REJECTED.getStatusCode()).get();
			transaction.setStatusTransaction(getStatus);

			transaction.setVersion(data.getVer());
			transactionUpdate = update(transaction);

			pojoUpdate.setVer(data.getVer());
			pojoUpdate.setMessage("Rejected");

		}
		return pojoUpdate;

	}

	public PojoDeleteRes deleteRes(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deleteById(id);
		res.setMessage("Berhasil Dihapus");
		return res;
	}

	public List<PojoTransactionGetAllRes> getAllTransaction(final String type) {
		final List<PojoTransactionGetAllRes> pojos = new ArrayList<>();
		List<Transaction> res = new ArrayList<>();
		if (type.isEmpty()) {
			res = transactionDao.getAll();
		}
		if (type.equals(TransactionType.EVENT.getTypeName())) {
			res = transactionDao.getByEvent();
		}
		if (type.equals(TransactionType.COURSE.getTypeName())) {
			res = transactionDao.getByCourse();
		}
		if (type.equals(TransactionType.MEMBERSHIP.getTypeName())) {
			res = transactionDao.getByMembership();
		}
		for (int i = 0; i < res.size(); i++) {
			final PojoTransactionGetAllRes pojo = new PojoTransactionGetAllRes();
			final Transaction transaction = res.get(i);
			final User user = userService.getByRefId(transaction.getUser().getId());
			if (transaction.getEvent() != null) {
				final Event event = eventService.getRefById(transaction.getEvent().getId());
				pojo.setItemName(event.getEventName());
			}
			if (transaction.getCourse() != null) {
				final Course course = courseService.getRefById(transaction.getCourse().getId());
				pojo.setItemName(course.getCourseName());
			}
			if (transaction.getMembership() != null) {
				final Membership membership = membershipService.getRefById(transaction.getMembership().getId());
				pojo.setItemName(membership.getMembershipName());
			}
			pojo.setId(transaction.getId());
			pojo.setGrandTotal(transaction.getGrandTotal());
			pojo.setFileId(transaction.getFile().getId());
			pojo.setFullName(user.getProfile().getFullName());
			pojo.setStatusTransaction(transaction.getStatusTransaction().getStatusName());
			pojo.setIsApproved(transaction.getIsApproved());
			pojo.setVer(transaction.getVersion());
			pojos.add(pojo);
		}
		return pojos;
	}

	public PojoTransactionGetAllRes getTransactionById(final String id) {

		final Optional<Transaction> transaction = transactionDao.getTransactionById(id);
		final PojoTransactionGetAllRes pojoTransactionGetAll = new PojoTransactionGetAllRes();
		pojoTransactionGetAll.setId(transaction.get().getId());
		if (transaction.get().getEvent() != null) {
			final Event event = eventService.getRefById(transaction.get().getEvent().getId());
			pojoTransactionGetAll.setItemName(event.getEventName());
		}
		if (transaction.get().getCourse() != null) {
			final Course course = courseService.getRefById(transaction.get().getCourse().getId());
			pojoTransactionGetAll.setItemName(course.getCourseName());
		}
		if (transaction.get().getMembership() != null) {
			final Membership membership = membershipService.getRefById(transaction.get().getMembership().getId());
			pojoTransactionGetAll.setItemName(membership.getMembershipName());
		}

		pojoTransactionGetAll.setId(transaction.get().getId());
		pojoTransactionGetAll.setFullName(transaction.get().getUser().getProfile().getFullName());
		// pojoTransactionGetAll.setItemName(transaction.get().getCourse().getCourseName());
		pojoTransactionGetAll.setFileId(transaction.get().getFile().getId());
		pojoTransactionGetAll.setStatusTransaction(transaction.get().getStatusTransaction().getId());
		pojoTransactionGetAll.setGrandTotal(transaction.get().getGrandTotal());
		pojoTransactionGetAll.setIsApproved(transaction.get().getIsApproved());
		pojoTransactionGetAll.setVer(transaction.get().getVersion());

		return pojoTransactionGetAll;
	}

	public PojoTransactionGetAllResData getTransactionPage(final Integer limit, final Integer offset,
			final String type) {
		final List<PojoTransactionGetAllRes> pojos = new ArrayList<>();
		List<Transaction> res = transactionDao.getAllTransaction(limit, offset);
		if (type.isEmpty()) {
			res = transactionDao.getAll();
		}
		if (type.equals(TransactionType.EVENT.getTypeName())) {
			res = transactionDao.getByEventPage(limit, offset);
		}
		if (type.equals(TransactionType.COURSE.getTypeName())) {
			res = transactionDao.getByCoursePage(limit, offset);
		}
		if (type.equals(TransactionType.MEMBERSHIP.getTypeName())) {
			res = transactionDao.getByMembershipPage(limit, offset);
		}
		for (int i = 0; i < res.size(); i++) {
			final PojoTransactionGetAllRes pojo = new PojoTransactionGetAllRes();
			final Transaction transaction = res.get(i);
			final User user = userService.getByRefId(transaction.getUser().getId());
			if (transaction.getEvent() != null) {
				final Event event = eventService.getRefById(transaction.getEvent().getId());
				pojo.setItemName(event.getEventName());
			}
			if (transaction.getCourse() != null) {
				final Course course = courseService.getRefById(transaction.getCourse().getId());
				pojo.setItemName(course.getCourseName());
			}
			if (transaction.getMembership() != null) {
				final Membership membership = membershipService.getRefById(transaction.getMembership().getId());
				pojo.setItemName(membership.getMembershipName());
			}
			pojo.setId(transaction.getId());
			pojo.setGrandTotal(transaction.getGrandTotal());
			pojo.setFileId(transaction.getFile().getId());
			pojo.setFullName(user.getProfile().getFullName());
			pojo.setIsApproved(transaction.getIsApproved());
			pojo.setVer(transaction.getVersion());
			pojos.add(pojo);
		}
		final PojoTransactionGetAllResData pojoTransactionData = new PojoTransactionGetAllResData();
		pojoTransactionData.setData(pojos);
		pojoTransactionData.setTotal(transactionDao.getTotalTransaction(type));
		return pojoTransactionData;
	}

	private void addSystemBalance(final Transaction data) {
		final Optional<User> system = userService.getUserSystem(RoleList.SYSTEM.getRoleCode());
		final User userSystem = userService.getByRefId(system.get().getId());
		final Profile systemProfile = profileDao.getRefById(userSystem.getProfile().getId());
		Voucher voucher = null;
		BigDecimal value = null;

		if (data.getMembership() != null) {
			final Membership membership = membershipService.getRefById(data.getMembership().getId());
			value = membership.getAmount();
		}
		if (data.getVoucher() != null) {
			voucher = voucherService.getRefById(data.getVoucher().getId());
			value.subtract(voucher.getAmount());
		}
		BigDecimal systemBalance = systemProfile.getBalance();
		BigDecimal toSystem = systemBalance.add(value);
		systemProfile.setBalance(toSystem);
		ConnHandler.begin();
		profileDao.update(systemProfile);
		ConnHandler.commit();
	}

	private void profitSharing(final Transaction data) {
		final Optional<User> system = userService.getUserSystem(RoleList.SYSTEM.getRoleCode());
		final User userSystem = userService.getByRefId(system.get().getId());
		final Profile systemProfile = profileDao.getRefById(userSystem.getProfile().getId());
		User organizer = null;
		Profile organizerProfile = null;
		BigDecimal value = null;
		Voucher voucher = null;
		if (data.getEvent() != null) {
			final Event event = eventService.getRefById(data.getEvent().getId());
			organizer = userService.getByRefId(event.getUser().getId());
			organizerProfile = profileDao.getRefById(organizer.getProfile().getId());
			value = event.getPrice();
		}
		if (data.getCourse() != null) {
			final Course course = courseService.getRefById(data.getCourse().getId());
			organizer = userService.getByRefId(course.getUser().getId());
			organizerProfile = profileDao.getRefById(organizer.getProfile().getId());
			value = course.getPrice();
		}
		if (data.getVoucher() != null) {
			voucher = voucherService.getRefById(data.getVoucher().getId());
			value.subtract(voucher.getAmount());
		}
		BigDecimal systemBalance = systemProfile.getBalance();
		BigDecimal organizerBalance = organizerProfile.getBalance();
		BigDecimal toOrganizer = organizerBalance.add(value.multiply(userShare));
		BigDecimal toSystem = systemBalance.add(value.multiply(systemShare));
		organizerProfile.setBalance(toOrganizer);
		systemProfile.setBalance(toSystem);
		ConnHandler.begin();
		profileDao.update(organizerProfile);
		profileDao.update(systemProfile);
		ConnHandler.commit();
	}

	public List<PojoTransactionGetReportRes> getMemberReportParticipantByDate(final String userId,
			final String startDate, final String endDate) {

		final List<PojoTransactionGetReportRes> transactions = transactionDao.getMemberReportParticipant(userId,
				DateUtil.strToLocalDate(startDate), DateUtil.strToLocalDate(endDate));

		for (int i = 0; i < transactions.size(); i++) {
			final PojoTransactionGetReportRes transactionReport = new PojoTransactionGetReportRes();
			transactionReport.setActivityType(transactions.get(i).getActivityType());
			transactionReport.setItemName(transactions.get(i).getItemName());
			transactionReport.setStartDate(transactions.get(i).getStartDate());
			transactionReport.setTotalParticipants(transactions.get(i).getTotalParticipants());
		}

		return transactions;
	}

	public List<PojoTransactionGetReportIncomeMemberRes> getReportIncomeMemberByDate(final String userId,
			final String startDate, final String endDate) {

		final List<PojoTransactionGetReportIncomeMemberRes> transactions = transactionDao.getReportIncomeMember(userId,
				DateUtil.strToLocalDate(startDate), DateUtil.strToLocalDate(endDate));

		for (int i = 0; i < transactions.size(); i++) {
			final PojoTransactionGetReportIncomeMemberRes transactionReport = new PojoTransactionGetReportIncomeMemberRes();
			transactionReport.setActivityType(transactions.get(i).getActivityType());
			transactionReport.setItemName(transactions.get(i).getItemName());
			transactionReport.setTotalIncomes(transactions.get(i).getTotalIncomes());

		}

		return transactions;
	}

	public List<PojoTransactionGetReportParticipantSuperAdminRes> getReportParticipantSuperAdminByDate(
			final String startDate, final String endDate) {

		final List<PojoTransactionGetReportParticipantSuperAdminRes> transactions = transactionDao
				.getReportSuperAdminParticipant(DateUtil.strToLocalDate(startDate), DateUtil.strToLocalDate(endDate));

		for (int i = 0; i < transactions.size(); i++) {
			final PojoTransactionGetReportParticipantSuperAdminRes reportSuperAdmin = new PojoTransactionGetReportParticipantSuperAdminRes();
			reportSuperAdmin.setMemberName(transactions.get(i).getMemberName());
			reportSuperAdmin.setProviderName(transactions.get(i).getProviderName());

			reportSuperAdmin.setActivityType(transactions.get(i).getActivityType());
			reportSuperAdmin.setItemName(transactions.get(i).getItemName());
			reportSuperAdmin.setStartDate(transactions.get(i).getStartDate());
			reportSuperAdmin.setTotalParticipants(transactions.get(i).getTotalParticipants());

		}

		return transactions;
	}

	public List<PojoTransactionGetReportIncomeSuperAdminRes> getReportIncomeSuperAdminByDate(final String startDate,
			final String endDate) {

		final List<PojoTransactionGetReportIncomeSuperAdminRes> transactions = transactionDao
				.getReportIncomeSuperAdmin(DateUtil.strToLocalDate(startDate), DateUtil.strToLocalDate(endDate));

		for (int i = 0; i < transactions.size(); i++) {
			final PojoTransactionGetReportIncomeSuperAdminRes reportSuperAdmin = new PojoTransactionGetReportIncomeSuperAdminRes();
			reportSuperAdmin.setFullName(transactions.get(i).getFullName());

			reportSuperAdmin.setActivityType(transactions.get(i).getActivityType());
			reportSuperAdmin.setTotalIncome(transactions.get(i).getTotalIncome());

		}

		return transactions;
	}

	@Override
	void valNotNullable(Transaction data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void valIdNull(Transaction data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void valIdNotNull(Transaction data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void valBkNull(Transaction data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void valFkNull(Transaction data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void valMaxLength(Transaction data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void valIdPresent(Transaction data) {
		// TODO Auto-generated method stub
		
	}

	
}
