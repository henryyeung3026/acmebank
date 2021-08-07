package com.acmebank.codetest.response;

import java.math.BigDecimal;

public class GetBalanceResponse extends Response {
	
	private BigDecimal balance;

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
