package com.myShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myShop.entities.productPhotos;

@Repository
public interface productPhotosRepo extends JpaRepository<productPhotos, Long> {

	List<productPhotos> findAllByProduct_id(Long id);

	void deleteAllByProduct_id(Long id);

}
