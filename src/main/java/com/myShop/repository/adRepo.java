package com.myShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myShop.entities.ad;

@Repository
public interface adRepo extends JpaRepository<ad, Long> {

	public List<ad> findTop5ByOrderByIdDesc();
}
