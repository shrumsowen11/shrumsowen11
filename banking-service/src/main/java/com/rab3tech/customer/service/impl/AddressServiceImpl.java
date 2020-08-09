package com.rab3tech.customer.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.customer.dao.repository.AddressRepository;
import com.rab3tech.customer.dao.repository.LoginRepository;
import com.rab3tech.customer.service.AddressService;
import com.rab3tech.dao.entity.Address;
import com.rab3tech.dao.entity.Login;
import com.rab3tech.vo.AddressVO;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public AddressVO findByLoginid(String username) {
		Login login = loginRepository.findByLoginid(username).get();
		Optional<Address> oAddress = addressRepository.findByUserid(login);
		if(oAddress.isPresent()) {
			Address address = oAddress.get();
			AddressVO addressVO = new AddressVO();
			addressVO.setAddress1(address.getAddress1());
			addressVO.setAddress2(address.getAddress2());
			addressVO.setCity(address.getCity());
			addressVO.setCountry(address.getCountry());
			addressVO.setFirstName(address.getFirstName());			
			addressVO.setLastName(address.getLastName());
			addressVO.setMobile(address.getMobile());
			addressVO.setUserid(address.getUserid().getLoginid());
			addressVO.setZipcode(address.getZipcode());
			return addressVO;
		}
		return null;
	}

	@Override
	public String addressUpdate(AddressVO addressVO) {
		String message = "Update not successful.";
		Address address = new Address();
		Login login = loginRepository.findByLoginid(addressVO.getUserid()).get();
		if(addressRepository.findByUserid(login) != null) {
			address.setAddress1(addressVO.getAddress1());
			address.setAddress2(addressVO.getAddress2());
			address.setCity(addressVO.getCity());
			address.setCountry(addressVO.getCountry());
			address.setFirstName(addressVO.getFirstName());
			address.setLastName(addressVO.getLastName());
			address.setMobile(addressVO.getMobile());
			address.setZipcode(addressVO.getZipcode());
			address.setUserid(login);
			addressRepository.save(address);
			message = "Address update successful";
		}
		return message;
	}
}
