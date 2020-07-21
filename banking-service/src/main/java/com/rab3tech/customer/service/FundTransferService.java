package com.rab3tech.customer.service;

import java.util.List;

import com.rab3tech.vo.FundTransferVO;

public interface FundTransferService {

	String processFundTransfer(FundTransferVO fundTransferVO);

	List<FundTransferVO> findAllTransactions(String username);

}
