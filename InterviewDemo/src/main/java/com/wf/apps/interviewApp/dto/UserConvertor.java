package com.wf.apps.interviewApp.dto;

import com.wf.apps.interviewApp.entity.User;

public class UserConvertor {
	
	public static User userDtoToUserConverted(UserDto userDto)
	{
		User user=new User();
		user.setEmail(userDto.getEmail());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setMobile(userDto.getMobile());
		return user;
	}
	
	public static UserDto userToUserDtoConverter(User user)
	{
		UserDto userDto=new UserDto();
		userDto.setUserId(user.getUserid());
		userDto.setEmail(user.getEmail());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setMobile(user.getMobile());
		
		return userDto;
	}

}
