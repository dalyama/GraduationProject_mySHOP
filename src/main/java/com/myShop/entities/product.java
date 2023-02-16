package com.myShop.entities;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product")
@Table(name = "product")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//Stock Keeping Unit
	private String sku;
	
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "madein", nullable = false)
	private String madein;
	
	@Column(name = "gender" ,nullable = true)
	private String gender = "all";
	
	private String brand;
	
	
	@Column(name = "cost")
	private float cost = (float) 0.0;
	
	@Column(name = "totalStock")
	private int totalStock = 0;

	@Column(name = "description")
	private String description;
	
	
	private Date updateDate = new Date();
	
	@Lob
    private byte[] albumImg;
	
	String albumName;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private category category;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private Set<productDetails> productDetails;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<productPhotos> productPhotos;

	public product(String name, String madein, String description,category category) {
		this.name = name;
		this.madein = madein;
		this.description = description;
		this.category = category;
	}
	
}
