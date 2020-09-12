package com.rab3tech.customer.ui.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rab3tech.customer.service.AddressService;
import com.rab3tech.customer.service.CustomerAccountService;
import com.rab3tech.customer.service.CustomerEnquiryService;
import com.rab3tech.customer.service.CustomerService;
import com.rab3tech.customer.service.FundTransferService;
import com.rab3tech.customer.service.LoginService;
import com.rab3tech.customer.service.PayeeInfoService;
import com.rab3tech.customer.service.SecurityQuestionService;
import com.rab3tech.email.service.EmailService;
import com.rab3tech.vo.AddressVO;
import com.rab3tech.vo.ChangePasswordVO;
import com.rab3tech.vo.CustomerAccountInfoVO;
import com.rab3tech.vo.CustomerSavingVO;
import com.rab3tech.vo.CustomerSecurityQueAnsVO;
import com.rab3tech.vo.CustomerVO;
import com.rab3tech.vo.EmailVO;
import com.rab3tech.vo.FundTransferVO;
import com.rab3tech.vo.LoginVO;
import com.rab3tech.vo.PayeeInfoVO;
import com.rab3tech.vo.RequestVO;
import com.rab3tech.vo.SecurityQuestionsVO;

/**
 * 
 * @author nagendra This class for customer GUI
 *
 */
