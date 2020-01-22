package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.HousingSocietyMainObject;
import com.example.demo.domain.User;

@Repository
public class UserRepositoryImpl implements IUserRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String register(User user, String password) {
		int count = 0;

		String registrationQuery = "insert into registration "
				+ " ( "
				+ " first_name , "
				+ " last_name , "
				+ " gender , "
				+ " contact_number , "
				+ " email_id , "
				+ " password "
				+ " ) values "
				+ " ( "
				+ " ? , "
				+ " ? , "
				+ " ? , "
				+ " ? , "
				+ " ? , "
				+ " ? "
				+ " )";
		try {


			count = jdbcTemplate.update(registrationQuery, user.getFirst_name(), user.getLast_name(), user.getGender(),
					user.getContact_number(), user.getEmail_id(), password);
			System.out.println(count);
		} catch (Exception e) {
			e.printStackTrace();
			count = 0;
		}

		if (count == 0) {
//			throw new CustomException("REGISTRATION FAILS.");
			return "{\"status\": \"Fail\",\"reason\": \"Registration Fail\"}";
		} else {
			return "{\"status\": \"Success\",\"reason\": \"Registration Success\"}";
		}
//		return null;
	}

	@Override
	public String checkAlreadyRegistered(String emailId) {
		String checkEmail = null;
		try {
			String checkAlreadyRegistered = "select id from `housing_registration` where email =? order by createdAt desc limit 1";
			checkEmail = jdbcTemplate.queryForObject(checkAlreadyRegistered, new Object[] { emailId }, String.class);
			System.out.println(checkEmail);
		} catch (Exception e) {
//			e.printStackTrace();
			checkEmail = null;
		}
		if (checkEmail == null) {
			return "{\"status\": \"Fail\",\"reason\": \"Email_not_found\",\"response\": \"Registration Success\"}";
		} else {
			return "{\"status\": \"Success\",\"reason\": \"Email_found\",\"response\": \"Registration Fail, Email Already Registered.\"}";
		}

	}

	@Override
	public String getUserData() {
		String GET_USER_QUERY="select * from registration";
		List<Map<String, Object>> userData= jdbcTemplate.queryForList(GET_USER_QUERY);
		JSONObject json= new JSONObject();
		if(userData.size() >=0) {
			try {
				json.put("user_info", userData);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return json.toString();
	}

	@Override
	public String login(String user, String password) {
		
		return null;
	}
	
	@Override
	@Transactional(rollbackFor= {Exception.class , RuntimeException.class})
	public String registrationHousing(HousingSocietyMainObject obj,String password) { 
		
		
		
		String status = null;
		int count=0;
		String checkEmail = null;
		String reg_id=null;
		String QUERY_FOR_INSERT_BASIC_INFO;
		String QUERY_FOR_GET_REGISTRATION_ID;
		String CHECK_IF_ALREADY_RESISTERED_ACCOUNT;
		
		
		
		CHECK_IF_ALREADY_RESISTERED_ACCOUNT="select email from housing_registration where email =? order by createdAt desc limit 1";
		try {
			checkEmail= jdbcTemplate.queryForObject(CHECK_IF_ALREADY_RESISTERED_ACCOUNT, new Object[] { obj.getEmail() }, String.class);
		} catch (Exception e) {
			checkEmail = null;
		}		
		System.out.println(checkEmail);
		
		
		
		
		if(checkEmail == null) {
			QUERY_FOR_INSERT_BASIC_INFO=" insert into housing_registration "
					+ " ( first_name , last_name , email , password , "
					+ " mobile1 , mobile2 , gender , "
					+ " address , registration_type ) "
					+ " values ( ? , ? , ? , ? , "
					+ " ? , ? , ? , "
					+ " ? , ? ) ";
			
			try {
				count=jdbcTemplate.update(QUERY_FOR_INSERT_BASIC_INFO, 
						obj.getFirst_name() , obj.getLast_name() , obj.getEmail() , password ,
						obj.getMobile1() , obj.getMobile2() , obj.getGender() , 
						obj.getAddress() , obj.getRegistration_type() );
			} catch (DataAccessException e) {
				count=0;
				e.printStackTrace();
			}
			
			if (count==0) {
				status= "{\"status\": \"Fail\",\"reason\": \"Basic Registration Fail\"}";
			}
			if (count==1) {
				status= "{\"status\": \"Success\",\"reason\": \"Basic Registration Success\"}";
			}
			
			/*
			 * else {
			 * 
			 * QUERY_FOR_GET_REGISTRATION_ID=" select resistration_id from housing_registration where email = ? order by createdAt desc limit 1"
			 * ; try { reg_id = jdbcTemplate.queryForObject(QUERY_FOR_GET_REGISTRATION_ID,
			 * new Object[] { obj.getEmail() }, String.class); } catch (Exception e) {
			 * reg_id=null; e.printStackTrace(); }
			 * System.out.println("REGISTRATION ID : "+reg_id); }
			 * 
			 * if(reg_id!=null) { int count_=0; String INSERT_INTO_REGISTERED_FLAT_DETAILS;
			 * for (int i = 0; i < jsonArray.length(); i++) { JSONObject objects =
			 * jsonArray.getJSONObject(i);
			 * INSERT_INTO_REGISTERED_FLAT_DETAILS=" insert into registered_flat_details " +
			 * " ( flat_no , building_or_house_or_tower_name , plot_no , " +
			 * "   landmark , sector , city , " + "   taluka , district , state , " +
			 * "   pin , resistraion_id ) " + "   values( ? , ? , ? , " + "   ? , ? , ? , "
			 * + "   ? , ? , ? , " + "   ? , ? ) ";
			 * 
			 * try { count_=jdbcTemplate.update(INSERT_INTO_REGISTERED_FLAT_DETAILS,
			 * objects.get("flat_no"),objects.get("building_or_house_or_tower_name"),objects
			 * .get("plot_no"),
			 * objects.get("landmark"),objects.get("sector"),objects.get("city"),
			 * objects.get("taluka"),objects.get("district"),objects.get("state"),
			 * objects.get("pin"),reg_id); } catch (Exception e) { count_=0;
			 * e.printStackTrace();
			 * 
			 * }
			 * 
			 * } if(count_==0) { status=
			 * "{\"status\": \"Fail\",\"reason\": \"Flat Registration Fail\"}"; } else {
			 * status=
			 * "{\"status\": \"Success\",\"reason\": \"Registration Successfully Done\"}"; }
			 * }
			 *
			*else {
			*	status= "{\"status\": \"Fail\",\"reason\": \"Basic Registration Fail\"}";
			}*/
		} else {
			status= "{\"status\": \"Fail\",\"reason\": \"Account With This Email ID Is Already Available\"}";
		}
	
		
		return status;
	}

	@Override
	public String login_(HousingSocietyMainObject obj) {
		String status=null;
		String CHECK_LOGIN_CREDENTIALS;
		String login_credentials=null;
		CHECK_LOGIN_CREDENTIALS="select id from housing_registration where email =? and password = ? and registration_type = ? order by createdAt desc limit 1";
		try {
			login_credentials= jdbcTemplate.queryForObject(CHECK_LOGIN_CREDENTIALS, new Object[] { obj.getEmail() , obj.getPassword(), obj.getRegistration_type() }, String.class);
			System.out.println(login_credentials);
		} catch (Exception e) {
			login_credentials = null;
		}	
		
		if(login_credentials != null) {
			String FOUND_INFO_OF_LOGIN_USER_FROM_registered_flat_details_TABLES;
//			String login_credentials_=null;
			List<Map<String, Object>>	login_credentials_ = null;
			FOUND_INFO_OF_LOGIN_USER_FROM_registered_flat_details_TABLES=
					"select * from  housing_registration where  id =?    limit 1";
			try {
				login_credentials_=jdbcTemplate.queryForList(FOUND_INFO_OF_LOGIN_USER_FROM_registered_flat_details_TABLES,  login_credentials);
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				System.out.println(">>>>"+login_credentials);
				JSONObject returnObj = new JSONObject();
				if(login_credentials_.size()>=0) {
					try {
						returnObj.put("userInfo", login_credentials_);
						returnObj.put("Status", "Success");
					} catch (Exception e) {
						e.printStackTrace();
						return status= "{\"Status\": \"Fail\",\"Reason\": \"LOGIN FAIL, ID & PASSWORD DO NOT MATCHED\"}";
						
					}
				}
				System.out.println(login_credentials_);
				return returnObj.toString();
			
			

		} else {
			status= "{\"Status\": \"Fail\",\"Reason\": \"LOGIN FAIL, ID & PASSWORD DO NOT MATCHED\"}";
		}
		
		return status;
	}
}
