package com.example.demo.repository;

import com.example.demo.domain.HousingSocietyMainObject;
import com.example.demo.domain.User;

public interface IUserRepository {

	String checkAlreadyRegistered(String emailId);

	String register(User userPojo, String password);

	String getUserData();

	String login(String user, String password);

	String registrationHousing(HousingSocietyMainObject obj, String password);

	String login_(HousingSocietyMainObject obj);



}
