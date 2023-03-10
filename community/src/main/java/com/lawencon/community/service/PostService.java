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
import com.lawencon.community.dao.UserBookmarkDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dao.UserLikeDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostDetail;
import com.lawencon.community.model.PostFile;
import com.lawencon.community.model.PostType;
import com.lawencon.community.model.User;
import com.lawencon.community.model.UserBookmark;
import com.lawencon.community.model.UserLike;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.post.PojoPostGetAllRes;
import com.lawencon.community.pojo.post.PojoPostInsertReq;
import com.lawencon.community.pojo.post.PojoPostUpdateReq;
import com.lawencon.community.pojo.postdetail.PojoPostDetailGetAllRes;
import com.lawencon.community.pojo.postdetail.PojoPostDetailInsertReq;
import com.lawencon.community.pojo.postdetail.PojoPostDetailUpdateReq;
import com.lawencon.community.pojo.postfile.PojoPostFileGetAllRes;
import com.lawencon.community.pojo.postfile.PojoPostFileInsertReq;
import com.lawencon.community.pojo.userbookmark.PojoUserBookmarkGetAllRes;
import com.lawencon.community.pojo.userbookmark.PojoUserBookmarkInsertReq;
import com.lawencon.community.pojo.userlike.PojoUserLikeInsertReq;
import com.lawencon.security.principal.PrincipalService;


@Service
public class PostService  {
	
	private final PostDao postDao;
	private final UserDao userDao;
	private final PostTypeDao postTypeDao;
	private final CategoryDao categoryDao;
	private final PostFileDao postFileDao;
	private final UserLikeDao userLikeDao;
	private final UserBookmarkDao userBookmarkDao;
	private final PostDetailDao postDetailDao;
	private final FileDao fileDao;
	private final PrincipalService principalService;
	
	public PostService(final PostDao postDao, final PostFileDao postFileDao, final UserLikeDao userLikeDao,final UserDao userDao, final PostTypeDao postTypeDao, final CategoryDao categoryDao, UserBookmarkDao userBookmarkDao,  PostDetailDao postDetailDao, FileDao fileDao, PrincipalService principalService) {


		this.postDao = postDao;
		this.userDao = userDao;
		this.postTypeDao = postTypeDao;
		this.categoryDao = categoryDao;

		this.postFileDao = postFileDao;
		this.fileDao = fileDao;
		this.userLikeDao = userLikeDao;

		this.principalService = principalService;
		this.userBookmarkDao = userBookmarkDao;
		this.postDetailDao = postDetailDao;
	}
	
