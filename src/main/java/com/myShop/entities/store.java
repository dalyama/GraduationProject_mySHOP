package com.myShop.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="store")
@Table(name = "store")
public class store {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	private String openTime = "8:00 AM";
	
	private String closeTime = "9:00 PM";
	
	@Column(name = "country")
	@NotEmpty
	private String country;

	@Column(name = "city")
	@NotEmpty
	private String city;

	@Column(name = "street")
	private String street;
	
	private String locality;

	@Column(name = "postNo")
	private String postNo;
	
	
	@Column(name = "description")
	String description;
	
	@JsonIgnore
	@OneToMany(mappedBy = "store")
    private Set<stock> stock;
	
    /*
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private address address;
    
    */
	
	
}
