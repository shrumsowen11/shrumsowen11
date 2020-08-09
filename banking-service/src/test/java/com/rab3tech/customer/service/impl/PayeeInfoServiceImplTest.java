package com.rab3tech.customer.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.customer.dao.repository.PayeeInfoRepository;
import com.rab3tech.dao.entity.PayeeInfo;
import com.rab3tech.vo.PayeeInfoVO;


@RunWith(MockitoJUnitRunner.class)
public class PayeeInfoServiceImplTest {


	@Mock
	private PayeeInfoRepository payeeInfoRepository;

	@Mock
	private CustomerAccountInfoRepository customerAccountInfoRepository;

	@InjectMocks
	private PayeeInfoServiceImpl payeeInfoServiceImpl;
	
	@Before
	public void init() {
		 MockitoAnnotations.initMocks(this); //Initializing mocking for each test cases
	}
	
	@Test
	public void testFindByCustomerIdWhenListPresent() {
	
		List<PayeeInfo> payeeList = new ArrayList<PayeeInfo>();
		PayeeInfo payeeInfo1 = new PayeeInfo();
		PayeeInfo payeeInfo2 = new PayeeInfo();
		payeeInfo1.setPayeeAccountNo("11");
		payeeInfo1.setCustomerId("bmb@gmail.com");
		payeeInfo2.setPayeeAccountNo("12");
		payeeInfo2.setCustomerId("bmb@gmail.com");
		payeeList.add(payeeInfo1);
		payeeList.add(payeeInfo2);
		when(payeeInfoRepository.findByCustomerId("bmb@gmail.com")).thenReturn(payeeList);
		
		List<PayeeInfoVO> payeeInfoVOList = payeeInfoServiceImpl.findByCustomerId("bmb@gmail.com");
		assertNotNull(payeeInfoVOList);
		assertEquals(2, payeeInfoVOList.size());
		assertEquals("11", payeeInfoVOList.get(0).getPayeeAccountNo());
		assertEquals("12", payeeInfoVOList.get(1).getPayeeAccountNo());
		verify(payeeInfoRepository, times(1)).findByCustomerId("bmb@gmail.com");
	    verifyNoMoreInteractions(payeeInfoRepository);

		
	}
	
	@Test
	public void testFindByCustomerIdWhenListNotPresent() {
		List<PayeeInfo> payeeInfoList = new ArrayList<PayeeInfo>();
		when(payeeInfoRepository.findByCustomerId("bmb@gmail.com")).thenReturn(payeeInfoList);
		List<PayeeInfoVO> payeeInfoVOList = payeeInfoServiceImpl.findByCustomerId("bmb@gmail.com");
		assertNotNull(payeeInfoVOList);
		assertEquals(0,payeeInfoVOList.size());
		verify(payeeInfoRepository, times(1)).findByCustomerId("bmb@gmail.com");
	    verifyNoMoreInteractions(payeeInfoRepository);

}
	
	@Test
	public void testFindByPayeeAccountNoWhenPresent() {
		PayeeInfo payeeInfo = new PayeeInfo();
		payeeInfo.setCustomerId("bmb@gmail.com");
		payeeInfo.setPayeeAccountNo("123");
		Optional<PayeeInfo> oPayeeInfo = Optional.of(payeeInfo);
		
		when(payeeInfoRepository.findByPayeeAccountNo("123")).thenReturn(oPayeeInfo);
		
		PayeeInfoVO payeeInfoVO = payeeInfoServiceImpl.findByPayeeAccountNo("123");
		assertNotNull(payeeInfoVO);
		assertEquals("bmb@gmail.com", payeeInfoVO.getCustomerId());
		verify(payeeInfoRepository,times(1)).findByPayeeAccountNo("123");
		verifyNoMoreInteractions(payeeInfoRepository);
	}
	
	@Test
	public void testFindByPayeeAccountNoWhenNotPresent() {
		PayeeInfo payeeInfo = new PayeeInfo();
		//Optional<PayeeInfo> oPayeeInfo = Optional.empty();
		
		Optional<PayeeInfo> oPayeeInfo = Optional.of(payeeInfo);
		when(payeeInfoRepository.findByPayeeAccountNo("123")).thenReturn(oPayeeInfo);
		PayeeInfoVO payeeInfoVO = payeeInfoServiceImpl.findByPayeeAccountNo("123");
		assertNotNull(payeeInfoVO);
		assertEquals(null, payeeInfoVO.getCustomerId());
		assertEquals(null, payeeInfoVO.getId());
		verify(payeeInfoRepository,times(1)).findByPayeeAccountNo("123");
		verifyNoMoreInteractions(payeeInfoRepository);
	}	
	
	
}
