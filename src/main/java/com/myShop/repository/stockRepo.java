
package com.myShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myShop.entities.stock;

@Repository
public interface stockRepo extends JpaRepository<stock, Long>{
	public List<stock> findAllByProductDetails_Product_id(Long product_id);
	
	public List<stock> findAllByProductDetails_Product_idAndCountGreaterThanOrderByProductDetails_idDesc(Long product_id,int count);

	public List<stock> findTop30ByProductDetails_Product_Category_idAndCountGreaterThanOrderByProductDetails_idDesc(Long category_id,int count);




}
