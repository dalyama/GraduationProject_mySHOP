package com.myShop.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="stock")
@Table(name="stock")
public class stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@NotNull(message = "Please provide a count")
	@Min(0)
	@Column(name = "count", nullable = false)
	private int count = 0;
	
	@ManyToOne
	@JoinColumn(name = "store_id")
    private store store;
 
    @ManyToOne
    @JoinColumn(name ="productDetails_id")
    private productDetails productDetails;
    
    public stock(store store,productDetails productDetails) {
    	this.store = store;
    	this.productDetails = productDetails;
    }
}
