package com.lawencon.community.service;

public abstract class ValidationService<T> {
	
	abstract void valNotNullable(T data);
	
	abstract void valIdNull(T data);
	
	abstract void valIdNotNull(T data);
	
	abstract void valBkNull(T data);
	
	abstract void valFkNull(T data);
	
	abstract void valMaxLength(T data);
	
	abstract void valMinLength(T data);
	
	abstract void valIdPresent(T data);
}
