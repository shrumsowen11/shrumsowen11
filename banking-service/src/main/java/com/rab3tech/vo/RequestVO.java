package com.rab3tech.vo;

import java.sql.Timestamp;

import lombok.Data;


@Data
public class RequestVO {

		private int id;
		private Timestamp requestDate;
		private String userid;
		private String description;
		private String customerAccountNo;
		private String userAddress;
		private String requestType;
		private String requestStatus;



}
