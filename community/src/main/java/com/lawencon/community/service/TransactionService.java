package com.lawencon.community.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.RoleList;
import com.lawencon.community.constant.TransactionType;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.dao.TransactionDao;
import com.lawencon.community.dao.VoucherDao;
import com.lawencon.community.model.Course;
import com.lawencon.community.model.Event;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Membership;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.Transaction;
import com.lawencon.community.model.User;
import com.lawencon.community.model.Voucher;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.transaction.PojoInsertTransactionReq;
import com.lawencon.community.pojo.transaction.PojoTransactionGetAllRes;
import com.lawencon.community.pojo.transaction.PojoTransactionGetAllResData;
import com.lawencon.community.pojo.transaction.PojoTransactionGetReportRes;
import com.lawencon.community.pojo.transaction.PojoUpdateTransactionReq;
import com.lawencon.community.util.DateUtil;
import com.lawencon.security.principal.PrincipalService;

@Service
public class TransactionService {
	private final TransactionDao transactionDao;
	private final FileDao fileDao;
	private final VoucherDao voucherDao;
	private final ProfileDao profileDao;
	private final UserService userService;
	private final EventService eventService;
	private final MembershipService membershipService;
	private final CourseService courseService;
	private final PrincipalService principalService;
	private final VoucherService voucherService;
	private static final BigDecimal userShare = new BigDecimal(0.8);
	private static final BigDecimal systemShare = new BigDecimal(0.2);

	public TransactionService(final TransactionDao transactionDao, final UserService userService,
			final EventService eventService, final MembershipService membershipService,
			final CourseService courseService, final PrincipalService principalService,
			final VoucherService voucherService, final FileDao fileDao, final VoucherDao voucherDao,
			final ProfileDao profileDao) {
		this.transactionDao = transactionDao;
		this.userService = userService;
		this.eventService = eventService;
		this.membershipService = membershipService;
		this.courseService = courseService;
		this.principalService = principalService;
		this.voucherService = voucherService;
		this.fileDao = fileDao;
		this.voucherDao = voucherDao;
		this.profileDao = profileDao;
	}

