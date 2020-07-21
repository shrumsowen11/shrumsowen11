package com.rab3tech.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class FundTransferVO {

	private int payeeId;
	private String customerId;
	private float amount;
	private String description;
	private String transactionType;
	private Timestamp transactionDate;
	private PayeeInfoVO payee;
	
	
}
