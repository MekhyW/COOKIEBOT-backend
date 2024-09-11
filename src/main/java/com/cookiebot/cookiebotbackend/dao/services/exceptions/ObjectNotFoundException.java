package com.cookiebot.cookiebotbackend.dao.services.exceptions;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6115813064362188340L;

	public ObjectNotFoundException() {
		super("Object Not Found");
	}
	
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
}
