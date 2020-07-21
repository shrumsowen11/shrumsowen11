package com.rab3tech.customer.service;

import com.rab3tech.vo.CustomerAccountInfoVO;
import com.rab3tech.vo.CustomerVO;

public interface CustomerService {

	CustomerVO createAccount(CustomerVO customerVO);

	CustomerVO findByEmail(String username);

	CustomerAccountInfoVO createBankAccount(int csaid);

	void updateCustomerProfile(CustomerVO customerVO);

	CustomerVO findByEmailWithSecurityQA(String email);

}
