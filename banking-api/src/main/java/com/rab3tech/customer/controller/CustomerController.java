package com.rab3tech.customer.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rab3tech.customer.service.AddressService;
import com.rab3tech.customer.service.CustomerAccountService;
import com.rab3tech.customer.service.LoginService;
import com.rab3tech.vo.AddressVO;
import com.rab3tech.vo.ApplicationResponseVO;
import com.rab3tech.vo.CustomerAccountInfoVO;
import com.rab3tech.vo.CustomerAddressInfoVO;
import com.rab3tech.vo.LoginRequestVO;
import com.rab3tech.vo.LoginVO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v3")
public class CustomerController {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private CustomerAccountService customerAccountService;
	
	@Autowired
	private AddressService addressService;
	
	@PostMapping("/user/login")
	public ApplicationResponseVO authUser(@RequestBody LoginRequestVO loginRequestVO) {
		
		Optional<LoginVO> optional = loginService.findUserByUsername(loginRequestVO.getUsername());
		ApplicationResponseVO applicationResponseVO = new ApplicationResponseVO();
		if(optional.isPresent()) {
			applicationResponseVO.setCode(200);
			applicationResponseVO.setStatus("success");
			applicationResponseVO.setMessage("Useris is correct");
		}else {
			applicationResponseVO.setCode(400);
			applicationResponseVO.setStatus("fail");
			applicationResponseVO.setMessage("Useris is not correct");
		}
		return applicationResponseVO;
	}
	
	@GetMapping(value = "/customers/customerAccount/{customerAccountNo}")
	public CustomerAddressInfoVO findCustomerAccountAddress (@PathVariable("customerAccountNo") String customerAccountNo) {
		CustomerAddressInfoVO customerAddressInfoVO = new CustomerAddressInfoVO();
		CustomerAccountInfoVO customerAccountInfoVO = customerAccountService.findByAccountNumber(customerAccountNo);
		AddressVO addressVO = addressService.findByLoginid(customerAccountInfoVO.getCustomerId().getEmail());
		
		if(addressVO != null) {
			BeanUtils.copyProperties(addressVO, customerAddressInfoVO);
			/*
			customerAddressInfoVO.setAddress1(addressVO.getAddress1());
			customerAddressInfoVO.setAddress2(addressVO.getAddress2());
			customerAddressInfoVO.setCity(addressVO.getCity());
			customerAddressInfoVO.setCountry(addressVO.getCountry());
			customerAddressInfoVO.setFirstName(addressVO.getFirstName());
			customerAddressInfoVO.setLastName(addressVO.getLastName());
			customerAddressInfoVO.setMobile(addressVO.getMobile());
			customerAddressInfoVO.setZipcode(addressVO.getZipcode());*/
			return customerAddressInfoVO;
		}
		return null;
	}

}
