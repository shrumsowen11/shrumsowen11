
package com.rab3tech.customer.service;

import java.util.List;

import com.rab3tech.vo.PayeeInfoVO;

public interface PayeeInfoService {

	List<String> findNicknamesByUsername(String username);

	PayeeInfoVO findByPayeeAccountNo(String payeeAccountNo);

	String updatePayeeInfo(PayeeInfoVO payeeInfoVO);

	String addPayee(PayeeInfoVO payeeInfoVO);

	List<PayeeInfoVO> findByCustomerId(String username);

	void deleteByAccountNo(String payeeAccountNo);

	void deleteById(int id);

	

}
