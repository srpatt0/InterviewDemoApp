package com.wf.apps.interviewApp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wf.apps.interviewApp.dto.UserConvertor;
import com.wf.apps.interviewApp.dto.UserDto;
import com.wf.apps.interviewApp.entity.User;
import com.wf.apps.interviewApp.exceptions.UserAlreadyPresentException;
import com.wf.apps.interviewApp.repository.InterviewRepository;
import com.wf.apps.interviewApp.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userrepository;
	
	@Autowired
	InterviewRepository interviewrepository;
	
	@Override
	public UserDto addUserService(UserDto userDto) {
		// TODO Auto-generated method stub
		Optional<User> existingUser =  userrepository.findUserByFirstNameandLastName(userDto.getFirstName(), userDto.getLastName());
		if(!existingUser.isPresent())
		{
		User user=UserConvertor.userDtoToUserConverted(userDto);
		return UserConvertor.userToUserDtoConverter(userrepository.save(user));
		}else {
			throw new UserAlreadyPresentException("Record with given 'FirstName' and 'LastName' already exists"); 
		}
	}

	@Override
	public UserDto deleteUserService(String mobile) {
		// TODO Auto-generated method stub
		User user=userrepository.findByMobile(mobile);
		if(user==null)
			return null;
		user.getInterviews().forEach(u->u.getUsers().remove(user));
		interviewrepository.saveAll(user.getInterviews());
		userrepository.deleteByMobile(mobile);
		return UserConvertor.userToUserDtoConverter(user);
	}

	@Override
	public List<UserDto> getAllUsersService() {
		
		List<UserDto> list=new ArrayList<UserDto>();
		// TODO Auto-generated method stub
		//System.out.println(userrepository.findAll().toString());
		for(User user:userrepository.findAll())
			list.add(UserConvertor.userToUserDtoConverter(user));
			
		return list;//Arrays.asList(userrepository.findByMobile("9505911111"));
	}

	@Override
	public boolean isUserPresent(String mobile) {
		// TODO Auto-generated method stub
		if(userrepository.findByMobile(mobile)==null)
			return false;
		return true;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		User user=userrepository.findById(userId).orElse(null);
		if(user==null)
			return null;
		return UserConvertor.userToUserDtoConverter(user);
	}



}
