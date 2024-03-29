package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.PollingAnswerDao;
import com.lawencon.community.dao.PollingChoiceDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.PostDetailDao;
import com.lawencon.community.dao.PostFileDao;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.dao.UserBookmarkDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dao.UserLikeDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.model.File;
import com.lawencon.community.model.PollingAnswer;
import com.lawencon.community.model.PollingChoice;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostDetail;
import com.lawencon.community.model.PostFile;
import com.lawencon.community.model.PostType;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.User;
import com.lawencon.community.model.UserBookmark;
import com.lawencon.community.model.UserLike;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.file.PojoFileInsertReq;
import com.lawencon.community.pojo.pollinganswer.PojoPollingAnswerGetCountRes;
import com.lawencon.community.pojo.pollinganswer.PojoPollingAnswerRes;
import com.lawencon.community.pojo.pollingchoice.PojoPollingChoiceGetAllRes;
import com.lawencon.community.pojo.pollingchoice.PojoPollingChoiceInsertReq;
import com.lawencon.community.pojo.post.PojoPostGetAllRes;
import com.lawencon.community.pojo.post.PojoPostGetAllResData;
import com.lawencon.community.pojo.post.PojoPostInsertReq;
import com.lawencon.community.pojo.post.PojoPostUpdateReq;
import com.lawencon.community.pojo.postdetail.PojoPostDetailGetAllRes;
import com.lawencon.community.pojo.postdetail.PojoPostDetailInsertReq;
import com.lawencon.community.pojo.postdetail.PojoPostDetailUpdateReq;
import com.lawencon.community.pojo.postfile.PojoPostFileInsertListReq;
import com.lawencon.community.pojo.userbookmark.PojoPostBookmarkRes;
import com.lawencon.community.pojo.userbookmark.PojoUserBookmarkGetAllByUser;
import com.lawencon.community.pojo.userbookmark.PojoUserBookmarkGetAllRes;
import com.lawencon.community.pojo.userbookmark.PojoUserBookmarkInsertReq;
import com.lawencon.community.pojo.userlike.PojoPostLikeRes;
import com.lawencon.community.pojo.userlike.PojoUserLikeGetAllByUser;
import com.lawencon.community.pojo.userlike.PojoUserLikeGetAllRes;
import com.lawencon.community.pojo.userlike.PojoUserLikeInsertReq;
import com.lawencon.security.principal.PrincipalService;

@Service
public class PostService {
	@Autowired
	private PostDao postDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private PostTypeDao postTypeDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private PostFileDao postFileDao;

	@Autowired
	private UserLikeDao userLikeDao;

	@Autowired
	private UserBookmarkDao userBookmarkDao;

	@Autowired
	private PostDetailDao postDetailDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private PollingChoiceDao pollingChoiceDao;

	@Autowired
	private PollingAnswerDao pollingAnswerDao;

	@Autowired
	private ProfileDao profileDao;

	@Autowired
	private PollingService pollingService;

	@Autowired
	private PrincipalService principalService;

	private void valIdNull(final Post data) {
		if (data.getId() != null) {
			throw new RuntimeException("ID harus kosong");
		}
	}

	private void valIdNotNull(final Post data) {
		if (data.getId() == null) {
			throw new RuntimeException("ID kosong");
		}
	}

	private void valNotNullable(final Post data) {
		if (data.getPostTitle() == null) {
			throw new RuntimeException("Judul Postingan Kosong");
		}
		if (data.getPostContent() == null) {
			throw new RuntimeException("Konten Postingan Kosong");
		}
		if (data.getPostType() == null) {
			throw new RuntimeException("Tipe Postingan Kosong");
		}
		if (data.getCategory() == null) {
			throw new RuntimeException("Kategori Kosong");
		}
	}

