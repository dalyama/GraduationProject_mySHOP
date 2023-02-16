package com.myShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myShop.entities.address;

@Repository
public interface addressRepo extends JpaRepository<address, Long> {

	address findOneByUser_Email(String email);

}
