package com.rab3tech.vo;


import java.util.Date;

import com.rab3tech.dao.entity.AccountStatus;
import com.rab3tech.dao.entity.AccountType;

import lombok.Data;

/**
 * 
 * @author nagendra
 *   
 */

@Data
public class CustomerSavingApprovedVO {

	private int csaid;
	private String name;
	private String email;
	private String mobile;
	private String location;
	private String ucrid;
	private AccountType accType;
	private AccountStatus status;
	private Date doa;
	private String appref;
	

}