	public Post insertPost(final Post data) {
		Post postInsert = null;
		try {
			ConnHandler.begin();
			valIdNull(data);
			valNotNullable(data);
			User user = userDao.getRefById(principalService.getAuthPrincipal());
			data.setUser(user);
			postInsert = postDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postInsert;
	}

	public PojoInsertRes insertPost(final PojoPostInsertReq data) {
		final Post post = new Post();
		PostType type = postTypeDao.getByPostTypeCode(com.lawencon.community.constant.PostType.NORMAL.getTypeCode())
				.get();
		if (!data.getPolling().isEmpty()) {
			type = postTypeDao.getByPostTypeCode(com.lawencon.community.constant.PostType.POLLING.getTypeCode()).get();

		}
		if (data.getIsPremium()) {
			type = postTypeDao.getByPostTypeCode(com.lawencon.community.constant.PostType.PREMIUM.getTypeCode()).get();
		}
		Post postInsert = null;

		post.setPostTitle(data.getPostTitle());
		post.setPostContent(data.getPostContent());

		final PostType postType = postTypeDao.getRefById(type.getId());
		post.setPostType(postType);

		final Category category = categoryDao.getRefById(data.getCategoryId());
		post.setCategory(category);

		post.setIsActive(true);

		postInsert = insertPost(post);

		if (data.getPolling() != null) {
			for (int i = 0; i < data.getPolling().size(); i++) {
				final PollingChoice pollingChoice = new PollingChoice();
				final PojoPollingChoiceInsertReq choice = data.getPolling().get(i);
				pollingChoice.setChoiceContent(choice.getChoiceContent());
				pollingChoice.setPost(postInsert);
				pollingService.insertPollingChoice(pollingChoice);
			}
		}

		if (data.getFile() != null) {
			for (int i = 0; i < data.getFile().size(); i++) {
				final PojoFileInsertReq pojo = data.getFile().get(i);
				final PostFile postFile = new PostFile();
				final File file = new File();
				file.setFileName(pojo.getFileName());
				file.setFileContent(pojo.getFileContent());
				file.setFileExtension(pojo.getFileExtension());
				postFile.setPost(postInsert);
				postFile.setFile(file);
				insertPostFile(postFile);
			}
		}

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(postInsert.getId());
		pojoInsertRes.setMessage("Berhasil Menambah Data");
		return pojoInsertRes;
	}

	public Post updatePost(final Post data) {
		Post postUpdate = null;
		valIdNotNull(data);
		try {
			ConnHandler.begin();
			postUpdate = postDao.update(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postUpdate;
	}

	public PojoUpdateRes updatePost(final PojoPostUpdateReq data) {
		Post postUpdate = null;
		postUpdate = getByIdAndDetach(data.getId());
		final Post post = postUpdate;

		if (data.getPostTitle() != null) {
			post.setPostTitle(data.getPostTitle());
		}

		if (data.getPostContent() != null) {
			post.setPostContent(data.getPostContent());
		}

		postUpdate = updatePost(post);

		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		pojoUpdateRes.setVer(postUpdate.getVersion());
		pojoUpdateRes.setMessage("Berhasil Mengubah Data");

		return pojoUpdateRes;
	}

	public boolean deleteById(final String id) {
		boolean postDelete = false;

		try {
			ConnHandler.begin();
			postDelete = postDao.deleteById(Post.class, id);
			ConnHandler.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return postDelete;
	}

	public PojoDeleteRes deleteRes(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deleteById(id);
		res.setMessage("Berhasil Menghapus Data");
		return res;
	}

	public Optional<Post> getById(final String id) {
		return postDao.getById(id);
	}

	public Post getByIdAndDetach(final String id) {
		return postDao.getByIdAndDetach(Post.class, id);
	}

	public Post getRefById(final String id) {
		return postDao.getRefById(id);
	}

	public List<Post> getAll() {
		return postDao.getAll();
	}

	public int getTotalPost() {
		return postDao.getTotalPost();
	}

	public List<PojoPostGetAllRes> getAllPost(final int limit, final int offset) {
		final List<PojoPostGetAllRes> listPojoPost = new ArrayList<>();
		final List<Post> listPost = postDao.getAllPost(limit, offset);

		for (int i = 0; i < listPost.size(); i++) {
			Boolean isPremium = false;
			List<PojoPollingAnswerGetCountRes> pollingAnswers = new ArrayList<>();
			final PojoPostGetAllRes pojoPost = new PojoPostGetAllRes();
			final PostType postType = postTypeDao.getRefById(listPost.get(i).getPostType().getId());

			if (postType.getTypeCode().equals(com.lawencon.community.constant.PostType.PREMIUM.getTypeCode())) {
				isPremium = true;
				pollingAnswers = getAnswerByChoiceId(listPost.get(i).getId());
			}
			final List<String> postFileId = new ArrayList<>();
			final List<PojoPollingChoiceGetAllRes> pollingPojos = new ArrayList<>();
			final List<PojoPostDetailGetAllRes> pojoDetails = new ArrayList<>();
			final List<PostFile> postFiles = postFileDao.getAllPostFile(listPost.get(i).getId());
			final List<PollingChoice> pollingChoices = pollingChoiceDao.getChoiceByPost(listPost.get(i).getId());
			final Profile userProfile = profileDao.getRefById(listPost.get(i).getUser().getProfile().getId());
			final Optional<PollingAnswer> pollingAnswer = pollingAnswerDao.getByPostIdAndUserId(listPost.get(i).getId(),
					principalService.getAuthPrincipal());
			PojoPostLikeRes pojoLike = null;
			PojoPostBookmarkRes pojoBookmark = null;
			PojoPollingAnswerRes pojoAnswer = null;
			final List<PostDetail> postDetails = getPostDetailByPostId(listPost.get(i).getId());
			if (postFiles.size() > 0) {
				for (int j = 0; j < postFiles.size(); j++) {
					final PostFile postFile = postFiles.get(j);
					final String fileId = postFile.getFile().getId();
					postFileId.add(fileId);
				}
			}
			if (pollingChoices.size() > 0) {
				for (int j = 0; j < pollingChoices.size(); j++) {
					final PollingChoice pollingChoice = pollingChoices.get(j);
					final PojoPollingChoiceGetAllRes pollingPojo = new PojoPollingChoiceGetAllRes();
					pollingPojo.setPollingChoiceId(pollingChoice.getId());
					pollingPojo.setChoiceContent(pollingChoice.getChoiceContent());
					pollingPojo.setVer(pollingChoice.getVersion());

					pollingPojos.add(pollingPojo);
				}
			}
			if (pollingAnswer.isPresent()) {
				final PollingAnswer answer = pollingAnswer.get();
				pojoAnswer = new PojoPollingAnswerRes();
				pojoAnswer.setId(answer.getId());
				pojoAnswer.setChoiceId(answer.getPollingChoice().getId());
			}
			final Optional<UserLike> postLike = userLikeDao.getLikeByPostId(listPost.get(i).getId(),
					principalService.getAuthPrincipal());
			final Optional<UserBookmark> postBookmark = userBookmarkDao.getBookmarkByPostId(listPost.get(i).getId(),
					principalService.getAuthPrincipal());
			if (postLike.isPresent()) {
				final UserLike userLike = postLike.get();
				pojoLike = new PojoPostLikeRes();
				pojoLike.setId(userLike.getId());
				pojoLike.setStatus(true);
			}
			if (postBookmark.isPresent()) {
				pojoBookmark = new PojoPostBookmarkRes();
				pojoBookmark.setId(postBookmark.get().getId());
				pojoBookmark.setStatus(true);
			}
			pojoPost.setId(listPost.get(i).getId());
			if(userProfile.getFile() != null) {				
				pojoPost.setUserFileId(userProfile.getFile().getId());
			}
			pojoPost.setFullName(listPost.get(i).getUser().getProfile().getFullName());
			pojoPost.setPostTitle(listPost.get(i).getPostTitle());
			pojoPost.setPostContent(listPost.get(i).getPostContent());
			pojoPost.setCategoryName(listPost.get(i).getCategory().getCategoryName());
			pojoPost.setPostTypeId(listPost.get(i).getPostType().getId());
			pojoPost.setVer(listPost.get(i).getVersion());
			pojoPost.setPostDetail(pojoDetails);
			pojoPost.setDetailCount(postDetails.size());
			pojoPost.setIsLiked(pojoLike);
			pojoPost.setIsBookmarked(pojoBookmark);
			pojoPost.setLikeCount(userLikeDao.getCount(listPost.get(i).getId()));
			pojoPost.setPostedAt(listPost.get(i).getCreatedAt());
			pojoPost.setFileId(postFileId);
			pojoPost.setPollingChoice(pollingPojos);
			pojoPost.setIsAnswered(pojoAnswer);
			pojoPost.setIsPremium(isPremium);
			pojoPost.setPollingAnswer(pollingAnswers);

			listPojoPost.add(pojoPost);
		}

		return listPojoPost;
	}

	public PojoPostGetAllRes getPostById(final String id) {
		final Post post = postDao.getRefById(id);

		final PojoPostGetAllRes pojoPost = new PojoPostGetAllRes();
		pojoPost.setId(post.getId());
		pojoPost.setUserFileId(post.getUser().getProfile().getFile().getId());
		pojoPost.setFullName(post.getUser().getProfile().getFullName());
		pojoPost.setPostTitle(post.getPostTitle());
		pojoPost.setPostContent(post.getPostContent());
		pojoPost.setCategoryName(post.getCategory().getCategoryName());
		pojoPost.setVer(post.getVersion());

		return pojoPost;
	}

	public PojoPostGetAllResData getPost(final int limit, final int offset) {
		final List<PojoPostGetAllRes> listPojoPost = new ArrayList<>();
		final List<Post> listPost = postDao.getAllPost(limit, offset);

		for (int i = 0; i < listPost.size(); i++) {
			final PojoPostGetAllRes pojoPost = new PojoPostGetAllRes();
			final Post currentPost = listPost.get(i);

			pojoPost.setId(currentPost.getId());
			pojoPost.setUserFileId(null);
			pojoPost.setFullName(currentPost.getUser().getProfile().getFullName());
			pojoPost.setPostTitle(currentPost.getPostTitle());
			pojoPost.setPostContent(currentPost.getPostContent());
			pojoPost.setCategoryName(currentPost.getCategory().getCategoryName());
			pojoPost.setPostTypeId(currentPost.getPostType().getId());
			pojoPost.setVer(currentPost.getVersion());

			listPojoPost.add(pojoPost);
		}

		final PojoPostGetAllResData pojoPostData = new PojoPostGetAllResData();
		pojoPostData.setData(listPojoPost);
		pojoPostData.setTotal(getTotalPost());

		return pojoPostData;
	}

	private void valIdNull(final PostFile data) {
		if (data.getId() != null) {
			throw new RuntimeException("ID harus kosong");
		}
	}

	private void valNotNullable(final PostFile data) {
		if (data.getPost() == null) {
			throw new RuntimeException("Postingan Kosong");
		}
		if (data.getFile() == null) {
			throw new RuntimeException("File Kosong");
		}
	}

	public PostFile insertPostFile(final PostFile data) {
		PostFile postFileInsert = null;
		valIdNull(data);
		valNotNullable(data);
		ConnHandler.begin();
		final File file = fileDao.insert(data.getFile());
		postFileInsert = data;
		postFileInsert.setFile(file);
		postFileInsert = postFileDao.insert(postFileInsert);
		ConnHandler.commit();
		return postFileInsert;
	}

	public PojoInsertRes insertPostFile(final PojoPostFileInsertListReq data) {
		PojoInsertRes pojoInsertRes = null;
		for (int i = 0; i < data.getData().size(); i++) {
			final PostFile postFile = new PostFile();

			final Post post = postDao.getRefById(data.getData().get(i).getPostId());
			postFile.setPost(post);

			final File file = new File();
			file.setFileName(data.getData().get(i).getFile().getFileName());
			file.setFileExtension(data.getData().get(i).getFile().getFileExtension());
			file.setFileContent(data.getData().get(i).getFile().getFileContent());
			file.setIsActive(true);
			ConnHandler.begin();
			final File fileInsert = fileDao.insert(file);
			ConnHandler.commit();
			postFile.setFile(fileInsert);

			postFile.setIsActive(true);

			final PostFile postFileInsert = insertPostFile(postFile);

			pojoInsertRes = new PojoInsertRes();
			pojoInsertRes.setId(postFileInsert.getId());
			pojoInsertRes.setMessage("Berhasil Menambah Data");

		}
		return pojoInsertRes;
	}

	public boolean deletePostFileById(final String id) {
		boolean postFileDelete = false;

		try {
			ConnHandler.begin();
			postFileDelete = postFileDao.deleteById(PostFile.class, id);
			ConnHandler.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return postFileDelete;
	}

	public PojoDeleteRes deletePostFileRes(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deletePostFileById(id);
		res.setMessage("Berhasil Menghapus Data");
		return res;
	}

	private void valIdNull(final UserLike data) {
		if (data.getId() != null) {
			throw new RuntimeException("ID harus kosong");
		}
	}

	private void valNotNullable(final UserLike data) {
		if (data.getPost() == null) {
			throw new RuntimeException("Postingan Kosong");
		}
		if (data.getUser() == null) {
			throw new RuntimeException("User Kosong");
		}
	}

	public UserLike insertUserLike(final UserLike data) {
		UserLike userLikeInsert = null;
		try {
			ConnHandler.begin();
			valIdNull(data);
			valNotNullable(data);
			User user = userDao.getRefById(principalService.getAuthPrincipal());
			data.setUser(user);
			userLikeInsert = userLikeDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userLikeInsert;
	}

	public PojoInsertRes insertUserLike(final PojoUserLikeInsertReq data) {
		final UserLike userLike = new UserLike();

		final User user = userDao.getRefById(principalService.getAuthPrincipal());
		userLike.setUser(user);

		final Post post = postDao.getRefById(data.getPostId());
		userLike.setPost(post);

		userLike.setIsActive(true);

		final UserLike userLikeInsert = insertUserLike(userLike);

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(userLikeInsert.getId());
		pojoInsertRes.setMessage("Berhasil Menyukai Postingan");
		return pojoInsertRes;
	}

	public boolean deleteUserLikeById(final String postLikeId) {
		boolean userLikeDelete = false;

		try {
			ConnHandler.begin();
			userLikeDelete = userLikeDao.delete(postLikeId);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userLikeDelete;
	}

	public List<PojoUserLikeGetAllRes> getLikeByUser(final String postId) {
		final List<PojoUserLikeGetAllRes> listPojoUserLike = new ArrayList<>();

		final List<UserLike> listUserLike = userLikeDao.getLikeByUserId(principalService.getAuthPrincipal());
		for (int i = 0; i < listUserLike.size(); i++) {
			final PojoUserLikeGetAllRes pojoUserLike = new PojoUserLikeGetAllRes();

			pojoUserLike.setId(listUserLike.get(i).getId());
			pojoUserLike.setUserId(listUserLike.get(i).getUser().getId());
			pojoUserLike.setPostId(listUserLike.get(i).getPost().getId());

			pojoUserLike.setCountedLike(userLikeDao.getCount(postId));

			listPojoUserLike.add(pojoUserLike);
		}

		return listPojoUserLike;
	}

	public PojoDeleteRes deleteUserLikeRes(final String postLikeId) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deleteUserLikeById(postLikeId);
		res.setMessage("Berhasil Membatalkan");

		return res;
	}

	public PojoDeleteRes deleteBookmark(final String postBookmarkId) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deleteBookmarkById(postBookmarkId);
		res.setMessage("Berhasil Membatalkan");
		return res;
	}

	private void valIdNull(final UserBookmark data) {
		if (data.getId() != null) {
			throw new RuntimeException("ID harus kosong");
		}
	}

	private void valNotNullable(final UserBookmark data) {
		if (data.getPost() == null) {
			throw new RuntimeException("Postingan Kosong");
		}
		if (data.getUser() == null) {
			throw new RuntimeException("User Kosong");
		}
	}

	public UserBookmark insert(final UserBookmark data) {
		UserBookmark userBookmarkInsert = null;
		try {
			ConnHandler.begin();
			valIdNull(data);
			valNotNullable(data);
			User user = userDao.getRefById(principalService.getAuthPrincipal());
			data.setUser(user);
			userBookmarkInsert = userBookmarkDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userBookmarkInsert;
	}

	public List<UserBookmark> getAllByUser(final String userId) {
		return userBookmarkDao.getAllByUser(userId);
	}

	public boolean deleteBookmarkById(final String id) {
		boolean userBookmarkDelete = false;

		try {
			ConnHandler.begin();
			userBookmarkDelete = userBookmarkDao.deleteById(UserBookmark.class, id);
			ConnHandler.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userBookmarkDelete;
	}

	public PojoInsertRes insertUserBookmark(final PojoUserBookmarkInsertReq data) {
		final UserBookmark userBookmark = new UserBookmark();

		final User user = userDao.getRefById(principalService.getAuthPrincipal());

		userBookmark.setUser(user);

		final Post post = postDao.getByIdRef(Post.class, data.getPostId());
		post.setId(data.getPostId());
		userBookmark.setPost(post);

		UserBookmark userBookmarkInsert = null;
		userBookmarkInsert = insert(userBookmark);

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(userBookmarkInsert.getId());
		pojoInsertRes.setMessage("Berhasil Bookmark Post");
		return pojoInsertRes;
	}

	public List<PojoUserBookmarkGetAllRes> getAllByUserRes() {
		final List<PojoUserBookmarkGetAllRes> userBookmarkGetAllRes = new ArrayList<>();
		final List<UserBookmark> bookmarks = userBookmarkDao.getAllByUser(principalService.getAuthPrincipal());

		for (int i = 0; i < bookmarks.size(); i++) {
			final PojoUserBookmarkGetAllRes pojoBookmark = new PojoUserBookmarkGetAllRes();
			final UserBookmark userBookmark = bookmarks.get(i);
			ConnHandler.getManager().detach(userBookmark);
			pojoBookmark.setId(userBookmark.getId());
			pojoBookmark.setPostId(bookmarks.get(i).getPost().getId());
			pojoBookmark.setUserId(bookmarks.get(i).getUser().getId());

			userBookmarkGetAllRes.add(pojoBookmark);
		}
		return userBookmarkGetAllRes;
	}

	public List<PojoUserBookmarkGetAllByUser> getAllByUserBookmark(final Integer limit, final Integer offset) {
		final List<PojoUserBookmarkGetAllByUser> userBookmarkGetAllRes = new ArrayList<>();
		final List<UserBookmark> bookmarks = userBookmarkDao.getAllByUserId(principalService.getAuthPrincipal(), limit,
				offset);

		for (int i = 0; i < bookmarks.size(); i++) {
			Boolean isPremium = false;
			List<PojoPollingAnswerGetCountRes> pollingAnswers = new ArrayList<>();
			final PojoUserBookmarkGetAllByUser pojoUserBookmark = new PojoUserBookmarkGetAllByUser();
			final PostType postType = postTypeDao.getRefById(bookmarks.get(i).getPost().getPostType().getId());
			if (postType.getTypeCode().equals(com.lawencon.community.constant.PostType.POLLING.getTypeCode())) {
				pollingAnswers = getAnswerByChoiceId(bookmarks.get(i).getPost().getId());
			}
			if (postType.getTypeCode().equals(com.lawencon.community.constant.PostType.PREMIUM.getTypeCode())) {
				isPremium = true;
				pollingAnswers = getAnswerByChoiceId(bookmarks.get(i).getPost().getId());
			}
			final List<String> postFileId = new ArrayList<>();
			final List<PojoPollingChoiceGetAllRes> pollingPojos = new ArrayList<>();
			final List<PojoPostDetailGetAllRes> pojoDetails = new ArrayList<>();

			final List<PostFile> postFiles = postFileDao.getAllPostFile(bookmarks.get(i).getPost().getId());
			final List<PollingChoice> pollingChoices = pollingChoiceDao
					.getChoiceByPost(bookmarks.get(i).getPost().getId());
			final Optional<PollingAnswer> pollingAnswer = pollingAnswerDao
					.getByPostIdAndUserId(bookmarks.get(i).getPost().getId(), principalService.getAuthPrincipal());
			PojoPostLikeRes pojoLike = null;
			PojoPostBookmarkRes pojoBookmark = null;
			PojoPollingAnswerRes pojoAnswer = null;

			final List<PostDetail> postDetails = getPostDetailByPostId(bookmarks.get(i).getPost().getId());
			if (postFiles.size() > 0) {
				for (int j = 0; j < postFiles.size(); j++) {
					final PostFile postFile = postFiles.get(j);
					final String fileId = postFile.getFile().getId();
					postFileId.add(fileId);
				}
			}
			if (pollingChoices.size() > 0) {
				for (int j = 0; j < pollingChoices.size(); j++) {
					final PollingChoice pollingChoice = pollingChoices.get(j);
					final PojoPollingChoiceGetAllRes pollingPojo = new PojoPollingChoiceGetAllRes();
					pollingPojo.setPollingChoiceId(pollingChoice.getId());
					pollingPojo.setChoiceContent(pollingChoice.getChoiceContent());
					pollingPojo.setVer(pollingChoice.getVersion());

					pollingPojos.add(pollingPojo);
				}
			}
			if (pollingAnswer.isPresent()) {
				final PollingAnswer answer = pollingAnswer.get();
				pojoAnswer = new PojoPollingAnswerRes();
				pojoAnswer.setId(answer.getId());
				pojoAnswer.setChoiceId(answer.getPollingChoice().getId());
			}
			final Optional<UserLike> postLike = userLikeDao.getLikeByPostId(bookmarks.get(i).getPost().getId(),
					principalService.getAuthPrincipal());
			final Optional<UserBookmark> postBookmark = userBookmarkDao
					.getBookmarkByPostId(bookmarks.get(i).getPost().getId(), principalService.getAuthPrincipal());
			if (postLike.isPresent()) {
				final UserLike userLike = postLike.get();
				pojoLike = new PojoPostLikeRes();
				pojoLike.setId(userLike.getId());
				pojoLike.setStatus(true);
			}
			if (postBookmark.isPresent()) {
				pojoBookmark = new PojoPostBookmarkRes();
				pojoBookmark.setId(postBookmark.get().getId());
				pojoBookmark.setStatus(false);
			}

			pojoUserBookmark.setId(bookmarks.get(i).getId());
			pojoUserBookmark.setUserFileId(bookmarks.get(i).getPost().getUser().getProfile().getFile().getId());
			pojoUserBookmark.setFullName(bookmarks.get(i).getPost().getUser().getProfile().getFullName());
			pojoUserBookmark.setPostTitle(bookmarks.get(i).getPost().getPostTitle());
			pojoUserBookmark.setPostContent(bookmarks.get(i).getPost().getPostContent());
			pojoUserBookmark.setCategoryName(bookmarks.get(i).getPost().getCategory().getCategoryName());
			pojoUserBookmark.setPostTypeId(bookmarks.get(i).getPost().getPostType().getId());
			pojoUserBookmark.setVer(bookmarks.get(i).getPost().getVersion());
			pojoUserBookmark.setPostDetail(pojoDetails);
			pojoUserBookmark.setDetailCount(postDetails.size());
			pojoUserBookmark.setIsLiked(pojoLike);
			pojoUserBookmark.setIsBookmarked(pojoBookmark);
			pojoUserBookmark.setLikeCount(userLikeDao.getCount(bookmarks.get(i).getPost().getId()));
			pojoUserBookmark.setPostedAt(bookmarks.get(i).getPost().getCreatedAt());
			pojoUserBookmark.setFileId(postFileId);
			pojoUserBookmark.setPollingChoice(pollingPojos);
			pojoUserBookmark.setIsAnswered(pojoAnswer);
			pojoUserBookmark.setIsPremium(isPremium);
			pojoUserBookmark.setPollingAnswer(pollingAnswers);

			userBookmarkGetAllRes.add(pojoUserBookmark);
		}

		return userBookmarkGetAllRes;
	}

	public List<PojoUserLikeGetAllByUser> getAllByUserLike(final Integer limit, final Integer offset) {
		final List<PojoUserLikeGetAllByUser> userLikeGetAllRes = new ArrayList<>();
		final List<UserLike> likes = userLikeDao.getLikeByUser(principalService.getAuthPrincipal(), limit, offset);

		for (int i = 0; i < likes.size(); i++) {
			Boolean isPremium = false;
			List<PojoPollingAnswerGetCountRes> pollingAnswers = new ArrayList<>();
			final PojoUserLikeGetAllByUser pojoUserLike = new PojoUserLikeGetAllByUser();
			final PostType postType = postTypeDao.getRefById(likes.get(i).getPost().getPostType().getId());
			if (postType.getTypeCode().equals(com.lawencon.community.constant.PostType.POLLING.getTypeCode())) {
				pollingAnswers = getAnswerByChoiceId(likes.get(i).getPost().getId());
			}
			if (postType.getTypeCode().equals(com.lawencon.community.constant.PostType.PREMIUM.getTypeCode())) {
				isPremium = true;
				pollingAnswers = getAnswerByChoiceId(likes.get(i).getPost().getId());
			}
			final List<String> postFileId = new ArrayList<>();
			final List<PojoPollingChoiceGetAllRes> pollingPojos = new ArrayList<>();
			final List<PojoPostDetailGetAllRes> pojoDetails = new ArrayList<>();

			final List<PostFile> postFiles = postFileDao.getAllPostFile(likes.get(i).getPost().getId());
			final List<PollingChoice> pollingChoices = pollingChoiceDao.getChoiceByPost(likes.get(i).getPost().getId());
			final Optional<PollingAnswer> pollingAnswer = pollingAnswerDao
					.getByPostIdAndUserId(likes.get(i).getPost().getId(), principalService.getAuthPrincipal());
			PojoPostLikeRes pojoLike = null;
			PojoPostBookmarkRes pojoBookmark = null;
			PojoPollingAnswerRes pojoAnswer = null;

			final List<PostDetail> postDetails = getPostDetailByPostId(likes.get(i).getPost().getId());

			if (postFiles.size() > 0) {
				for (int j = 0; j < postFiles.size(); j++) {
					final PostFile postFile = postFiles.get(j);
					final String fileId = postFile.getFile().getId();
					postFileId.add(fileId);
				}
			}
			if (pollingChoices.size() > 0) {
				for (int j = 0; j < pollingChoices.size(); j++) {
					final PollingChoice pollingChoice = pollingChoices.get(j);
					final PojoPollingChoiceGetAllRes pollingPojo = new PojoPollingChoiceGetAllRes();
					pollingPojo.setPollingChoiceId(pollingChoice.getId());
					pollingPojo.setChoiceContent(pollingChoice.getChoiceContent());
					pollingPojo.setVer(pollingChoice.getVersion());

					pollingPojos.add(pollingPojo);
				}
			}
			if (pollingAnswer.isPresent()) {
				final PollingAnswer answer = pollingAnswer.get();
				pojoAnswer = new PojoPollingAnswerRes();
				pojoAnswer.setId(answer.getId());
				pojoAnswer.setChoiceId(answer.getPollingChoice().getId());
			}
			final Optional<UserLike> postLike = userLikeDao.getLikeByPostId(likes.get(i).getPost().getId(),
					principalService.getAuthPrincipal());
			final Optional<UserBookmark> postBookmark = userBookmarkDao
					.getBookmarkByPostId(likes.get(i).getPost().getId(), principalService.getAuthPrincipal());
			if (postLike.isPresent()) {
				final UserLike userLike = postLike.get();
				pojoLike = new PojoPostLikeRes();
				pojoLike.setId(userLike.getId());
				pojoLike.setStatus(true);
			}
			if (postBookmark.isPresent()) {
				pojoBookmark = new PojoPostBookmarkRes();
				pojoBookmark.setId(postBookmark.get().getId());
				pojoBookmark.setStatus(false);
			}

			pojoUserLike.setId(likes.get(i).getId());
			pojoUserLike.setUserFileId(likes.get(i).getPost().getUser().getProfile().getFile().getId());
			pojoUserLike.setFullName(likes.get(i).getPost().getUser().getProfile().getFullName());
			pojoUserLike.setPostTitle(likes.get(i).getPost().getPostTitle());
			pojoUserLike.setPostContent(likes.get(i).getPost().getPostContent());
			pojoUserLike.setCategoryName(likes.get(i).getPost().getCategory().getCategoryName());
			pojoUserLike.setPostTypeId(likes.get(i).getPost().getPostType().getId());
			pojoUserLike.setVer(likes.get(i).getPost().getVersion());
			pojoUserLike.setPostDetail(pojoDetails);
			pojoUserLike.setDetailCount(postDetails.size());
			pojoUserLike.setIsLiked(pojoLike);
			pojoUserLike.setIsBookmarked(pojoBookmark);
			pojoUserLike.setLikeCount(userLikeDao.getCount(likes.get(i).getPost().getId()));
			pojoUserLike.setPostedAt(likes.get(i).getPost().getCreatedAt());
			pojoUserLike.setFileId(postFileId);
			pojoUserLike.setPollingChoice(pollingPojos);
			pojoUserLike.setIsAnswered(pojoAnswer);
			pojoUserLike.setIsPremium(isPremium);
			pojoUserLike.setPollingAnswer(pollingAnswers);

			userLikeGetAllRes.add(pojoUserLike);
		}

		return userLikeGetAllRes;
	}

	public PojoUserBookmarkGetAllRes getCountBookmark(final String postId) {
		final PojoUserBookmarkGetAllRes bookmark = new PojoUserBookmarkGetAllRes();

		bookmark.setUserId(principalService.getAuthPrincipal());
		bookmark.setPostId(postId);
		bookmark.setCountedBookmark(userBookmarkDao.getCount(postId));

		return bookmark;
	}

	private void valIdNull(final PostDetail data) {
		if (data.getId() != null) {
			throw new RuntimeException("ID harus kosong");
		}
	}

	private void valIdNotNull(final PostDetail data) {
		if (data.getId() == null) {
			throw new RuntimeException("ID kosong");
		}
	}

	private void valNotNullable(final PostDetail data) {
		if (data.getDetailContent() == null) {
			throw new RuntimeException("Komentar Kosong");
		}
		if (data.getPost() == null) {
			throw new RuntimeException("Postingan Kosong");
		}
	}

	public PostDetail insert(final PostDetail data) {
		PostDetail postDetailInsert = null;
		try {
			valIdNull(data);
			valNotNullable(data);
			final User user = userDao.getById(principalService.getAuthPrincipal()).get();
			final PostDetail postDetail = data;
			postDetail.setUser(user);
			ConnHandler.begin();
			postDetailInsert = postDetailDao.insert(postDetail);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postDetailInsert;
	}

	public PostDetail update(final PostDetail data) {
		PostDetail postDetailUpdate = null;
		valIdNotNull(data);
		try {
			ConnHandler.begin();
			postDetailUpdate = postDetailDao.update(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return postDetailUpdate;
	}

	public Optional<PostDetail> getPostDetailById(final String id) {
		return postDetailDao.getById(id);
	}

	public List<PostDetail> getPostDetailByPostId(final String postId) {
		return postDetailDao.getByPostId(postId);
	}

	public List<PostDetail> getAllPostDetail() {
		return postDetailDao.getAll();
	}

	public boolean deletePostDetailById(final String id) {
		boolean postDetailDelete = false;

		try {
			ConnHandler.begin();
			postDetailDelete = postDetailDao.deleteById(PostDetail.class, id);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postDetailDelete;
	}

	public PostDetail getDetailByIdAndDetach(final String id) {
		return postDetailDao.getByIdAndDetach(PostDetail.class, id);
	}

	public PojoInsertRes insertDetail(final PojoPostDetailInsertReq data) {
		final PostDetail postDetail = new PostDetail();
		final Post post = getRefById(data.getPostId());
		postDetail.setPost(post);
		postDetail.setDetailContent(data.getDetailContent());

		PostDetail postDetailInsert = null;
		postDetailInsert = insert(postDetail);

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(postDetailInsert.getId());
		pojoInsertRes.setMessage("Terkirim");
		return pojoInsertRes;
	}

	public PojoUpdateRes update(final PojoPostDetailUpdateReq data) {
		final PostDetail postDetail = getDetailByIdAndDetach(data.getId());

		if (data.getDetailContent() != null) {
			postDetail.setDetailContent(data.getDetailContent());
		}

		if (data.getVer() != null) {
			postDetail.setVersion(data.getVer());
		}

		if (data.getPostId() != null) {
			final Post post = postDao.getByIdRef(Post.class, data.getPostId());
			post.setId(data.getPostId());
			postDetail.setPost(post);
		}

		final PostDetail postDetailUpdate = update(postDetail);
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		pojoUpdateRes.setVer(postDetailUpdate.getVersion());
		pojoUpdateRes.setMessage("Updated");
		return pojoUpdateRes;
	}

	public List<PojoPostDetailGetAllRes> getAllPostDetailRes() {
		final List<PojoPostDetailGetAllRes> getAllRes = new ArrayList<>();
		final List<PostDetail> posDetails = getAllPostDetail();

		for (int i = 0; i < posDetails.size(); i++) {
			final PojoPostDetailGetAllRes detailGetAllRes = new PojoPostDetailGetAllRes();
			final PostDetail detail = posDetails.get(i);

			ConnHandler.getManager().detach(detail);
			detailGetAllRes.setId(detail.getId());
			detailGetAllRes.setDetailContent(posDetails.get(i).getDetailContent());
			getAllRes.add(detailGetAllRes);
		}
		return getAllRes;
	}

	public List<PojoPostDetailGetAllRes> getDetailByPostId(final String postId) {
		final List<PojoPostDetailGetAllRes> pojos = new ArrayList<>();
		final List<PostDetail> details = getPostDetailByPostId(postId);
		for (int i = 0; i < details.size(); i++) {
			final PojoPostDetailGetAllRes pojo = new PojoPostDetailGetAllRes();
			final PostDetail detail = details.get(i);
			final User user = userDao.getRefById(detail.getUser().getId());
			final Profile profile = profileDao.getRefById(user.getProfile().getId());
			pojo.setId(detail.getId());
			pojo.setFullName(profile.getFullName());
			if(profile.getFile() != null) {				
				pojo.setUserFileId(profile.getFile().getId());
			}
			pojo.setPostedAt(detail.getCreatedAt());
			pojo.setVer(detail.getVersion());
			pojo.setDetailContent(detail.getDetailContent());
			pojos.add(pojo);
		}
		return pojos;
	}

	public List<PojoPostDetailGetAllRes> getAllDetailByPostId(final String postId, final Integer limit,
			final Integer offset) {
		final List<PojoPostDetailGetAllRes> postDetailGetAllRes = new ArrayList<>();
		final List<PostDetail> postDetails = postDetailDao.getByPostId(postId, limit, offset);

		for (int i = 0; i < postDetails.size(); i++) {
			final PojoPostDetailGetAllRes detailGetAllRes = new PojoPostDetailGetAllRes();
			final PostDetail postDetail = postDetails.get(i);
			final User user = userDao.getRefById(postDetail.getUser().getId());
			final Profile profile = profileDao.getRefById(user.getProfile().getId());
			detailGetAllRes.setId(postDetail.getId());
			detailGetAllRes.setFullName(profile.getFullName());
			if(profile.getFile() != null) {				
				detailGetAllRes.setUserFileId(profile.getFile().getId());
			}
			detailGetAllRes.setPostedAt(postDetail.getCreatedAt());
			detailGetAllRes.setVer(postDetail.getVersion());
			detailGetAllRes.setDetailContent(postDetail.getDetailContent());
			postDetailGetAllRes.add(detailGetAllRes);
		}
		return postDetailGetAllRes;
	}

	public PojoDeleteRes deletePostDetail(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deletePostDetailById(id);
		res.setMessage("Berhasil Dihapus");

		return res;
	}

	public PojoInsertRes insertPollingAnswer(final String pollingChoiceId) {
		PollingAnswer pollingAnwerInsert = null;
		final PojoInsertRes pojo = new PojoInsertRes();
		final PollingAnswer pollingAnswer = new PollingAnswer();
		final PollingChoice pollingChoice = pollingChoiceDao.getRefById(pollingChoiceId);
		final Post post = pollingChoice.getPost();
		pollingAnswer.setPollingChoice(pollingChoice);
		pollingAnswer.setUser(userDao.getRefById(principalService.getAuthPrincipal()));
		pollingAnswer.setPost(post);
		pollingAnswer.setIsActive(true);
		ConnHandler.begin();
		pollingAnwerInsert = pollingAnswerDao.insert(pollingAnswer);
		ConnHandler.commit();
		pojo.setId(pollingAnwerInsert.getId());
		pojo.setMessage("Berhasil menjawab polling");
		return pojo;
	}

	public PojoDeleteRes deletePollingAnswer(final String pollingAnswerId) {
		final PojoDeleteRes deleteRes = new PojoDeleteRes();

		ConnHandler.begin();
		boolean delete = pollingAnswerDao.delete(pollingAnswerId);
		ConnHandler.commit();
		if (delete) {
			deleteRes.setMessage("Berhasil membatalkan jawaban polling");
		}
		return deleteRes;
	}

	public List<PojoPollingAnswerGetCountRes> getAnswerByChoiceId(final String postId) {
		final List<PojoPollingAnswerGetCountRes> listAnswer = pollingAnswerDao.getCountByChoiceId(postId);
		final Long totalChoice = pollingAnswerDao.getCount(postId);
		for (int i = 0; i < listAnswer.size(); i++) {
			final PojoPollingAnswerGetCountRes pojoAnswerCount = listAnswer.get(i);
			float percent = (listAnswer.get(i).getCountPollAnswer() * (100 / totalChoice));
			pojoAnswerCount.setChoiceContent(listAnswer.get(i).getChoiceContent());
			pojoAnswerCount.setPercent(percent);
		}
		return listAnswer;
	}
}