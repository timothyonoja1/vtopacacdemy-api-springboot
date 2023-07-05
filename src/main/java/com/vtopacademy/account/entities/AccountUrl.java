package com.vtopacademy.account.entities;

public class AccountUrl {
	
  private final String registerUrl;
  private final String loginUrl;
	
  public AccountUrl(String registerUrl, String loginUrl) {
	this.registerUrl = registerUrl;
	this.loginUrl = loginUrl;
  }

  public String getRegisterUrl() {
	return registerUrl;
  }

  public String getLoginUrl() {
	return loginUrl;
  }
	
}