
package com.rab3tech.customer.service;

import java.util.List;

import com.rab3tech.vo.ChangePasswordVO;
import com.rab3tech.vo.CustomerAccountInfoVO;
import com.rab3tech.vo.CustomerSecurityQueAnsVO;

public interface CustomerAccountService {

	CustomerAccountInfoVO getCustomerAccount(String username);

	CustomerSecurityQueAnsVO getCustomerSecurityQA(String username);

	void changeForgottenPassword(ChangePasswordVO changePasswordVO);

	boolean getCustomerAccountApprovedByUsername(String username);


	CustomerAccountInfoVO findByAccountNumber(String payeeAccountNo);

	List<CustomerAccountInfoVO> findByLoginid(String username);


}
