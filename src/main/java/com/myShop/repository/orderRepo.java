package com.myShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myShop.entities.order;

@Repository
public interface orderRepo extends JpaRepository<order, Long> {

}
