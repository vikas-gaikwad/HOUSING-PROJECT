package com.example.demo.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.HousingSocietyMainObject;
import com.example.demo.domain.User;
import com.example.demo.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService{
	@Autowired(required=true)
	IUserRepository userRepository;

	@Override
	public String registrationService(String user,String password) {
		User userPojo= new User();
		String userObj=null;
		JSONObject obj= new JSONObject(user);
		String emailId=obj.getString("email_id");
		String check_email_registered=userRepository.checkAlreadyRegistered(emailId);
		System.out.println(check_email_registered);
		JSONObject reponseJson=new JSONObject(check_email_registered);
		String reason=reponseJson.getString("reason");
		if(reason.equals("Email_not_found")) {
			String first_name=obj.getString("first_name");
			String last_name = obj.getString("last_name");
			String gender= obj.getString("gender");
			String contact_number=obj.getString("contact_number");
			String email_id=obj.getString("email_id");
			userPojo.setFirst_name(first_name);
			userPojo.setLast_name(last_name);
			userPojo.setGender(gender);
			userPojo.setContact_number(contact_number);
			userPojo.setEmail_id(email_id);			
			userObj=userRepository.register(userPojo,password);
			return userObj;
		}
		else{
			return check_email_registered;
		}
		
	}

	@Override
	public String getUserData() {
		
		return userRepository.getUserData();
	}

	@Override
	public String login(String user, String password) {
		// TODO Auto-generated method stub
		return userRepository.login(user,password);
	}

	@Override
	public String registrationHousing(String object, String password) {
		
		HousingSocietyMainObject obj= new HousingSocietyMainObject();
		JSONObject json=new JSONObject(object);	
		obj.setFirst_name(json.getString("first_name"));
		obj.setLast_name(json.getString("last_name"));
		obj.setEmail(json.getString("email"));
		obj.setMobile1(json.getString("mobile1"));
		obj.setMobile2(json.getString("mobile2"));
		obj.setGender(json.getString("gender"));
		obj.setAddress(json.getString("address"));
		obj.setRegistration_type(json.getString("registration_type"));
//		JSONArray jArr=json.getJSONArray("house_info");
		
		
		
		return userRepository.registrationHousing(obj,password);
	}

	@Override
	public String login_(String email, String registrationType, String password) {
		HousingSocietyMainObject obj= new HousingSocietyMainObject();
		obj.setEmail(email);
		obj.setRegistration_type(registrationType);
		obj.setPassword(password);
		return userRepository.login_(obj);
	}

}