	public Post insertPost(final Post data) {
		Post postInsert = null;
		try {
			ConnHandler.begin();
			postInsert = postDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postInsert;
	}
	
	public PojoInsertRes insertPost(final PojoPostInsertReq data) {
		final Post post = new Post();
		
		final User user = userDao.getRefById(principalService.getAuthPrincipal());
		post.setUser(user);
		
		post.setPostTitle(data.getPostTitle());
		post.setPostContent(data.getPostContent());
		
		final PostType postType = postTypeDao.getRefById(data.getPostTypeId());
		post.setPostType(postType);
		
		final Category category = categoryDao.getRefById(data.getCategoryId());
		post.setCategory(category);
		
		post.setIsActive(true);
		
		final Post postInsert = insertPost(post);
		
		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(postInsert.getId());
		pojoInsertRes.setMessage("Berhasil Menambah Data");
		return pojoInsertRes;
	}
	
	public Post updatePost(final Post data) {
		Post postUpdate = null;
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
				
		if(data.getPostTitle() != null) {
			post.setPostTitle(data.getPostTitle());
		}
		
		if(data.getPostContent() != null) {
			post.setPostContent(data.getPostContent());
		}
		
		if(data.getPostTypeId() != null) {
			final PostType postType = postTypeDao.getRefById(data.getPostTypeId());
			post.setPostType(postType);			
		}
				
		if(data.getCategoryId() != null) {
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
	
	public Optional<Post> getById(final String id){
		return postDao.getById(id);
	}
	
	public Post getByIdAndDetach(final String id) {
		return postDao.getByIdAndDetach(Post.class, id);
	}
	
	public Post getRefById(final String id){
		return postDao.getRefById(id);
	}
	
	public List<Post> getAll(){
		return postDao.getAll();
	}
	
	public List<PojoPostGetAllRes> getAllPost(){
		final List<PojoPostGetAllRes> listPojoPost = new ArrayList<>();
		
		final List<Post> listPost = getAll();
		
		for(int i = 0 ; i < listPost.size(); i++) {
			final PojoPostGetAllRes pojoPost = new PojoPostGetAllRes();
			
			pojoPost.setId(listPost.get(i).getId());
			pojoPost.setUserId(listPost.get(i).getUser().getId());
			pojoPost.setFullName(listPost.get(i).getUser().getProfile().getFullName());
			pojoPost.setPostTitle(listPost.get(i).getPostTitle());
			pojoPost.setPostContent(listPost.get(i).getPostContent());
			pojoPost.setCategoryId(listPost.get(i).getCategory().getId());
			pojoPost.setCategoryName(listPost.get(i).getCategory().getCategoryName());
			pojoPost.setPostTypeId(listPost.get(i).getPostType().getId());
			pojoPost.setTypeName(listPost.get(i).getPostType().getTypeName());
			
			listPojoPost.add(pojoPost);
		}
		
		return listPojoPost;
	}

	
	public PostFile insertPostFile(final PostFile data) {
		PostFile postFileInsert = null;
		try {
			ConnHandler.begin();
			final File file = fileDao.insert(data.getFile());
			postFileInsert = data;
			postFileInsert.setFile(file);
			postFileInsert = postFileDao.insert(postFileInsert);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postFileInsert;
	}
	
	public PojoInsertRes insertPostFile(final PojoPostFileInsertReq data) {
		final PostFile postFile = new PostFile();
		
		final Post post = postDao.getRefById(data.getPostId());
		postFile.setPost(post);
		
		final File file = new File();
		file.setFileName(data.getFile().getFileName());
		file.setFileExtension(data.getFile().getFileExtension());
		file.setFileContent(data.getFile().getFileContent());
		file.setIsActive(true);
		final File fileInsert = fileDao.insert(file);
		postFile.setFile(fileInsert);
		
		postFile.setIsActive(true);
		
		final PostFile postFileInsert = insertPostFile(postFile);
		
		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(postFileInsert.getId());
		pojoInsertRes.setMessage("Berhasil Menambah Data");
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
		deleteById(id);
		res.setMessage("Berhasil Menghapus Data");
		return res;
	}
	
	
	public List<PojoPostFileGetAllRes> getAllPostFile(final String postId){
		final List<PojoPostFileGetAllRes> listPojoPostFile = new ArrayList<>();
		
		final List<PostFile> listPostFile = postFileDao.getAllPostFile(postId);
		
		for(int i = 0; i < listPostFile.size(); i++) {
			final PojoPostFileGetAllRes pojoPostFile = new PojoPostFileGetAllRes();
			pojoPostFile.setId(listPostFile.get(i).getId());
			pojoPostFile.setPostId(listPostFile.get(i).getPost().getId());
			pojoPostFile.setFileId(listPostFile.get(i).getFile().getId());
			
			listPojoPostFile.add(pojoPostFile);
		}
		
		return listPojoPostFile;
	}
	
	public UserLike insertUserLike(final UserLike data) {
		UserLike userLikeInsert = null;
		try {
			ConnHandler.begin();
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
		pojoInsertRes.setMessage("Berhasil Menambah Data");
		return pojoInsertRes;
	}
	
	public boolean deleteUserLikeById(final String id) {
		boolean userLikeDelete = false;

		try {
			ConnHandler.begin();
			userLikeDelete = userLikeDao.deleteById(Post.class, id);
			ConnHandler.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userLikeDelete;
	}
	

	public PojoDeleteRes deleteUserLikeRes(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deleteById(id);
		res.setMessage("Berhasil Menghapus Data");

		return res;
	}
	
	public UserBookmark insert(final UserBookmark data) {
		UserBookmark userBookmarkInsert = null;
		try {
			ConnHandler.begin();
			userBookmarkInsert = userBookmarkDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userBookmarkInsert;
	}
	
	public java.util.List<UserBookmark> getAllByUser(final String userId) {
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
		final List<UserBookmark> bookmarks = getAllByUser(principalService.getAuthPrincipal());
		
		for(int i = 0; i < bookmarks.size(); i++) {
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
	
	public PojoDeleteRes delete(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deleteBookmarkById(id);
		res.setMessage("Berhasil Dihapus");
		return res;
	}
	
	public PostDetail insert(final PostDetail data) {
		PostDetail postDetailInsert = null;
		try {
			ConnHandler.begin();
			postDetailInsert = postDetailDao.insert(data);
			ConnHandler.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postDetailInsert;
	} 
	
	public PostDetail update(final PostDetail data) {
		PostDetail postDetailUpdate = null;
		
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
		
		final File files = fileDao.getByIdRef(File.class, data.getFileId());
		files.setId(data.getFileId());
		postDetail.setFile(files);
		
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
		
		postDetail.setDetailContent(data.getDetailContent());
		postDetail.setVersion(data.getVer());
		
		final File file = fileDao.getByIdRef(File.class, data.getFileId());
		file.setId(data.getFileId());
		postDetail.setFile(file);
		
		final Post post = postDao.getByIdRef(Post.class, data.getPostId());
		post.setId(data.getPostId());
		postDetail.setPost(post);
		
		final PostDetail postDetailUpdate = update(postDetail);
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		pojoUpdateRes.setVer(postDetailUpdate.getVersion());
		pojoUpdateRes.setMessage("Updated");
		return pojoUpdateRes;
	}
	
	public List<PojoPostDetailGetAllRes> getAllPostDetailRes() {
		final List<PojoPostDetailGetAllRes> getAllRes = new ArrayList<>();
		final List<PostDetail> posDetails = getAllPostDetail();
		
		for(int i=0; i < posDetails.size(); i++) {
			final PojoPostDetailGetAllRes detailGetAllRes = new PojoPostDetailGetAllRes();
			final PostDetail detail = posDetails.get(i);
			
			ConnHandler.getManager().detach(detail);
			detailGetAllRes.setId(detail.getId());
			detailGetAllRes.setDetailContent(posDetails.get(i).getDetailContent());
			detailGetAllRes.setFileId(posDetails.get(i).getFile().getId());
			detailGetAllRes.setPostId(posDetails.get(i).getPost().getId());
			getAllRes.add(detailGetAllRes);	
		}
		return getAllRes;
	}
	
	public PojoDeleteRes deletePostDetail(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deletePostDetailById(id);
		res.setMessage("Berhasil Dihapus");

		return res;
	}
}

