package com.rab3tech.customer.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rab3tech.dao.entity.PayeeInfo;

public interface PayeeInfoRepository extends JpaRepository<PayeeInfo, Integer>{

	@Query("SELECT t FROM PayeeInfo t where t.customerId = :pusername")
	List<String> findNicknamesByUsername(@Param("pusername") String customerId);

	public Optional<PayeeInfo> findByPayeeAccountNo(String payeeAccountNo);
	
	
	@Query("SELECT t FROM PayeeInfo t where t.customerId = :pusername")
	List<PayeeInfo> findAllPayees(@Param("pusername") String customerId);

	
	@Query("SELECT t FROM PayeeInfo t where t.customerId = :pusername and t.payeeNickName = :pnickname")
	Optional<PayeeInfo> findBeneficiaryByNickname(@Param("pusername")String customerId, @Param("pnickname") String payeeNickName);

	@Query("SELECT t FROM PayeeInfo t where t.customerId = :pusername and t.payeeAccountNo = :paccountNo")
	Optional<PayeeInfo> findBeneficiaryByPayeeAccountNo(@Param("pusername")String customerId, @Param("paccountNo") String payeeAccountNo);

	List<PayeeInfo> findByCustomerId(String username);

	void deleteByPayeeAccountNo(String payeeAccountNo);

	
	
}
