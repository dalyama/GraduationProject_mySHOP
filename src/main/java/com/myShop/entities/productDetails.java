package com.myShop.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "productDetails")
@Table(name = "productDetails")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class productDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Please provide a price")
	@DecimalMin("1.00")
	@Column(name = "price", nullable = false)
	private float price;

	@Column(name = "color", nullable = false)
	private String color;
	
	@Column(name = "size", nullable = false)
	private String size;
	
	@Min(value = 0, message = "Sale should not be less than 0")
	@Max(value = 100, message = "Sale should not be greater than 100")
	@Column(name = "sale", nullable = true)
	private int sale = 0;//per 100
	
	@ManyToOne
	@JoinColumn(name ="product_id")
	private product product;

	/*
	@ManyToOne
	@JoinColumn(name = "store_id")
	private store store;
	*/
	@JsonIgnore
	@OneToMany(mappedBy = "productDetails")
    private Set<stock> stock;
	
	@JsonIgnore
	@OneToMany(mappedBy = "productDetails")
	private Set<orderDetails> orderDetails;
	
	@JsonIgnore
	@OneToMany(mappedBy = "productDetails")
    private Set<cart> cart;
	
}
