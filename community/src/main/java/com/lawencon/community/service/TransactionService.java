package com.lawencon.community.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.TransactionType;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.TransactionDao;
import com.lawencon.community.dao.VoucherDao;
import com.lawencon.community.model.Course;
import com.lawencon.community.model.Event;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Membership;
import com.lawencon.community.model.Transaction;
import com.lawencon.community.model.User;
import com.lawencon.community.model.Voucher;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.transaction.PojoInsertTransactionReq;
import com.lawencon.community.pojo.transaction.PojoTransactionGetAllRes;
import com.lawencon.community.pojo.transaction.PojoTransactionGetByCourseIdRes;
import com.lawencon.community.pojo.transaction.PojoTransactionGetByEventIdRes;
import com.lawencon.community.pojo.transaction.PojoTransactionGetByMembershipIdRes;
import com.lawencon.community.pojo.transaction.PojoUpdateTransactionReq;
import com.lawencon.security.principal.PrincipalService;

@Service
public class TransactionService {
	private final TransactionDao transactionDao;
	private final FileDao fileDao;
	private final VoucherDao voucherDao;
	private final UserService userService;
	private final EventService eventService;
	private final MembershipService membershipService;
	private final CourseService courseService;
	private final PrincipalService principalService;
	private final VoucherService voucherService;

	public TransactionService(final TransactionDao transactionDao, final UserService userService,
			final EventService eventService, final MembershipService membershipService,
			final CourseService courseService, final PrincipalService principalService,
			final VoucherService voucherService, FileDao fileDao, VoucherDao voucherDao) {
		this.transactionDao = transactionDao;
		this.userService = userService;
		this.eventService = eventService;
		this.membershipService = membershipService;
		this.courseService = courseService;
		this.principalService = principalService;
		this.voucherService = voucherService;
		this.fileDao = fileDao;
		this.voucherDao = voucherDao;
	}