@Controller
public class CustomerUIController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerUIController.class);

	@Autowired
	private CustomerEnquiryService customerEnquiryService;

	@Autowired
	private CustomerAccountService customerAccountService;

	@Autowired
	private SecurityQuestionService securityQuestionService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private PayeeInfoService payeeInfoService;

	@Autowired
	private FundTransferService fundTransferService;

	@GetMapping("/customer/validateEmail")
	public String getValidateEmail() {
		return "redirect:/customer/validateEmail";
	}

	@PostMapping("/customer/validateEmail")
	public String validateEmail(String email, Model model, HttpSession session) {
		String viewName = "customer/login";
		CustomerVO customerVO = customerService.findByEmail(email);
		if (customerVO != null) {
			CustomerSecurityQueAnsVO customerSecurityQueAnsVO = customerAccountService.getCustomerSecurityQA(email);
			session.setAttribute("customerSecurityQueAnsVO", customerSecurityQueAnsVO);
			model.addAttribute("customerSecurityQueAnsVO", customerSecurityQueAnsVO);
			viewName = "/customer/askSecurityQuestion"; // securityQuestion.html
		} else {
			model.addAttribute("message", "Email not valid. Try Again.");
			viewName = "customer/login";
		}
		return viewName;
	}

	@PostMapping("/customer/validateAnswers")
	public String validateSecurityAnswers(
			@ModelAttribute("customerSecurityQueAnsVO") CustomerSecurityQueAnsVO customerSecurityQueAnsVO,
			String answer1, String answer2, Model model, HttpSession session) {

		String viewName = "customer/askSecurityQuestion";

		if (customerSecurityQueAnsVO.getSecurityQuestionAnswer1().equalsIgnoreCase(answer1)
				&& customerSecurityQueAnsVO.getSecurityQuestionAnswer2().equalsIgnoreCase(answer2)) {
			model.addAttribute("customerSecurityQueAnsVO", customerSecurityQueAnsVO);
			viewName = "customer/changeForgottenPassword";
		} else {
			model.addAttribute("error", "Your answers do not match.");
			model.addAttribute("customerSecurityQueAnsVO", session.getAttribute("customerSecurityQueAnsVO"));
			viewName = "customer/askSecurityQuestion";
		}
		return viewName;
	}

	@PostMapping("/customer/changeForgottenPassword")
	public String changeForgottenPassword(
			@ModelAttribute("customerSecurityQueAnsVO") CustomerSecurityQueAnsVO customerSecurityQueAnsVO, Model model,
			String newPassword, String confirmPassword, HttpSession session) {

		ChangePasswordVO changePasswordVO = new ChangePasswordVO();
		changePasswordVO.setLoginid(customerSecurityQueAnsVO.getLoginid());
		if (newPassword.equals(confirmPassword)) {
			changePasswordVO.setNewPassword(newPassword);
			customerAccountService.changeForgottenPassword(changePasswordVO);
			model.addAttribute("message", "Password Updated.");
		} else {
			model.addAttribute("error", "Sorry , your new password and confirm passwords are not same!");
			return "customer/changeForgottenPassword"; // login.html
		}
		return "customer/login";
	}

	@PostMapping("/customer/changePassword")
	public String saveCustomerQuestions(@ModelAttribute ChangePasswordVO changePasswordVO, Model model,
			HttpSession session) {
		LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO");
		String loginid = loginVO2.getUsername();
		changePasswordVO.setLoginid(loginid);
		String viewName = "customer/dashboard";
		boolean status = loginService.checkPasswordValid(loginid, changePasswordVO.getCurrentPassword());
		if (status) {

			if (changePasswordVO.getNewPassword().equals(changePasswordVO.getConfirmPassword())) {
				// we need to check the string of the Password send
				// if the condition matches, go in
				viewName = "customer/dashboard";
				loginService.changePassword(changePasswordVO);
				// else send error message and change viewName -->
				// "redirect:/customer/changePassword"
			} else {
				model.addAttribute("error", "Sorry , your new password and confirm passwords are not same!");
				return "customer/login"; // login.html
			}
		} else {
			model.addAttribute("error", "Sorry , your username and password are not valid!");
			return "customer/login"; // login.html
		}
		return viewName;
	}

	@PostMapping("/customer/securityQuestion")
	public String saveCustomerQuestions(
			@ModelAttribute("customerSecurityQueAnsVO") CustomerSecurityQueAnsVO customerSecurityQueAnsVO, Model model,
			HttpSession session) {
		LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO");
		String loginid = loginVO2.getUsername();
		customerSecurityQueAnsVO.setLoginid(loginid);
		securityQuestionService.save(customerSecurityQueAnsVO);
		//
		return "customer/chagePassword";
	}

	// http://localhost:444/customer/account/registration?cuid=1585a34b5277-dab2-475a-b7b4-042e032e8121603186515
	@GetMapping("/customer/account/registration")
	public String showCustomerRegistrationPage(@RequestParam String cuid, Model model) {

		logger.debug("cuid = " + cuid);
		Optional<CustomerSavingVO> optional = customerEnquiryService.findCustomerEnquiryByUuid(cuid);
		CustomerVO customerVO = new CustomerVO();

		if (!optional.isPresent()) {
			return "customer/error";
		} else {
			// model is used to carry data from controller to the view =- JSP/
			CustomerSavingVO customerSavingVO = optional.get();
			customerVO.setEmail(customerSavingVO.getEmail());
			customerVO.setName(customerSavingVO.getName());
			customerVO.setMobile(customerSavingVO.getMobile());
			customerVO.setAddress(customerSavingVO.getLocation());
			customerVO.setToken(cuid);
			logger.debug(customerSavingVO.toString());
			// model - is hash map which is used to carry data from controller to
			// thymeleaf!!!!!
			// model is similar to request scope in jsp and servlet
			model.addAttribute("customerVO", customerVO);
			return "customer/customerRegistration"; // thyme leaf
		}
	}

	@PostMapping("/customer/account/registration")
	public String createCustomer(@ModelAttribute CustomerVO customerVO, Model model) {
		// saving customer into database
		logger.debug(customerVO.toString());
		customerVO = customerService.createAccount(customerVO);
		// Write code to send email
		EmailVO mail = new EmailVO(customerVO.getEmail(), "javahunk2020@gmail.com",
				"Regarding Customer " + customerVO.getName() + "  userid and password", "", customerVO.getName());
		mail.setUsername(customerVO.getUserid());
		mail.setPassword(customerVO.getPassword());
		emailService.sendUsernamePasswordEmail(mail);
		System.out.println(customerVO);
		model.addAttribute("loginVO", new LoginVO());
		model.addAttribute("message", "Your account has been setup successfully , please check your email.");
		return "customer/login";
	}

	@GetMapping(value = { "/customer/account/enquiry", "/", "/mocha", "/welcome" })
	public String showCustomerEnquiryPage(Model model) {
		CustomerSavingVO customerSavingVO = new CustomerSavingVO();
		// model is map which is used to carry object from controller to view
		model.addAttribute("customerSavingVO", customerSavingVO);
		return "customer/customerEnquiry"; // customerEnquiry.html
	}

	@PostMapping("/customer/account/enquiry")
	public String submitEnquiryData(@ModelAttribute CustomerSavingVO customerSavingVO, Model model) {
		boolean status = customerEnquiryService.emailNotExist(customerSavingVO.getEmail());
		logger.info("Executing submitEnquiryData");
		if (status) {
			CustomerSavingVO response = customerEnquiryService.save(customerSavingVO);
			logger.debug("Hey Customer , your enquiry form has been submitted successfully!!! and appref "
					+ response.getAppref());
			model.addAttribute("message",
					"Hey Customer , your enquiry form has been submitted successfully!!! and appref "
							+ response.getAppref());
		} else {
			model.addAttribute("message", "Sorry , this email is already in use " + customerSavingVO.getEmail());
		}
		return "customer/success"; // customerEnquiry.html

	}

	@GetMapping("/customer/profileDetails")
	public String customerProfileDetails(Model model, HttpSession session) {

		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		String loginid = loginVO.getUsername();

		CustomerVO customerVO = customerService.findByEmail(loginid);

		if (customerVO != null) {
			// customerVO.setUserid(loginid);
			model.addAttribute("customerVO", customerVO);
			return "customer/showCustomerProfile";
		} else {
			return "customer/error";

		}
	}

	@GetMapping("/customer/editProfile")
	public String editCustomerProfile(HttpSession session, Model model) {
		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		String loginid = loginVO.getUsername();
		CustomerVO customerVO = customerService.findByEmailWithSecurityQA(loginid);
		List<SecurityQuestionsVO> questionsVOs = securityQuestionService.findAll();
		if (customerVO != null) {
			customerVO.setUserid(loginid);
			model.addAttribute("customerVO", customerVO);
			model.addAttribute("questionsVOs", questionsVOs);
			return "customer/editCustomerProfile";
		} else {
			return "customer/error";

		}

	}

	@PostMapping("/customer/updateCustomerProfile")
	public String updateCustomerProfile(@ModelAttribute CustomerVO customerVO, Model model, HttpSession session) {
		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		if (loginVO != null) {
			customerVO.setUserid(loginVO.getEmail());
			customerService.updateCustomerProfile(customerVO);
			model.addAttribute("message", "Your Profile has been updated successfully.");
		} else {
			model.addAttribute("message", "Please login first.");
		}
		return "customer/showCustomerProfile";
	}

	@GetMapping("/customers/deletePayee")
	public String deletePayee(String payeeAccountNo, Model model) {
		logger.info("deletePayee is called!!!");
		payeeInfoService.deleteByAccountNo(payeeAccountNo);
		return "redirect:/customer/showBeneficiaries";
	}

	@PostMapping("/customer/editPayee")
	public String editPayee(@ModelAttribute PayeeInfoVO payeeInfoVO,RedirectAttributes attributes, HttpSession session, Model model) {
		logger.info("editPayee is called!!!");
		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		String message = null;
		if (loginVO != null) {
			payeeInfoVO.setCustomerId(loginVO.getUsername());
			message = payeeInfoService.updatePayeeInfo(payeeInfoVO);
			attributes.addFlashAttribute("message", message);	
		}else {
			attributes.addFlashAttribute("error", "Please login again.");	
		}
		return "redirect:/customer/showBeneficiaries";
	}

	@GetMapping("/customer/showBeneficiaries")
	public String showCustomerBeneficiaries(HttpSession session, Model model) {
		logger.info("showCustomerBeneficiaries is called!!!");
		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		List<PayeeInfoVO> payeeList = payeeInfoService.findByCustomerId(loginVO.getUsername());
		model.addAttribute("payeeList", payeeList);
		return "customer/showBeneficiaries"; // customerEnquiryList.html
	}

	@GetMapping("/customer/addBeneficiary")
	public String addBeneficiary(HttpSession session, Model model) {
		LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO");
		boolean customerAccountExist = customerAccountService.getCustomerAccountApprovedByUsername(loginVO2.getUsername());
		String viewName = "/customer/dashboard";
		if (customerAccountExist) {
			// Setting the customerId to the PayeeInfoVO before passing it to the Model
			PayeeInfoVO payeeInfoVO = new PayeeInfoVO();
			payeeInfoVO.setCustomerId(loginVO2.getUsername());
			model.addAttribute("payeeInfoVO", payeeInfoVO);
			viewName = "/customer/addBeneficiary";
		} else {
			model.addAttribute("error", "Sorry, You are not Registered Saving account yet. Contact Bank.");
		}
		return viewName;
	}

	@PostMapping("/customer/addBeneficiary")
	public String addBeneficiaryPost(@ModelAttribute PayeeInfoVO payeeInfoVO, HttpSession session, Model model) {
		LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO");
		String message = null;
		boolean customerAccountExist = customerAccountService.getCustomerAccountApprovedByUsername(loginVO2.getUsername());
		String viewName = "/customer/addBeneficiary";
		// checking the customer who is trying to add the beneficiary has logged in or
		// not
		// and he/she is in good standing or not
		if (!customerAccountExist) {
			model.addAttribute("error", "Sorry, You are not Registered yet. Contact Bank.");
		} else {
			message = payeeInfoService.addPayee(payeeInfoVO);
			model.addAttribute("message", message);
			viewName = "redirect:/customer/showBeneficiaries";

		}
		return viewName;
	}

	@GetMapping("/customer/fundTransferPage")
	public String fundTransferPage(HttpSession session, Model model) {
		logger.info("fundTransferPage is called!!!");
		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		if (loginVO != null) {
			List<PayeeInfoVO> payeeList = payeeInfoService.findByCustomerId(loginVO.getUsername());
			model.addAttribute("payeeList", payeeList);
			return "customer/fundTransfer";
		} else {
			model.addAttribute("message", "Sorry, the fund transfer was not successful. Please login.");
			return "customer/login";
		}

	}

	@PostMapping("/customer/transferFund")
	public String transferFund(@ModelAttribute FundTransferVO fundTransferVO, HttpSession session, Model model) {
		logger.info("fundTransferPage is called!!!");
		String viewName = "redirect:/customer/fundTransferPage";
		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		boolean customerAccountExist = customerAccountService.getCustomerAccountApprovedByUsername(loginVO.getUsername());
		if (!customerAccountExist) {
			model.addAttribute("message", "Sorry, the fund transfer was not successful. Please try again.");
		} else {
			fundTransferVO.setCustomerId(loginVO.getUsername());
			String message = fundTransferService.processFundTransfer(fundTransferVO);
			
			model.addAttribute("message", message);
			viewName = "/customer/dashboard";

		}
		return viewName;
	}

	@GetMapping("/customer/showTransactions")
	public String showTransactions(HttpSession session, Model model) {
		logger.info("showTransactions is called!!!");
		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		String viewName = "customer/dashboard";
		if (loginVO != null) {
			List<FundTransferVO> transactionList = fundTransferService.findAllTransactionsByUsername(loginVO.getUsername());
			if (transactionList != null) {
				
				model.addAttribute("transactionList", transactionList);
				viewName = "customer/accountSummary";
			} else {
				model.addAttribute("message", "You do not have any transactions yet.");
				viewName = "customer/accountSummary";
			}
		} else {
			model.addAttribute("message", "Sorry, the fund transfer was not successful. Please login.");
		}
		return viewName;

	}

	@GetMapping("/customer/checkBookRequest")
	public String checkBookRequest(HttpSession session, Model model) {
		logger.info("checkBookRequest is called!!!");
		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		String viewName = "customer/dashboard";
		if (loginVO != null) {
			AddressVO addressVO = addressService.findByLoginid(loginVO.getUsername());
			if (addressVO != null) {
				List<CustomerAccountInfoVO> customerAccountInfoVOs = customerAccountService.findByLoginid(loginVO.getUsername());
				model.addAttribute("customerAccountInfoVOs", customerAccountInfoVOs);
				model.addAttribute("addressVO", addressVO);
				viewName = "customer/showAddressSendEnquiry";
			} else {
				viewName = "customer/updateAddress";
			}
		}
		return viewName;
	}
	@PostMapping("/customer/raiseRequest")
	public String raiseRequest(@ModelAttribute RequestVO requestVO, HttpSession session, Model model) {
		logger.info("addressUpdate is called!!!");
		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		if (loginVO != null) {
		}
		return null;
		}
			


	@PostMapping("/customer/addressUpdate")
	public String addressUpdate(@ModelAttribute AddressVO addressVO, HttpSession session, Model model) {
		logger.info("addressUpdate is called!!!");
		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		if (loginVO != null) {
			addressVO.setUserid(loginVO.getUsername());
			String message = addressService.addressUpdate(addressVO);
			model.addAttribute("message", message);
			return "redirect:/customer/dashboard";
		} else {
			model.addAttribute("message", "Please login for update.");
			return "customer/login";
		}
	}

}


