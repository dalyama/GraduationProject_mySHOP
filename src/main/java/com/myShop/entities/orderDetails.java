package com.myShop.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orderDetails")
@Table(name = "orderDetails")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class orderDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name = "count")
	private int count;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "discount")
	private float discount;
	
	//@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "order_id")
	private order order;
	
	//@JsonIgnore
	@ManyToOne
	@JoinColumn(name ="productDetails_id")
	private product productDetails;
	
	
	
}
