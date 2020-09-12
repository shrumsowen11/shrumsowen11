package com.rab3tech.customer.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.customer.dao.repository.FundTransferRepository;
import com.rab3tech.customer.dao.repository.LoginRepository;
import com.rab3tech.customer.dao.repository.PayeeInfoRepository;
import com.rab3tech.customer.service.FundTransferService;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.FundTransfer;
import com.rab3tech.dao.entity.Login;
import com.rab3tech.dao.entity.PayeeInfo;
import com.rab3tech.vo.FundTransferVO;
import com.rab3tech.vo.PayeeInfoVO;

@Service
@Transactional
public class FundTransferServiceImpl implements FundTransferService {

	@Autowired
	private FundTransferRepository fundTransferRepository;

	@Autowired
	private CustomerAccountInfoRepository customerAccountInfoRepository;

	@Autowired
	private PayeeInfoRepository payeeInfoRepository;

	@Autowired
	private LoginRepository loginRepository;

	@Override
	public String processFundTransfer(FundTransferVO fundTransferVO) {
		String message = null;
		Optional<Login> login = loginRepository.findByLoginid(fundTransferVO.getCustomerId());
		CustomerAccountInfo loginCustomerAccount = new CustomerAccountInfo();
		if (login.isPresent()) {
			Login loginDetails = login.get();
			loginCustomerAccount = customerAccountInfoRepository.findByCustomerId(loginDetails);
		}

		// checking the logged in customer account is in good standing or not
		if (loginCustomerAccount == null) {
			message = "Sorry, Customer account number invalid. Contact bank.";
			return message;
		}
		if (loginCustomerAccount.getAvBalance() < fundTransferVO.getAmount()) {
			message = "Sorry, Your account have insufficient balance.";
			return message;
		}
		PayeeInfo payeeInfo = payeeInfoRepository.findById(fundTransferVO.getPayeeId()).get();
		// PayeeInfo payeeInfo =
		// payeeInfoRepository.findById(fundTransferVO.getPayee().getId());

		CustomerAccountInfo payeeCustomerAccount = customerAccountInfoRepository
				.findByAccountNumber(payeeInfo.getPayeeAccountNo()).get();

		// checking the beneficiary account is in good standing or not
		if (payeeCustomerAccount == null) {
			message = "Sorry, Beneficiary account number invalid. Contact bank.";
			return message;
		}
		FundTransfer fundTransfer = new FundTransfer();
		fundTransfer.setAmount(fundTransferVO.getAmount());
		fundTransfer.setDebitAccountNo(loginCustomerAccount.getAccountNumber());
		fundTransfer.setDescription(fundTransferVO.getDescription());
		fundTransfer.setPayeeId(payeeInfo);
		// here since the FundTransfer table is taking the PayeeId as the whole table of
		// PayeeInfo, we will have to provide the whole table and the JPA will pick what
		// key is required from the PayeeInfo provided
		fundTransfer.setTransactionDate(new Timestamp(new Date().getTime()));

		// saving all this into our FundTransfer table
		fundTransferRepository.save(fundTransfer);

		loginCustomerAccount.setAvBalance(loginCustomerAccount.getAvBalance() - fundTransferVO.getAmount());

		loginCustomerAccount.setTavBalance(loginCustomerAccount.getTavBalance() - fundTransferVO.getAmount());

		// Update the logged in customer Tav and av balance
		payeeCustomerAccount.setAvBalance(payeeCustomerAccount.getAvBalance() + fundTransferVO.getAmount());

		// Update the payee customer Tav and av balance
		payeeCustomerAccount.setTavBalance(payeeCustomerAccount.getTavBalance() + fundTransferVO.getAmount());
		message = "Fund transfer successful.";

		return message;
	}

	@Override
	public List<FundTransferVO> findAllTransactionsByUsername(String username) {
		Optional<Login> login = loginRepository.findByLoginid(username);
		CustomerAccountInfo loginCustomerAccount = new CustomerAccountInfo();
		List<FundTransferVO> fundTransferVOList = new ArrayList<FundTransferVO>();
		if (login.isPresent()) {
			Login loginDetails = login.get();
			loginCustomerAccount = customerAccountInfoRepository.findByCustomerId(loginDetails);
			List<FundTransfer> fundTransferList = fundTransferRepository.getAllTransaction(loginCustomerAccount.getAccountNumber());
			for (FundTransfer transaction : fundTransferList) {
				FundTransferVO fundTransferVO = new FundTransferVO();
				fundTransferVO.setAmount(transaction.getAmount());
				fundTransferVO.setDescription(transaction.getDescription());
				fundTransferVO.setPayeeId(transaction.getPayeeId().getId());
				fundTransferVO.setTransactionDate(transaction.getTransactionDate());

				if (transaction.getDebitAccountNo().equals(loginCustomerAccount.getAccountNumber())) {
					fundTransferVO.setTransactionType("Debit");
				} else {
					fundTransferVO.setTransactionType("Credit");
				}
				PayeeInfoVO payeeVO = new PayeeInfoVO();
				BeanUtils.copyProperties(transaction.getPayeeId(), payeeVO);
				fundTransferVO.setPayee(payeeVO);
				fundTransferVO.setPayeeId(payeeVO.getId());
				
				fundTransferVOList.add(fundTransferVO);
				
				//System.out.println(fundTransferVO + "\n ");
			}
			
			return fundTransferVOList;
		}

		return null;
	}
}