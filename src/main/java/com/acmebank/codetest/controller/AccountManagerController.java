package com.acmebank.codetest.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.acmebank.codetest.exception.AcmeException;
import com.acmebank.codetest.request.GetBalanceRequest;
import com.acmebank.codetest.request.TransferBalanceRequest;
import com.acmebank.codetest.response.GetBalanceResponse;
import com.acmebank.codetest.response.TransferBalanceResponse;
import com.acmebank.codetest.service.AccountManagerService;

@RestController
@RequestMapping("account-manager")
public class AccountManagerController {
	
	@Autowired
	private AccountManagerService accountManagerService;
	
	@RequestMapping(value = "/getBalance", method = RequestMethod.POST)
	public @ResponseBody GetBalanceResponse getBalance(HttpServletRequest servletRequest, HttpServletResponse servletResponse, @RequestBody GetBalanceRequest request) {
		GetBalanceResponse response = new GetBalanceResponse();
		try {
			BigDecimal balance = accountManagerService.getBalanceByAccountNo(request.getAccountNo());
			response.setBalance(balance);
		} catch (AcmeException e) {
			response.setSuccess(false);
			response.setErrorMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMessage("Unexpected exception occur");
		}
		return response;
	}
	
	@RequestMapping(value = "/transferBalance", method = RequestMethod.POST)
	public @ResponseBody TransferBalanceResponse transferBalance(HttpServletRequest servletRequest, HttpServletResponse servletResponse, @RequestBody TransferBalanceRequest request) {
		TransferBalanceResponse response = new TransferBalanceResponse();
		try {
			accountManagerService.transferBalance(request.getFromAccount(), request.getToAccount(), request.getTransferAmount());
		} catch (AcmeException e) {
			response.setSuccess(false);
			response.setErrorMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMessage("Unexpected exception occur");
		}
		return response;
	}
	

}
