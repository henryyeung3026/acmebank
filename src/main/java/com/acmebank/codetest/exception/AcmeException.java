package com.acmebank.codetest.exception;

public class AcmeException extends Exception {
	
	private static final long serialVersionUID = -6568105382186613371L;
	
	public AcmeException(String errorMessage) {
		super(errorMessage);
	}

}
