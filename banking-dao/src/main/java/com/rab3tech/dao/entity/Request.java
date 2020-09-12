package com.rab3tech.dao.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "request_tbl")
@Data
public class Request {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private Timestamp requestDate;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid", nullable = false)
	private Login userid;
	
	private String description;
	
	private String customerAccountNo;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userAddress", nullable = false)
	private Address userAddress;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requestType", nullable = false)
	private RequestType requestType;

	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requestStatus", nullable = false)
	private RequestStatus requestStatus;

	
}
