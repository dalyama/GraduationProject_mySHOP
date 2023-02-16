package com.myShop.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "delivery")
@Table(name = "delivery")
public class delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "isDeparture")
	private boolean isDeparture = false;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "departureTime")
	private Date departureTime;

	@Column(name = "arrivalTime")
	private Date arrivalTime;

	@Column(name = "isArrival")
	private boolean isArrival = false;

}
