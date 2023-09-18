package it.unisa.utils;

import javax.servlet.http.Cookie;

public class LongLivedCookie extends Cookie{
	
	public static final long serialVersionUID = 1L;
	public static final int SECOND_PER_YEAR = 60 * 60 * 24 * 365;
	
	
	public LongLivedCookie (String name, String value) {
		super(name, value);
		
		this.setMaxAge(SECOND_PER_YEAR);
	}

}
