package com.example.demo.service;

public interface IUserService {

	public String registrationService(String user, String password) ;

	public String getUserData();

	public String login(String user, String password);

	public String registrationHousing(String object, String password);

	public String login_(String email, String registrationType, String password);

}
