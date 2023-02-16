package com.myShop.entities.temporary;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class categoryGroup {

	//private Long categoryId ;
	private String categoryName;
	private float minPrice;
	private float maxPrice;
	private Long sumQuantity;
	private Long countProduct;
	private Double avgPrice;
	
	
	
	
}
