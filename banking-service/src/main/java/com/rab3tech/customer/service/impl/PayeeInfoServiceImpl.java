
package com.rab3tech.customer.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.customer.dao.repository.PayeeInfoRepository;
import com.rab3tech.customer.service.PayeeInfoService;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.PayeeInfo;
import com.rab3tech.utils.Copy;
import com.rab3tech.vo.CustomerAccountInfoVO;
import com.rab3tech.vo.LoginVO;
import com.rab3tech.vo.PayeeInfoVO;

@Service
@Transactional
public class PayeeInfoServiceImpl implements PayeeInfoService {

	@Autowired
	private PayeeInfoRepository payeeInfoRepository;

	
	
	@Autowired
	private CustomerAccountInfoRepository customerAccountInfoRepository;

	@Override
	public List<String> findNicknamesByUsername(String username) {
		List<String> nicknameList = payeeInfoRepository.findNicknamesByUsername(username);
		return nicknameList;
	}

	@Override
	public PayeeInfoVO findByPayeeAccountNo(String payeeAccountNo) {
		PayeeInfo payeeInfo = payeeInfoRepository.findByPayeeAccountNo(payeeAccountNo).get();
		PayeeInfoVO payeeInfoVO = new PayeeInfoVO();
		if (payeeInfo != null) {
			BeanUtils.copyProperties(payeeInfo, payeeInfoVO);
		}
		return payeeInfoVO;
	}

	
	
	
	@Override
	public String updatePayeeInfo(PayeeInfoVO payeeInfoVO) {
		String message = null;
		Optional<CustomerAccountInfo> payeeAccountInfo = customerAccountInfoRepository.findByAccountNumber(payeeInfoVO.getPayeeAccountNo());

		// checking the beneficiary account is in good standing or not
		if (payeeAccountInfo.isEmpty()) {
			message = "Sorry, Beneficiary account number invalid.";
			return message;
		}

		// checking the beneficiary nicknames in the customer themselves is
		// duplicate or not
		// instead of doing this check, customerId of logging customer and passed nickname, if that is in dB then reject 
		Optional<PayeeInfo> payeeInfo = payeeInfoRepository.findBeneficiaryByNickname(payeeInfoVO.getCustomerId(), payeeInfoVO.getPayeeNickName());
		if (payeeInfo.isPresent()) {
			message = "Sorry, The beneficiary nickname already exist.";
			return message;
		}
		
		// We need this here, in order to get the Date of entry of the Payee (as this is the method for editing the payee)
		Optional<PayeeInfo> payeeOptional = payeeInfoRepository.findByPayeeAccountNo(payeeInfoVO.getPayeeAccountNo());

		//if everything is good
		payeeInfoVO.setDoe(payeeOptional.get().getDoe());
		payeeInfoVO.setDom(new Timestamp(new Date().getTime()));
	
		PayeeInfo payeeInfo2 = new PayeeInfo();
		BeanUtils.copyProperties(payeeInfoVO, payeeInfo2);
		message = "Your Beneficiary is added successfully.";
		payeeInfoRepository.save(payeeInfo2);
		return message;
	}

	
	
	
	@Override
	public String addPayee(PayeeInfoVO payeeInfoVO) {
		String message = null;
		Optional<CustomerAccountInfo> payeeAccountInfo = customerAccountInfoRepository.findByAccountNumber(payeeInfoVO.getPayeeAccountNo());

		// checking the beneficiary account is in good standing or not
		if (payeeAccountInfo.isEmpty()) {
			message = "Sorry, Beneficiary account number invalid.";
			return message;
		}

		// checking the beneficiary nicknames in the customer themselves is
		// duplicate or not
		// instead of doing this check, customerId of logging customer and passed nickname, if that is in dB then reject 
		Optional<PayeeInfo> payeeInfo = payeeInfoRepository.findBeneficiaryByNickname(payeeInfoVO.getCustomerId(), payeeInfoVO.getPayeeNickName());
		if (payeeInfo.isPresent()) {
			message = "Sorry, The beneficiary nickname already exist.";
			return message;
		}
		
		// check if the account number match with logged in customers'  accNo
		// logging customer's email . equals (the one going to be added as Payee's
		// email)
		if (payeeInfoVO.getCustomerId().equals(payeeAccountInfo.get().getCustomerId().getLoginid())) {
			message = "Sorry, You cannot add yourself as a Beneficiary.";
			return message;
		}

		// check if the beneficiary already exists
		payeeInfo = payeeInfoRepository.findBeneficiaryByPayeeAccountNo(payeeInfoVO.getCustomerId(),payeeInfoVO.getPayeeAccountNo());
		if (payeeInfo.isPresent()) {
			message =  "Sorry, Beneficiary already exist.";
			return message;
		}
		
		//if everything is good
		payeeInfoVO.setDoe(new Timestamp(new Date().getTime()));
		payeeInfoVO.setDom(new Timestamp(new Date().getTime()));
	
		
		PayeeInfo payeeInfo2 = new PayeeInfo();
		BeanUtils.copyProperties(payeeInfoVO, payeeInfo2);
		message = "Your Beneficiary is added successfully.";
		payeeInfoRepository.save(payeeInfo2);
		return message;
	}

	@Override
	public List<PayeeInfoVO> findByCustomerId(String username) {
		List<PayeeInfo> payeeList = payeeInfoRepository.findByCustomerId(username);
		List<PayeeInfoVO> payeeVOList = 
				payeeList.stream().map(t->{
					PayeeInfoVO payeeInfoVO = new PayeeInfoVO();
					BeanUtils.copyProperties(t, payeeInfoVO);
					return payeeInfoVO;
				}).collect(Collectors.toList());
		return payeeVOList;
	}

	@Override
	public void deleteByAccountNo(String payeeAccountNo) {
		payeeInfoRepository.deleteByPayeeAccountNo(payeeAccountNo);
		
	}

	@Override
	public void deleteById(int id) {
		
		payeeInfoRepository.deleteById(id);
		
	}
}
