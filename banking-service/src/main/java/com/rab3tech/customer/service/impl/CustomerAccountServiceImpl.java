
package com.rab3tech.customer.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.customer.dao.repository.CustomerAccountApprovedRepository;
import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.customer.dao.repository.CustomerQuestionsAnsRepository;
import com.rab3tech.customer.dao.repository.CustomerRepository;
import com.rab3tech.customer.dao.repository.LoginRepository;
import com.rab3tech.customer.dao.repository.SecurityQuestionsRepository;
import com.rab3tech.customer.service.CustomerAccountService;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.CustomerQuestionAnswer;
import com.rab3tech.dao.entity.CustomerSavingApproved;
import com.rab3tech.dao.entity.Login;
import com.rab3tech.vo.ChangePasswordVO;
import com.rab3tech.vo.CustomerAccountInfoVO;
import com.rab3tech.vo.CustomerSecurityQueAnsVO;
import com.rab3tech.vo.LoginVO;

@Service

@Transactional
public class CustomerAccountServiceImpl implements CustomerAccountService {

	@Autowired
	private CustomerAccountInfoRepository customerAccountInfoRepository;
	
	@Autowired
	private CustomerQuestionsAnsRepository customerQuestionsAnsRepository;
	
	@Autowired
	private CustomerAccountApprovedRepository customerAccountApprovedRepository;
	
	@Autowired
	private SecurityQuestionsRepository securityQuestionsRepository;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public CustomerAccountInfoVO getCustomerAccount(String username) {
		Optional<Login> login = loginRepository.findByLoginid(username);
		
		CustomerAccountInfo customerAccountInfo = customerAccountInfoRepository.findByCustomerId(login.get());
		CustomerAccountInfoVO customerAccountInfoVO = new CustomerAccountInfoVO();
		if (customerAccountInfo != null) {
			BeanUtils.copyProperties(customerAccountInfo, customerAccountInfoVO);
		}
		return customerAccountInfoVO;
	}

	@Override
	public CustomerSecurityQueAnsVO getCustomerSecurityQA(String username) {
		List<CustomerQuestionAnswer> questionAnswers = customerQuestionsAnsRepository.findQuestionAnswer(username);
		//List<CustomerSecurityQueAnsVO> questions = new ArrayList<CustomerSecurityQueAnsVO>();
		CustomerSecurityQueAnsVO customerSecurityQueAnsVO = new CustomerSecurityQueAnsVO();
		if (questionAnswers != null) {
			customerSecurityQueAnsVO.setLoginid(username);
			customerSecurityQueAnsVO.setSecurityQuestion1(questionAnswers.get(0).getQuestion());
			customerSecurityQueAnsVO.setSecurityQuestion2(questionAnswers.get(1).getQuestion());
			customerSecurityQueAnsVO.setSecurityQuestionAnswer1(questionAnswers.get(0).getAnswer());
			customerSecurityQueAnsVO.setSecurityQuestionAnswer2(questionAnswers.get(1).getAnswer());
			

		}
		return customerSecurityQueAnsVO;
	}
	
	@Override
	public void changeForgottenPassword(ChangePasswordVO changePasswordVO) {
		String encodedPassword=bCryptPasswordEncoder.encode(changePasswordVO.getNewPassword());
		//Customer customer = customerRepository.findByEmail(email).get();
		Login  login = loginRepository.findByLoginid(changePasswordVO.getLoginid()).get();
		login.setPassword(encodedPassword);
		login.setLlt(new Timestamp(new Date().getTime()));
	}

	@Override
	public boolean getCustomerAccountApprovedByUsername(String username) {
		 Optional<CustomerSavingApproved> optional =  customerAccountApprovedRepository.findByEmail(username);

		if(optional.isPresent()) {
			return true;

		}
	return false;
	}

	@Override
	public CustomerAccountInfoVO findByAccountNumber(String payeeAccountNo) {
		Optional<CustomerAccountInfo> optional = customerAccountInfoRepository.findByAccountNumber(payeeAccountNo);
		CustomerAccountInfoVO customerAccountInfoVO =  new CustomerAccountInfoVO();
		if(optional.isPresent()) {
			BeanUtils.copyProperties(optional.get(), customerAccountInfoVO);
			Login login = optional.get().getCustomerId();
			LoginVO loginVO = new LoginVO();
			// we cannot beancopy the login into loginVO because, they do not have all the data types same if you go see inside them
			//BeanUtils.copyProperties(login, loginVO);

			loginVO.setEmail(login.getLoginid());
			customerAccountInfoVO.setCustomerId(loginVO);
		}
		return customerAccountInfoVO;
	}
}

