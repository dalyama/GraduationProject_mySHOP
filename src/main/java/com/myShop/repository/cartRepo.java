package com.myShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myShop.entities.cart;

@Repository
public interface cartRepo extends JpaRepository<cart, Long> {

	List<cart> findAllByUser_id(Long user_id);

}
