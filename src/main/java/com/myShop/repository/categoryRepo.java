package com.myShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myShop.entities.category;

@Repository
public interface categoryRepo extends JpaRepository<category, Long> {

}