	private Transaction insert(final Transaction data) {
		Transaction transInsert = null;

		try {
			final Transaction trans = new Transaction();
			ConnHandler.begin();
			trans.setTransactionDate(LocalDateTime.now());
			trans.setGrandTotal(data.getGrandTotal());
			trans.setFile(data.getFile());
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

	private List<Transaction> getAll(final String type, final String id) {
		return transactionDao.getTransaction(type, id);
	}

	public PojoInsertRes insertRes(final PojoInsertTransactionReq data) {
		BigDecimal grandTotal = null;
		final PojoInsertRes pojo = new PojoInsertRes();
		Transaction transactionInsert = null;
		final User user = userService.getByRefId(principalService.getAuthPrincipal());
		final Transaction trans = new Transaction();
		trans.setUser(user);
		trans.setTransactionDate(LocalDateTime.now());
		
		Float value=(float) 0;
		
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
		

		trans.setGrandTotal(grandTotal);
		trans.setIsApproved(false);

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
		transaction.setIsApproved(data.getIsApproved());
		transaction.setVersion(data.getVer());
		transactionUpdate = update(transaction);
		if (transactionUpdate.getIsApproved() == true) {
			if (transactionUpdate.getMembership() != null) {
				addSystemBalance(transactionUpdate);
				final User user = userService.getByRefId(principalService.getAuthPrincipal());

				Profile profileUpdate = null;
				Profile profile = profileDao.getByIdAndDetach(user.getProfile().getId());

				profile.setPremiumUntil(LocalDateTime.now().plusDays(transactionUpdate.getMembership().getDuration()));
			

				ConnHandler.begin();
				profileUpdate = profileDao.update(profile);
				ConnHandler.commit();
			} else if (transactionUpdate.getEvent() != null || transactionUpdate.getCourse() != null) {
				profitSharing(transactionUpdate);
			}
		}

		final PojoUpdateRes pojoUpdate = new PojoUpdateRes();
		pojoUpdate.setVer(data.getVer());
		pojoUpdate.setMessage("Updated");
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

		pojoTransactionGetAll.setId(listTransaction.getId());
		pojoTransactionGetAll.setFullName(listTransaction.getUser().getProfile().getFullName());
		pojoTransactionGetAll.setItemName(listTransaction.getCourse().getCourseName());
		pojoTransactionGetAll.setFileId(listTransaction.getFile().getId());
		pojoTransactionGetAll.setGrandTotal(listTransaction.getGrandTotal());
		pojoTransactionGetAll.setIsApproved(listTransaction.getIsApproved());
		pojoTransactionGetAll.setVer(listTransaction.getVersion());

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

//	public List<PojoTransactionGetAllRes> getAllRes(final String type) {
//		final List<PojoTransactionGetAllRes> pojos = new ArrayList<>();
//		List<Transaction> res = new ArrayList<>();
//		if (type.equals(null)) {
//			res = getAll(type);
//		}
//		if (type.equals(TransactionType.EVENT.getTypeName())) {
//			res = getAll(TransactionType.EVENT.getTypeName());
//		}
//		if (type.equals(TransactionType.COURSE.getTypeName())) {
//			res = getAll(TransactionType.COURSE.getTypeName());
//		}
//		if (type.equals(TransactionType.MEMBERSHIP.getTypeName())) {
//			res = getAll(TransactionType.MEMBERSHIP.getTypeName());
//		}
//		for (int i = 0; i < res.size(); i++) {
//			final PojoTransactionGetAllRes pojo = new PojoTransactionGetAllRes();
//			final Transaction transaction = res.get(i);
//			final User user = userService.getByRefId(transaction.getUser().getId());
//			if (transaction.getEvent() != null) {
//				final Event event = eventService.getRefById(transaction.getEvent().getId());
//				pojo.setItemName(event.getEventName());
//			}
//			if (transaction.getCourse() != null) {
//				final Course course = courseService.getRefById(transaction.getCourse().getId());
//				pojo.setItemName(course.getCourseName());
//			}
//			if (transaction.getMembership() != null) {
//				final Membership membership = membershipService.getRefById(transaction.getMembership().getId());
//				pojo.setItemName(membership.getMembershipName());
//			}
//			pojo.setId(transaction.getId());
//			pojo.setGrandTotal(transaction.getGrandTotal());
//			pojo.setFileId(transaction.getFile().getId());
//			pojo.setFullName(user.getProfile().getFullName());
//			pojo.setIsApproved(transaction.getIsApproved());
//			pojos.add(pojo);
//		}
//
//		return pojos;
//	}

//	public List<PojoTransactionGetByEventIdRes> getByEventId(final String id) {
//		final List<PojoTransactionGetByEventIdRes> pojos = new ArrayList<>();
//		final List<Transaction> res = transactionDao.getByEventId(id);
//
//		for (int i = 0; i < res.size(); i++) {
//			final PojoTransactionGetByEventIdRes pojo = new PojoTransactionGetByEventIdRes();
//			final Transaction transaction = res.get(i);
//			final User user = userService.getByRefId(transaction.getUser().getId());
//
//			final Event event = eventService.getRefById(transaction.getEvent().getId());
//			pojo.setItemName(event.getEventName());
//
//			ConnHandler.getManager().detach(transaction);
//			pojo.setId(transaction.getId());
//			pojo.setGrandTotal(transaction.getGrandTotal());
//			pojo.setFileId(transaction.getFile().getId());
//			pojo.setFullName(user.getProfile().getFullName());
//			pojo.setIsApproved(transaction.getIsApproved());
//
//			pojos.add(pojo);
//		}
//
//		return pojos;
//	}
//
//	public List<PojoTransactionGetByCourseIdRes> getByCourseId(final String id) {
//		final List<PojoTransactionGetByCourseIdRes> pojos = new ArrayList<>();
//		final List<Transaction> res = transactionDao.getByCourseId(id);
//
//		for (int i = 0; i < res.size(); i++) {
//			final PojoTransactionGetByCourseIdRes pojo = new PojoTransactionGetByCourseIdRes();
//			final Transaction transaction = res.get(i);
//			final User user = userService.getByRefId(transaction.getUser().getId());
//
//			final Course course = courseService.getRefById(transaction.getCourse().getId());
//			pojo.setItemName(course.getCourseName());
//
//			ConnHandler.getManager().detach(transaction);
//			pojo.setId(transaction.getId());
//			pojo.setGrandTotal(transaction.getGrandTotal());
//			pojo.setFileId(transaction.getFile().getId());
//			pojo.setFullName(user.getProfile().getFullName());
//			pojo.setIsApproved(transaction.getIsApproved());
//
//			pojos.add(pojo);
//		}
//
//		return pojos;
//	}
//
//	public List<PojoTransactionGetByMembershipIdRes> getByMembershipId(final String id) {
//		final List<PojoTransactionGetByMembershipIdRes> pojos = new ArrayList<>();
//		final List<Transaction> res = transactionDao.getByMembershipId(id);
//
//		for (int i = 0; i < res.size(); i++) {
//			final PojoTransactionGetByMembershipIdRes pojo = new PojoTransactionGetByMembershipIdRes();
//			final Transaction transaction = res.get(i);
//			final User user = userService.getByRefId(transaction.getUser().getId());
//
//			final Membership membership = membershipService.getRefById(transaction.getMembership().getId());
//			pojo.setItemName(membership.getMembershipName());
//
//			ConnHandler.getManager().detach(transaction);
//			pojo.setId(transaction.getId());
//			pojo.setGrandTotal(transaction.getGrandTotal());
//			pojo.setFileId(transaction.getFile().getId());
//			pojo.setFullName(user.getProfile().getFullName());
//			pojo.setIsApproved(transaction.getIsApproved());
//
//			pojos.add(pojo);
//		}
//
//		return pojos;
//	}

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

	
	public List<PojoTransactionGetReportRes> getReportByDate(final String startDate, final String endDate){
		
		final List<PojoTransactionGetReportRes> transactions = transactionDao.getCourseReport(DateUtil.strToLocalDate(startDate),DateUtil.strToLocalDate(endDate));
		
		for(int i = 0; i < transactions.size(); i++) {
			final PojoTransactionGetReportRes transactionReport = new PojoTransactionGetReportRes();
			transactionReport.setActivityType(transactions.get(i).getActivityType());
			transactionReport.setItemName(transactions.get(i).getItemName());
			transactionReport.setStartDate(transactions.get(i).getStartDate());
			transactionReport.setTotalParticipants(transactions.get(i).getTotalParticipants());
			
		}
		
		return transactions;
	}
}

