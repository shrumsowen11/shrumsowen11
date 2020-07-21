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
@Table(name="customer_payee_funding_tbl")
@Data
public class FundTransfer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payeeId", nullable = false)
	private PayeeInfo payeeId;
	
	
	private float amount;
	private String description;
	private String debitAccountNo;
	private Timestamp transactionDate;
	
	
}
