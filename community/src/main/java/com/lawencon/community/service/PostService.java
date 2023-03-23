package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.dao.FileDao;
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
import com.lawencon.community.pojo.pollingchoice.PojoPollingChoiceInsertReq;
import com.lawencon.community.pojo.post.PojoPostGetAllRes;
import com.lawencon.community.pojo.post.PojoPostGetAllResData;
import com.lawencon.community.pojo.post.PojoPostInsertReq;
import com.lawencon.community.pojo.post.PojoPostUpdateReq;
import com.lawencon.community.pojo.postdetail.PojoPostDetailGetAllRes;
import com.lawencon.community.pojo.postdetail.PojoPostDetailGetByPostIdRes;
import com.lawencon.community.pojo.postdetail.PojoPostDetailInsertReq;
import com.lawencon.community.pojo.postdetail.PojoPostDetailUpdateReq;
import com.lawencon.community.pojo.postfile.PojoPostFileInsertListReq;
import com.lawencon.community.pojo.userbookmark.PojoPostBookmarkRes;
import com.lawencon.community.pojo.userbookmark.PojoUserBookmarkGetAllRes;
import com.lawencon.community.pojo.userbookmark.PojoUserBookmarkInsertReq;
import com.lawencon.community.pojo.userlike.PojoPostLikeRes;
import com.lawencon.community.pojo.userlike.PojoUserLikeGetAllRes;
import com.lawencon.community.pojo.userlike.PojoUserLikeInsertReq;
import com.lawencon.security.principal.PrincipalService;

@Service
public class PostService {
	private final PostDao postDao;
	private final UserDao userDao;
	private final PostTypeDao postTypeDao;
	private final CategoryDao categoryDao;
	private final PostFileDao postFileDao;
	private final UserLikeDao userLikeDao;
	private final UserBookmarkDao userBookmarkDao;
	private final PostDetailDao postDetailDao;
	private final FileDao fileDao;
	private final ProfileDao profileDao;
	private final PollingService pollingService;
	private final PrincipalService principalService;

	public PostService(final PostDao postDao, final PostFileDao postFileDao, final UserLikeDao userLikeDao,
			final UserDao userDao, final PostTypeDao postTypeDao, final CategoryDao categoryDao,
			final UserBookmarkDao userBookmarkDao, final PostDetailDao postDetailDao, final FileDao fileDao,
			final PrincipalService principalService, final PollingService pollingService, final ProfileDao profileDao) {

		this.postDao = postDao;
		this.userDao = userDao;
		this.postTypeDao = postTypeDao;
		this.categoryDao = categoryDao;
		this.postFileDao = postFileDao;
		this.fileDao = fileDao;
		this.profileDao = profileDao;
		this.userLikeDao = userLikeDao;
		this.principalService = principalService;
		this.userBookmarkDao = userBookmarkDao;
		this.postDetailDao = postDetailDao;
		this.pollingService = pollingService;
	}

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
		if (data.getPolling() != null) {
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

		if (data.getPostTypeId() != null) {
			final PostType postType = postTypeDao.getRefById(data.getPostTypeId());
			post.setPostType(postType);
		}

		if (data.getCategoryId() != null) {
			final Category category = categoryDao.getRefById(data.getCategoryId());
			post.setCategory(category);
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
			final PojoPostGetAllRes pojoPost = new PojoPostGetAllRes();
			final List<String> postFileId = new ArrayList<>();
			final List<PojoPostDetailGetAllRes> pojoDetails = new ArrayList<>();
			final List<PostFile> postFiles = postFileDao.getAllPostFile(listPost.get(i).getId());
			PojoPostLikeRes pojoLike = null;
			PojoPostBookmarkRes pojoBookmark = null;
			final List<PostDetail> postDetails = getPostDetailByPostId(listPost.get(i).getId());
			for (int j = 0; i < postDetails.size(); j++) {
				final PostDetail currentDetail = postDetails.get(j);
				final User user = userDao.getRefById(currentDetail.getUser().getId());
				final Profile profile = profileDao.getRefById(user.getProfile().getId());
				final PojoPostDetailGetAllRes pojoDetail = new PojoPostDetailGetAllRes();
				pojoDetail.setId(currentDetail.getId());
				pojoDetail.setFullName(profile.getFullName());
				pojoDetail.setDetailContent(currentDetail.getDetailContent());
				pojoDetail.setVer(currentDetail.getVersion());

				pojoDetails.add(pojoDetail);
			}
			for (int j = 0; j < postFiles.size(); j++) {
				final PostFile postFile = postFiles.get(i);
				final String fileId = postFile.getId();

				postFileId.add(fileId);
			}
			final Optional<UserLike> postLike = userLikeDao.getLikeByPostId(listPost.get(i).getId(),
					principalService.getAuthPrincipal());
			final Optional<UserBookmark> postBookmark = userBookmarkDao.getBookmarkByPostId(listPost.get(i).getId(),
					principalService.getAuthPrincipal());
			if (postLike.isPresent()) {
				pojoLike = new PojoPostLikeRes();
				pojoLike.setId(postLike.get().getId());
				pojoLike.setStatus(true);
			}
			if (postBookmark.isPresent()) {
				pojoBookmark = new PojoPostBookmarkRes();
				pojoBookmark.setId(postBookmark.get().getId());
				pojoBookmark.setStatus(true);
			}
			pojoPost.setId(listPost.get(i).getId());
			pojoPost.setUserId(listPost.get(i).getUser().getId());
			pojoPost.setFullName(listPost.get(i).getUser().getProfile().getFullName());
			pojoPost.setPostTitle(listPost.get(i).getPostTitle());
			pojoPost.setPostContent(listPost.get(i).getPostContent());
			pojoPost.setCategoryName(listPost.get(i).getCategory().getCategoryName());
			pojoPost.setPostTypeId(listPost.get(i).getPostType().getId());
			pojoPost.setVer(listPost.get(i).getVersion());
			pojoPost.setPostDetail(pojoDetails);
			pojoPost.setDetailCount(pojoDetails.size());
			pojoPost.setIsLiked(pojoLike);
			pojoPost.setIsBookmarked(pojoBookmark);
			pojoPost.setPostedAt(listPost.get(i).getCreatedAt());
			pojoPost.setFileId(postFileId);

			listPojoPost.add(pojoPost);
		}

		return listPojoPost;
	}

