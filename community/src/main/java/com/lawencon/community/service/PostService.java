package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.PostDetailDao;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.dao.UserBookmarkDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostDetail;
import com.lawencon.community.model.PostType;
import com.lawencon.community.model.User;
import com.lawencon.community.model.UserBookmark;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.post.PojoPostGetAllRes;
import com.lawencon.community.pojo.post.PojoPostInsertReq;
import com.lawencon.community.pojo.post.PojoPostUpdateReq;
import com.lawencon.community.pojo.postdetail.PojoPostDetailGetAllRes;
import com.lawencon.community.pojo.postdetail.PojoPostDetailInsertReq;
import com.lawencon.community.pojo.postdetail.PojoPostDetailUpdateReq;
import com.lawencon.community.pojo.userbookmark.PojoUserBookmarkGetAllRes;
import com.lawencon.community.pojo.userbookmark.PojoUserBookmarkInsertReq;
import com.lawencon.security.principal.PrincipalServiceImpl;

public class PostService extends PrincipalServiceImpl {
	
	private final PostDao postDao;
	private final UserDao userDao;
	private final PostTypeDao postTypeDao;
	private final CategoryDao categoryDao;
	private final UserBookmarkDao userBookmarkDao;
	private final PostDetailDao postDetailDao;
	private final FileDao fileDao;
	
	public PostService(final PostDao postDao, final UserDao userDao, final PostTypeDao postTypeDao, final CategoryDao categoryDao, UserBookmarkDao userBookmarkDao,  PostDetailDao postDetailDao, FileDao fileDao) {
		this.postDao = postDao;
		this.userDao = userDao;
		this.postTypeDao = postTypeDao;
		this.categoryDao = categoryDao;
		this.userBookmarkDao = userBookmarkDao;
		this.postDetailDao = postDetailDao;
		this.fileDao = fileDao;
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
		
		final User user = userDao.getRefById(getAuthPrincipal()).get();
		post.setUser(user);
		
		post.setPostTitle(data.getPostTitle());
		post.setPostContent(data.getPostContent());
		
		final PostType postType = postTypeDao.getRefById(data.getPostTypeId()).get();
		post.setPostType(postType);
		
		final Category category = categoryDao.getRefById(data.getCategoryId()).get();
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
			final PostType postType = postTypeDao.getRefById(data.getPostTypeId()).get();
			post.setPostType(postType);			
		}
				
		if(data.getCategoryId() != null) {
			final Category category = categoryDao.getRefById(data.getCategoryId()).get();
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
	
	public Optional<Post> getRefById(final String id){
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
		
		final User user = userDao.getRefById(getAuthPrincipal()).get();
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
		final List<UserBookmark> bookmarks = getAllByUser(getAuthPrincipal());
		
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
