package com.lawencon.community.service;

public abstract class ValidationService<T> {
	
	abstract void valId(T data);
	
	abstract void valIdNull(T data);
	
}
