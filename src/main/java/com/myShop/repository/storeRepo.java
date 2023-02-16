package com.myShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myShop.entities.store;

@Repository
public interface storeRepo extends JpaRepository<store,Long>{

}
