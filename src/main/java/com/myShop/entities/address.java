package com.myShop.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "address")
@Table(name = "address")
public class address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "country")
	@NotEmpty
	private String country;

	@Column(name = "city")
	@NotEmpty
	private String city;

	@Column(name = "street")
	//@NotEmpty
	private String street;

	@Column(name = "postNo")
	private String postNo;

	@Column(name = "description")
	private String description;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;

	/*
	@OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
	private store store;
	*/
	
	

}