package com.rab3tech.customer.service;

import com.rab3tech.vo.AddressVO;

public interface AddressService {

	AddressVO findByLoginid(String username);

	String addressUpdate(AddressVO addressVO);



}
