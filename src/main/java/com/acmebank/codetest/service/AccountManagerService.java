package com.acmebank.codetest.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acmebank.codetest.dao.AccountManagerDao;
import com.acmebank.codetest.exception.AcmeException;
import com.acmebank.codetest.model.Account;

@Service
public class AccountManagerService {
	
	private final static Logger logger = LoggerFactory.getLogger(AccountManagerService.class);
	
	@Autowired
	private AccountManagerDao accountManagerDao;
	
	public BigDecimal getBalanceByAccountNo(String accountNo) throws Exception {
		Account account = accountManagerDao.selectAccount(accountNo, false);
		if (account == null) {
			throw new AcmeException("Account does not exist");
		}
		return account.getAccountBalance();
	}
	
	@Transactional(rollbackFor = { Exception.class })
	public void transferBalance(String fromAccount, String toAccount, BigDecimal transferAmount) throws Exception {
		Account account = accountManagerDao.selectAccount(fromAccount, true);
		if (account == null) {
			throw new AcmeException("Account does not exist");
		}
		if (account.getAccountBalance().compareTo(transferAmount) == -1) {
			logger.debug("Transfer amount {} is larger than account balance {}", transferAmount, account.getAccountBalance());
			throw new AcmeException("Transfer amount is larger than account balance");
		}
		if (!accountManagerDao.updateAccountBalance(fromAccount, transferAmount.negate())) {
			throw new AcmeException("Update account balance fail for account " + fromAccount);
		}
		if (!accountManagerDao.updateAccountBalance(toAccount, transferAmount)) {
			throw new AcmeException("Update account balance fail for account " + toAccount);
		}
	}
	
	

}
