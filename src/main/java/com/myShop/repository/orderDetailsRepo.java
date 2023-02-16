package com.myShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myShop.entities.orderDetails;

@Repository
public interface orderDetailsRepo extends JpaRepository<orderDetails, Long> {
	
	@Query( value = " select * from myshop.order_details where order_id =?1 " , nativeQuery = true )
	List <orderDetails> getAllOrdersDetailsByOrderId(Long order_id);
	
	@Query( value = " select count(id) from myshop.order_details where order_id =?1 " , nativeQuery = true )
	int getCountOrdersDetailsByOrderId(Long order_id);
	
	
}