	private Transaction insert(final Transaction data) {
		Transaction transInsert = null;
		BigDecimal grandTotal = null;
		try {
			final Transaction trans = new Transaction();
			ConnHandler.begin();
			trans.setTransactionDate(LocalDateTime.now());
			trans.setGrandTotal(grandTotal);
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

	private List<Transaction> getAll(final String type) {
		return transactionDao.getTransaction(type);
	}

	public PojoInsertRes insertRes(final PojoInsertTransactionReq data) {
		BigDecimal grandTotal = null;
		final PojoInsertRes pojo = new PojoInsertRes();
		Transaction transactionInsert = null;
		final User user = userService.getByRefId(principalService.getAuthPrincipal());
		final Transaction trans = new Transaction();
		trans.setUser(user);
		trans.setTransactionDate(LocalDateTime.now());

		if (data.getEventId() != null) {
			final Event event = eventService.getRefById(data.getEventId());
			trans.setEvent(event);
			grandTotal = event.getPrice();
		}
		if (data.getMembershipId() != null) {
			final Membership membership = membershipService.getRefById(data.getMembershipId());
			trans.setMembership(membership);
			grandTotal = membership.getAmount();
		}
		if (data.getCourseId() != null) {
			final Course course = courseService.getRefById(data.getCourseId());
			trans.setCourse(course);
			grandTotal = course.getPrice();
		}
		if (data.getVoucherCode() != null) {
			Voucher voucher = null;
			final Optional<Voucher> optVoucher = voucherDao.getByVoucherCode(data.getVoucherCode());
			if (optVoucher.isPresent()) {
				voucher = voucherService.getRefById(optVoucher.get().getId());
				trans.setVoucher(voucher);
			}
		}

		trans.setGrandTotal(grandTotal);
		trans.setIsApproved(false);

		final File fileInsert = new File();
		fileInsert.setFileName(data.getFile().getFileName());
		fileInsert.setFileExtension(data.getFile().getFileExtension());
		fileInsert.setFileContent(data.getFile().getFileContent());

		ConnHandler.begin();
		final File file = fileDao.insert(fileInsert);
		trans.setFile(file);

		transactionInsert = transactionDao.insert(trans);
		ConnHandler.commit();
		pojo.setId(transactionInsert.getId());
		pojo.setMessage("Berhasil");
		return pojo;
	}

	public PojoUpdateRes updateRes(final PojoUpdateTransactionReq data) {
		Transaction transactionUpdate = null;

		transactionUpdate = getByIdAndDetach(data.getTransactionId());

		final Transaction transaction = transactionUpdate;

		transaction.setIsApproved(true);
		transaction.setVersion(data.getVer());

		transactionUpdate = update(transaction);

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

	public List<PojoTransactionGetAllRes> getAllRes(final String type) {
		final List<PojoTransactionGetAllRes> pojos = new ArrayList<>();
		List<Transaction> res = new ArrayList<>();
		if (type.equals(null)) {
			res = getAll(type);
		}
		if (type.equals(TransactionType.EVENT.getTypeName())) {
			res = getAll(TransactionType.EVENT.getTypeName());
		}
		if (type.equals(TransactionType.COURSE.getTypeName())) {
			res = getAll(TransactionType.COURSE.getTypeName());
		}
		if (type.equals(TransactionType.MEMBERSHIP.getTypeName())) {
			res = getAll(TransactionType.MEMBERSHIP.getTypeName());
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
			pojos.add(pojo);
		}

		return pojos;
	}

	public List<PojoTransactionGetByEventIdRes> getByEventId(final String id) {
		final List<PojoTransactionGetByEventIdRes> pojos = new ArrayList<>();
		final List<Transaction> res = transactionDao.getByEventId(id);

		for (int i = 0; i < res.size(); i++) {
			final PojoTransactionGetByEventIdRes pojo = new PojoTransactionGetByEventIdRes();
			final Transaction transaction = res.get(i);
			final User user = userService.getByRefId(transaction.getUser().getId());

			final Event event = eventService.getRefById(transaction.getEvent().getId());
			pojo.setItemName(event.getEventName());

			ConnHandler.getManager().detach(transaction);
			pojo.setId(transaction.getId());
			pojo.setGrandTotal(transaction.getGrandTotal());
			pojo.setFileId(transaction.getFile().getId());
			pojo.setFullName(user.getProfile().getFullName());
			pojo.setIsApproved(transaction.getIsApproved());

			pojos.add(pojo);
		}

		return pojos;
	}

	public List<PojoTransactionGetByCourseIdRes> getByCourseId(final String id) {
		final List<PojoTransactionGetByCourseIdRes> pojos = new ArrayList<>();
		final List<Transaction> res = transactionDao.getByCourseId(id);

		for (int i = 0; i < res.size(); i++) {
			final PojoTransactionGetByCourseIdRes pojo = new PojoTransactionGetByCourseIdRes();
			final Transaction transaction = res.get(i);
			final User user = userService.getByRefId(transaction.getUser().getId());

			final Course course = courseService.getRefById(transaction.getCourse().getId());
			pojo.setItemName(course.getCourseName());

			ConnHandler.getManager().detach(transaction);
			pojo.setId(transaction.getId());
			pojo.setGrandTotal(transaction.getGrandTotal());
			pojo.setFileId(transaction.getFile().getId());
			pojo.setFullName(user.getProfile().getFullName());
			pojo.setIsApproved(transaction.getIsApproved());

			pojos.add(pojo);
		}

		return pojos;
	}

	public List<PojoTransactionGetByMembershipIdRes> getByMembershipId(final String id) {
		final List<PojoTransactionGetByMembershipIdRes> pojos = new ArrayList<>();
		final List<Transaction> res = transactionDao.getByMembershipId(id);

		for (int i = 0; i < res.size(); i++) {
			final PojoTransactionGetByMembershipIdRes pojo = new PojoTransactionGetByMembershipIdRes();
			final Transaction transaction = res.get(i);
			final User user = userService.getByRefId(transaction.getUser().getId());

			final Membership membership = membershipService.getRefById(transaction.getMembership().getId());
			pojo.setItemName(membership.getMembershipName());

			ConnHandler.getManager().detach(transaction);
			pojo.setId(transaction.getId());
			pojo.setGrandTotal(transaction.getGrandTotal());
			pojo.setFileId(transaction.getFile().getId());
			pojo.setFullName(user.getProfile().getFullName());
			pojo.setIsApproved(transaction.getIsApproved());

			pojos.add(pojo);
		}

		return pojos;
	}

}
