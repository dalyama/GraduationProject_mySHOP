package com.myShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myShop.entities.productDetails;

@Repository
public interface productDetailsRepo extends JpaRepository<productDetails, Long> {

	@Query (value = "select * from product_details where product_id = ?1",nativeQuery = true)
	List<productDetails> findAllById(Long product_id);

	List<productDetails> findAllByProduct_id(Long product_id);

	
}
