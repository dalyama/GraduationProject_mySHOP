package com.myShop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myShop.entities.product;
import com.myShop.entities.temporary.categoryGroup;

@Repository
public interface productRepo extends JpaRepository<product, Long>{
	/*
	String query = "SELECT DISTINCT product.id  \r\n" + 
			"  FROM stock join product_details on stock.product_details_id = product_details.id join product on product.id = product_details.product_id where stock.count > 0";
*/
	List<product> findTop10ByOrderByIdDesc();
	
	/*@Query( value = " select * from users where email =?1 " , nativeQuery = true )
	List<product> findOne(String email);
	*/
	/*
	@Query( value = " with newTable as ( SELECT product_id, min(price) as minimum\r\n" + 
			"	FROM product_details join product on product.id = product_details.product_id \r\n" + 
			"	GROUP BY product_id)\r\n" + 
			"	select * from newTable join product_details on product_details.product_id = newTable.product_id" , nativeQuery = true )
	List<Object> getAllProductWithMinimum();

	@Query(value = "select top 1000 * from product where product.available = 'available' and product.album_image is not null " , nativeQuery = true)
	List<product> getAllAvailableProducts();
	*/
	//@Query(value = "select * from producetDetails where category.id = ?1",nativeQuery = true)
	//List<productDetails> getAllProductDetailsForOneCategory(Long id);
	
	List<product> findAllByTotalStockGreaterThanOrderByIdDesc(int totalStock);
	
	
	@Query("SELECT name FROM product where name like %:keyword%")
	public List<String> search(@Param("keyword") String keyword);
	
	
	@Query("select new com.myShop.entities.temporary.categoryGroup("
			//+ "p.category.id as categoryId, "
			+ "p.category.name as categoryName, "
			+ "min(p.cost) as minPrice, "
			+ "max(p.cost) as maxPrice, "
			+ "count(p.id) as countProduct, "
			+ "sum(p.totalStock) as sumQuantity,"
			+ "avg(p.cost) as avgPrice"
			+ ") from product p "
			+ "group by p.category.name")
	 List<categoryGroup> groupBy();
	 
	 List<product> findTop30ByCategory_idAndTotalStockGreaterThanOrderByIdDesc(Long id , int count);

	List<product> findTop30ByCategory_idAndTotalStockGreaterThanAndIdNotOrderByIdDesc(Long cid,
			int i, Long id);

	Page<product> findAllByTotalStockGreaterThanOrderByIdDesc(int i, Pageable pageable);
	
	
	Page<product> findByTotalStockGreaterThan(int i, Pageable pageable);
	
	Page<product> findByNameLike(String name,Pageable pageable);
	
	List<product> findTop30ByName(String name);
}
