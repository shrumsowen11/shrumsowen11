package com.rab3tech.dao.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "address_tbl")
public class Address {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid", nullable = false)
	private Login userid;
	
	private String firstName;
	private String lastName;
	private Long mobile;
	private String address1;
	private String address2;
	private String city;
	private String zipcode;
	private String country;

}
