package com.rab3tech.customer.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rab3tech.dao.entity.CustomerSaving;
import com.rab3tech.dao.entity.FundTransfer;

/**
 * 
 * @author nagendra
 * comment
 * 
 * Spring Data JPA repository
 *
 */
public interface FundTransferRepository extends JpaRepository<FundTransfer, Integer> {

	@Query("SELECT t FROM FundTransfer t where t.debitAccountNo = :accountNumber or t.payeeId.payeeAccountNo = :accountNumber order by t.transactionDate desc") 
	List<FundTransfer> getAllTransaction(@Param("accountNumber")String accountNumber);
	
	}

