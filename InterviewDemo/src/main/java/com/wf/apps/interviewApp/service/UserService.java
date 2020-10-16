package com.wf.apps.interviewApp.service;

import java.util.List;
import java.util.Optional;

import com.wf.apps.interviewApp.dto.UserDto;
import com.wf.apps.interviewApp.entity.User;

public interface UserService {
	
	public UserDto addUserService(UserDto userDto);
	public UserDto deleteUserService(String mobile);
	public List<UserDto> getAllUsersService();
	//public boolean isUserPresent(Long mobile);
	public boolean isUserPresent(String mobile);
	public UserDto getUserById(Integer userId);	
	

}
