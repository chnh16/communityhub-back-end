package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

public interface MasterDao<T> {
	
	Optional<T> getById(Long id);
	
	Optional<T> getRefById(Long id);
	
	List<T> getAll();
	
	T update(T data);
	
	T insert(T data);
	
	boolean delete(Long id);
}
