package com.wf.apps.interviewApp.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wf.apps.interviewApp.dto.InterviewAttendees;
import com.wf.apps.interviewApp.dto.InterviewConvertor;
import com.wf.apps.interviewApp.dto.InterviewCountDto;
import com.wf.apps.interviewApp.dto.InterviewDto;
import com.wf.apps.interviewApp.entity.Interview;
import com.wf.apps.interviewApp.entity.User;
import com.wf.apps.interviewApp.repository.InterviewRepository;
import com.wf.apps.interviewApp.repository.UserRepository;

@Service
@Transactional
public class InterviewServiceImpl implements InterviewService{

	@Autowired
	public InterviewRepository interviewrepository;
	@Autowired
	public UserRepository userrepository;
	
	@Override
	public InterviewDto saveInterview(InterviewDto interviewDto) {
		// TODO Auto-generated method stub
		Interview interview=InterviewConvertor.interviewDtoToInterviewConvertor(interviewDto);
		interview.setDate(LocalDate.now());
		interview.setTime(LocalTime.now());
		interviewrepository.save(interview); 
		return InterviewConvertor.interviewToInterviewDtoConvertor(interview);
		
	}

	@Override
	public InterviewDto addAttendees(String interviewName,Integer userId) {
		// TODO Auto-generated method stub
		User user;		
		Interview interview=interviewrepository.findByInterviewName(interviewName);
		List<User> interviewusers=interview.getUsers();
		if(interviewusers==null)
			interviewusers=new ArrayList<User>();
			user=userrepository.findById(userId).orElse(null);
			interviewusers.add(user);
		interview.setUsers(interviewusers);
		interviewrepository.save(interview);
		return InterviewConvertor.interviewToInterviewDtoConvertor(interview);
	}

	@Override
	public InterviewDto getAttendees(String intName) {
		// TODO Auto-generated method stub
		return InterviewConvertor.interviewToInterviewDtoConvertor(interviewrepository.findByInterviewName(intName));
	}

	@Override
	public InterviewDto getInterview(String technology) {
		// TODO Auto-generated method stub	
		if(interviewrepository.findByInterviewName(technology)==null)
			return null;
		return InterviewConvertor.interviewToInterviewDtoConvertor(interviewrepository.findByInterviewName(technology));
	}
	@Override
	public List<InterviewDto> getInterviewByInterviewor(String name) {
		// TODO Auto-generated method stub	
		List<InterviewDto> list= new ArrayList<InterviewDto>();
		if(interviewrepository.findAllByInterviewerName(name)==null)
			return null;
		for(Interview interview:interviewrepository.findAllByInterviewerName(name))
		{
			list.add(InterviewConvertor.interviewToInterviewDtoConvertor(interview));
		}
		return list;
	}
	
	@Override
	public InterviewDto deleteInterview(String technology) {
		// TODO Auto-generated method stub
		InterviewDto interviewDto=getInterview(technology);
		interviewrepository.deleteByInterviewName(technology);
		return interviewDto;
	}

	@Override
	public List<InterviewDto> getAllInterviews() {
		// TODO Auto-generated method stub
		List<InterviewDto> intList=new ArrayList<InterviewDto>();
		for(Interview item:interviewrepository.findAll()) {
			intList.add(InterviewConvertor.interviewToInterviewDtoConvertor(item));
		}
		return intList;
	}

	@Override
	public InterviewDto modifyStatus(String interviewName, String status) {
		// TODO Auto-generated method stub
		Interview interview=interviewrepository.findByInterviewName(interviewName);
		interview.setInterviewStatus(status);
		return InterviewConvertor.interviewToInterviewDtoConvertor(interview);
	}


	@Override
	public boolean isAttendeeAddedToInterview(String interviewName,Integer userId) {
		// TODO Auto-generated method stub
		Interview interview=interviewrepository.findByInterviewName(interviewName);
		boolean found=false;
		if(interview!=null)
		{
			List<User> users=interview.getUsers();
			for(User user:users) {
					if(userId==user.getUserid())
						found=true;				
			}
			
		}
		return found;
	}

	@Override
	public boolean isExists(String fname, String lname) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInterviewPresent(String interviewName) {
		// TODO Auto-generated method stub
		return false;
	}

}
