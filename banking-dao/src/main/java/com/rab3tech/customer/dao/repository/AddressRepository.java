package com.rab3tech.customer.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rab3tech.dao.entity.Address;
import com.rab3tech.dao.entity.Login;

/**
 * @author shrums
 * 
 *
 */
public interface AddressRepository extends JpaRepository<Address, Integer> {
	public Optional<Address> findByUserid(Login userid); 
	// This "Login" parameter will pick the primary key of the Login table
}

