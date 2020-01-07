package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.IUserService;

@RestController
public class UserController {
	
	@Autowired IUserService userService;
//	@GetMapping("/cool-cars")
	
	
	@RequestMapping(value="/registration", method = RequestMethod.POST,headers="accept=application/json")
	public ResponseEntity<?> registration(@RequestBody String user,@RequestHeader String password){
		System.out.println("OK>>>GOT IT>>>");
		 String status=userService.registrationService(user,password);
		 return new ResponseEntity<>(status,HttpStatus.OK);
	}
	
	@RequestMapping(value="getUserData", method = RequestMethod.GET, headers = "accept=application/json")
	public ResponseEntity<?> getUserData(){
		String status = userService.getUserData();
		return new ResponseEntity<>(status,HttpStatus.OK);
	}
	@RequestMapping(value="login", method = RequestMethod.POST, headers="accept=application/json")
	public ResponseEntity<?> login(@RequestBody String user,@RequestHeader String password){
		String status=userService.login(user,password);
		return new ResponseEntity<>(status,HttpStatus.OK);
	}
	
//	##################################################################################################
	
	@RequestMapping(value="/registration_admin_tenant", method = RequestMethod.POST, headers="accept=application/json")
	public ResponseEntity<?> registration_(@RequestBody String object,@RequestHeader String password){
		String status=userService.registrationHousing(object,password);
		return new ResponseEntity<>(status,HttpStatus.OK);
	}
//	##################################################################################################
	@RequestMapping(value="login_admin_tenant", method = RequestMethod.GET, headers="accept=application/json")
	public ResponseEntity<?> login_(@RequestParam String email,@RequestParam String registrationType,@RequestHeader String password){
		String status=userService.login_(email,registrationType,password);
		return new ResponseEntity<>(status,HttpStatus.OK);
	}
}
