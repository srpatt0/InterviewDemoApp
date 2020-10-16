package com.wf.apps.interviewApp.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wf.apps.interviewApp.dto.UserDto;
import com.wf.apps.interviewApp.exceptions.CustomExceptions;
import com.wf.apps.interviewApp.service.InterviewService;
import com.wf.apps.interviewApp.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userservice;
	@Autowired
	InterviewService interviewservice;
		
	
	@PostMapping("/users")
	public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto,BindingResult result) {
		
		if(result.hasErrors())
		{
			CustomExceptions exception=new CustomExceptions();
			for(FieldError err:result.getFieldErrors())
			{
				if(exception.getErrorMessage()==null) {
				exception.setErrorCode(err.getCode());
				exception.setErrorMessage(err.getDefaultMessage());
				}
				else
				{
					exception.setErrorCode(exception.getErrorCode()+" || "+err.getCode());
					exception.setErrorMessage(exception.getErrorMessage()+" || "+err.getDefaultMessage());					
				}
			}
			exception.setErrorTimeStamp(LocalDateTime.now());
			throw exception;
		}
		
		return new ResponseEntity<UserDto>(userservice.addUserService(userDto),HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{mobile}")
	public ResponseEntity<UserDto> deleteUser(@PathVariable("mobile") String mobile) {
		if(!userservice.isUserPresent(mobile))
			throw new CustomExceptions(LocalDateTime.now(),"Users Exception", "User with Mobile "+mobile+" already Deleted or user does'nt exist");
		return new ResponseEntity<UserDto>(userservice.deleteUserService(mobile),HttpStatus.OK);
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUsers(){ 
		return new ResponseEntity<List<UserDto>>(userservice.getAllUsersService(),HttpStatus.OK);
	}
	


}
