package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.TypeProduct;

@Repository
public class TypeProductDao extends MasterDao<TypeProduct>{

	@Override
	public Optional<TypeProduct> getById(final String id) {
		return Optional.ofNullable(super.getById(TypeProduct.class, id));
	}

	@Override
	public TypeProduct getRefById(final String id) {
		return super.getByIdRef(TypeProduct.class, id);
	}

	@Override
	public TypeProduct getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(TypeProduct.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TypeProduct> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM type_product tp ")
			.append(" WHERE tp.is_active = true");
		final List<TypeProduct> res = em().createNativeQuery(toStr(str), TypeProduct.class)
				.getResultList();
		return res;
	}
	
	public Optional<TypeProduct> getTypeByCode(final String typeCode) {
		final StringBuilder str = new StringBuilder();
		TypeProduct typeProduct = null;
		try {
			str.append("SELECT tp.id, tp.created_by, tp.updated_by, tp.created_at, tp.updated_at, tp.ver, tp.is_active FROM type_product tp ")
			.append(" WHERE tp.type_code = :typeCode AND tp.is_active = true ");
			
			final Object res = em().createNativeQuery(toStr(str))
					.setParameter("typeCode", typeCode)
					.getSingleResult();
			
			if(res != null) {
				typeProduct = new TypeProduct();
				final Object[] objArr = (Object[]) res;

				typeProduct.setId(objArr[0].toString());

				typeProduct.setCreatedBy(objArr[1].toString());
				
				if (objArr[2] != null) {
					typeProduct.setUpdatedBy(objArr[2].toString());
				}

				typeProduct.setCreatedAt(Timestamp.valueOf(objArr[3].toString()).toLocalDateTime());

				if (objArr[4] != null) {
					typeProduct.setUpdatedAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				}

				typeProduct.setVersion(Integer.valueOf(objArr[5].toString()));
				typeProduct.setIsActive(Boolean.valueOf(objArr[6].toString()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(typeProduct);
	}

	@Override
	public TypeProduct update(final TypeProduct data) {
		final TypeProduct res = saveAndFlush(data);
		return res;
	}

	@Override
	public TypeProduct insert(final TypeProduct data) {
		final TypeProduct res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(String id) {
		return deleteById(TypeProduct.class, id);
	}

}
