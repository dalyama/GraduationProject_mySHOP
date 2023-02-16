package com.myShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myShop.entities.userImage;

@Repository
public interface userImageRepo extends JpaRepository<userImage, Long> {
	
	userImage findOneByUser_Email(String email);
}
