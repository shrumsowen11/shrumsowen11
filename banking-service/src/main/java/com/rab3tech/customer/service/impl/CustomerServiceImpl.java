package com.rab3tech.customer.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.admin.dao.repository.AccountStatusRepository;
import com.rab3tech.admin.dao.repository.AccountTypeRepository;
import com.rab3tech.admin.dao.repository.MagicCustomerRepository;
import com.rab3tech.customer.dao.repository.CustomerAccountApprovedRepository;
import com.rab3tech.customer.dao.repository.CustomerAccountEnquiryRepository;
import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.customer.dao.repository.CustomerQuestionsAnsRepository;
import com.rab3tech.customer.dao.repository.LoginRepository;
import com.rab3tech.customer.dao.repository.RoleRepository;
import com.rab3tech.customer.service.CustomerService;
import com.rab3tech.dao.entity.AccountStatus;
import com.rab3tech.dao.entity.Customer;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.CustomerQuestionAnswer;
import com.rab3tech.dao.entity.CustomerSaving;
import com.rab3tech.dao.entity.CustomerSavingApproved;
import com.rab3tech.dao.entity.Login;
import com.rab3tech.dao.entity.Role;
import com.rab3tech.utils.AccountStatusEnum;
import com.rab3tech.utils.Copy;
import com.rab3tech.utils.PasswordGenerator;
import com.rab3tech.utils.Utils;
import com.rab3tech.vo.CustomerAccountInfoVO;
import com.rab3tech.vo.CustomerVO;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private MagicCustomerRepository customerRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private CustomerAccountEnquiryRepository customerAccountEnquiryRepository;

	@Autowired
	private LoginRepository loginRepository; 
	
	@Autowired
	private AccountStatusRepository accountStatusRepository;

	@Autowired
	private AccountTypeRepository accountTypeRepository;

	@Autowired
	private CustomerAccountApprovedRepository customerAccountApprovedRepository;

	@Autowired
	private CustomerAccountInfoRepository customerAccountInfoRepository;

	@Autowired
	private CustomerQuestionsAnsRepository customerQuestionsAnsRepository;
	
	
	@Override
	public CustomerAccountInfoVO createBankAccount(int csaid) {
		String customerAccount = Utils.generateCustomerAccount();

		CustomerSaving customerSaving = customerAccountEnquiryRepository.findById(csaid).get();

		CustomerAccountInfo customerAccountInfo = new CustomerAccountInfo();
		customerAccountInfo.setAccountNumber(customerAccount);
		customerAccountInfo.setAccountType(customerSaving.getAccType());
		customerAccountInfo.setAvBalance(1000.0F);
		customerAccountInfo.setBranch(customerSaving.getLocation());
		customerAccountInfo.setCurrency("$");
		Customer customer = customerRepository.findByEmail(customerSaving.getEmail()).get();
		

		customerAccountInfo.setCustomerId(customer.getLogin());
		customerAccountInfo.setStatusAsOf(new Date());
		customerAccountInfo.setTavBalance(1000.0F);
		CustomerAccountInfo customerAccountInfo2 = customerAccountInfoRepository.save(customerAccountInfo);

		CustomerSavingApproved customerSavingApproved = new CustomerSavingApproved();
		BeanUtils.copyProperties(customerSaving, customerSavingApproved);
		customerSavingApproved.setAccType(customerSaving.getAccType());
		customerSavingApproved.setStatus(customerSaving.getStatus());
		// saving entity into customer_saving_enquiry_approved_tbl
		customerAccountApprovedRepository.save(customerSavingApproved);

		// delete data from
		customerAccountEnquiryRepository.delete(customerSaving);

		CustomerAccountInfoVO accountInfoVO = new CustomerAccountInfoVO();
		BeanUtils.copyProperties(customerAccountInfo2, accountInfoVO);
		return accountInfoVO;	
	
	
	
	
	
	
	
	
	
	
		// We can also do it like this by making the VO of "customerAccountInfo"
		/*
		 * CustomerAccountInfo customerAccountInfo =
		 * customerAccountEnquiryRepository.findByUsername(username).get();
		 * CustomerAccountInfoVO customerAccountInfoVO = new CustomerAccountInfoVO(); if
		 * (customerAccountInfo != null) { BeanUtils.copyProperties(customerAccountInfo,
		 * customerAccountInfoVO); } return customerAccountInfoVO;
		 */
	}

	@Override
	public CustomerVO createAccount(CustomerVO customerVO) {
		Customer pcustomer = new Customer();
		BeanUtils.copyProperties(customerVO, pcustomer);
		Login login = new Login();
		login.setNoOfAttempt(3);
		login.setLoginid(customerVO.getEmail());
		login.setName(customerVO.getName());
		String genPassword = PasswordGenerator.generateRandomPassword(8);
		customerVO.setPassword(genPassword);
		login.setPassword(bCryptPasswordEncoder.encode(genPassword));
		login.setToken(customerVO.getToken());
		login.setLocked("no");

		Role entity = roleRepository.findById(3).get();
		Set<Role> roles = new HashSet<>();
		roles.add(entity);
		// setting roles inside login
		login.setRoles(roles);
		// setting login inside
		pcustomer.setLogin(login);
		Customer dcustomer = customerRepository.save(pcustomer);
		customerVO.setId(dcustomer.getId());
		customerVO.setUserid(customerVO.getUserid());

		Optional<CustomerSaving> optional = customerAccountEnquiryRepository.findByEmail(dcustomer.getEmail());
		if (optional.isPresent()) {
			CustomerSaving customerSaving = optional.get();
			// AccountStatus accountStatus = accountStatusRepository.findById(6).get();
			AccountStatus accountStatus = accountStatusRepository.findByCode(AccountStatusEnum.REGISTERED.getCode())
					.get();
			customerSaving.setStatus(accountStatus);
		}

		return customerVO;
	}

	@Override
	public CustomerVO findByEmail(String email) {
		Optional<Customer> optional = customerRepository.findByEmail(email);
		CustomerVO customerVO = null;
		if (optional.isPresent()) {
			customerVO = new CustomerVO();
			Customer customer = optional.get();
			BeanUtils.copyProperties(customer, customerVO);
		}
		return customerVO;
	}

	
	@Override
	public CustomerVO findByEmailWithSecurityQA(String email) {
		Optional<Customer> optional = customerRepository.findByEmail(email);
		CustomerVO customerVO = null;
		if (optional.isPresent()) {
			customerVO = new CustomerVO();
			Customer customer = optional.get();
			BeanUtils.copyProperties(customer, customerVO);
		}
		List<CustomerQuestionAnswer> qAnswer = customerQuestionsAnsRepository.findQuestionAnswer(email);
		customerVO.setQuestion1(qAnswer.get(0).getQuestion());
		customerVO.setQuestion2(qAnswer.get(1).getQuestion());
		customerVO.setAnswer1(qAnswer.get(0).getAnswer());
		customerVO.setAnswer2(qAnswer.get(1).getAnswer());
		return customerVO;
	}
	
	
	@Override
	public void updateCustomerProfile(CustomerVO customerVO) {
		Customer customer = new Customer();
		Copy.copyNonNullProperties(customerVO, customer);
		Login login = loginRepository.findByLoginid(customerVO.getEmail()).get();
		customer.setLogin(login);
		List<CustomerQuestionAnswer> qAnswer = customerQuestionsAnsRepository.findQuestionAnswer(customerVO.getEmail());
		
		if(qAnswer.get(0).getQuestion().equalsIgnoreCase(customerVO.getQuestion1()) &&
				qAnswer.get(0).getAnswer().equalsIgnoreCase(customerVO.getAnswer1()) &&
				qAnswer.get(1).getQuestion().equalsIgnoreCase(customerVO.getQuestion2()) &&
				qAnswer.get(1).getAnswer().equalsIgnoreCase(customerVO.getAnswer2())) {
			System.out.println("No changes in security Question answers");
		}else {
			//CustomerSecurityQueAnsVO customerSecurityQueAnsVO = customerQuestionsAnsRepository.
			qAnswer.get(0).setQuestion(customerVO.getQuestion1());
			qAnswer.get(0).setAnswer(customerVO.getAnswer1());
			qAnswer.get(1).setQuestion(customerVO.getQuestion2());
			qAnswer.get(1).setAnswer(customerVO.getAnswer2());
			BeanUtils.copyProperties(qAnswer, customer);
					}

		
		customerRepository.save(customer);
	}
}