	public PojoPostGetAllRes getPostById(final String id) {

		final Optional<Post> post = postDao.getById(id);

		final PojoPostGetAllRes pojoPost = new PojoPostGetAllRes();
		pojoPost.setId(post.get().getId());
		pojoPost.setUserId(post.get().getUser().getId());
		pojoPost.setFullName(post.get().getUser().getProfile().getFullName());
		pojoPost.setPostTitle(post.get().getPostTitle());
		pojoPost.setPostContent(post.get().getPostContent());
		pojoPost.setCategoryName(post.get().getCategory().getCategoryName());
		pojoPost.setPostTypeId(post.get().getPostType().getId());
		pojoPost.setVer(post.get().getVersion());

		return pojoPost;
	}

	public PojoPostGetAllResData getPost(final int limit, final int offset) {
		final List<PojoPostGetAllRes> listPojoPost = new ArrayList<>();

		final List<Post> listPost = postDao.getAllPost(limit, offset);

		for (int i = 0; i < listPost.size(); i++) {
			final PojoPostGetAllRes pojoPost = new PojoPostGetAllRes();
			final Post currentPost = listPost.get(i);

			pojoPost.setId(currentPost.getId());
			pojoPost.setUserId(currentPost.getUser().getId());
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

	public PojoInsertRes insert(final PojoUserBookmarkInsertReq data) {
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
		pojoInsertRes.setMessage("Success");
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

	public PojoUserBookmarkGetAllRes getCountBookmark(final String postId) {
		final PojoUserBookmarkGetAllRes bookmark = new PojoUserBookmarkGetAllRes();

		bookmark.setUserId(principalService.getAuthPrincipal());
		bookmark.setPostId(postId);
		bookmark.setCountedBookmark(userBookmarkDao.getCount(postId));

		return bookmark;
	}

	public PojoDeleteRes deleteBookmark(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deleteBookmarkById(id);
		res.setMessage("Berhasil Dihapus");
		return res;
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
			ConnHandler.begin();
			valIdNull(data);
			valNotNullable(data);
			final File file = fileDao.insert(data.getFile());
			postDetailInsert = data;
			postDetailInsert.setFile(file);
			postDetailInsert = postDetailDao.insert(postDetailInsert);
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

	public PojoInsertRes insert(final PojoPostDetailInsertReq data) {
		final PostDetail postDetail = new PostDetail();

		postDetail.setDetailContent(data.getDetailContent());

		final File file = new File();
		file.setFileName(data.getFile().getFileName());
		file.setFileExtension(data.getFile().getFileExtension());
		file.setFileContent(data.getFile().getFileContent());

		ConnHandler.begin();
		final File fileInsert = fileDao.insert(file);
		ConnHandler.commit();
		postDetail.setFile(fileInsert);

		final Post post = postDao.getByIdRef(Post.class, data.getPostId());
		post.setId(data.getPostId());
		postDetail.setPost(post);

		PostDetail postDetailInsert = null;
		postDetailInsert = insert(postDetail);

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(postDetailInsert.getId());
		pojoInsertRes.setMessage("Success");
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

		if (data.getFile() != null) {
			final File file = new File();
			file.setFileName(data.getFile().getFileName());
			file.setFileExtension(data.getFile().getFileExtension());
			file.setFileContent(data.getFile().getFileContent());
			ConnHandler.begin();
			final File fileInsert = fileDao.insert(file);
			ConnHandler.commit();
			postDetail.setFile(fileInsert);
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

	public List<PojoPostDetailGetByPostIdRes> getByPostId(final String id) {
		final List<PojoPostDetailGetByPostIdRes> pojos = new ArrayList<>();
		final List<PostDetail> res = postDetailDao.getByPostId(id);

		for (int i = 0; i < res.size(); i++) {
			final PojoPostDetailGetByPostIdRes pojo = new PojoPostDetailGetByPostIdRes();
			final PostDetail postDetail = res.get(i);

			ConnHandler.getManager().detach(postDetail);
			pojo.setFileId(postDetail.getFile().getFileName());
			pojo.setId(postDetail.getId());
			pojo.setPostId(postDetail.getPost().getId());
			pojo.setDetailContent(postDetail.getDetailContent());
			pojo.setCountedCommentar(postDetailDao.getCount(id));
			pojos.add(pojo);
		}

		return pojos;
	}

	public PojoDeleteRes deletePostDetail(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deletePostDetailById(id);
		res.setMessage("Berhasil Dihapus");

		return res;
	}
}
