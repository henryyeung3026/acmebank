package com.acmebank.codetest.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.acmebank.codetest.model.Account;

@Repository
public class AccountManagerDao {
	
	private final static String SELECT_ACCOUNT = "select * from ACCOUNT where ACCOUNT_NO = :accountNo";
	
	private final static String SELECT_ACCOUNT_FOR_UPDATE = SELECT_ACCOUNT + " for update";
	
	private final static String UPDATE_ACCOUNT_BALANCE = "update ACCOUNT set ACCOUNT_BALANCE = ACCOUNT_BALANCE + :deposit where ACCOUNT_NO = :accountNo";
	
	@PersistenceContext
	protected EntityManager em;
	
	public Account selectAccount(String accountNo, boolean forUpdate) {
		Query query = em.createNativeQuery(forUpdate ? SELECT_ACCOUNT_FOR_UPDATE : SELECT_ACCOUNT, Account.class);
		query.setParameter("accountNo", accountNo);
		List<Account> account = query.getResultList();
		if (account.size() == 0) {
			return null;
		} else {
			return account.get(0); // Assume only one result because accountNo is primary key
		}
	}
	
	public boolean updateAccountBalance(String accountNo, BigDecimal deposit) {
		Query query = em.createNativeQuery(UPDATE_ACCOUNT_BALANCE);
		query.setParameter("deposit", deposit);
		query.setParameter("accountNo", accountNo);
		int result = query.executeUpdate();
		if (result <= 0) {
			return false;
		}
		return true;
	}
}
