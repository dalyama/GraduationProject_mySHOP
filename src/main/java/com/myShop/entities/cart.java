package com.myShop.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cart")
@Table(name = "cart")
public class cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Min(value = 0)
	public int quantity ;
	
	private Date addedDate = new Date();
	
	@ManyToOne
	@JoinColumn(name = "user_id")
    private User user;
 
    @ManyToOne
    @JoinColumn(name ="productDetails_id")
    private productDetails productDetails;
}
