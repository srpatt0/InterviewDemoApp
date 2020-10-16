package com.wf.apps.interviewApp.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.wf.apps.interviewApp.entity.Interview;

public class InterviewConvertor {
	
	public static Interview interviewDtoToInterviewConvertor(InterviewDto interviewDto) {
		
		Interview interview=new Interview();
		
		interview.setDate(LocalDate.now());
		interview.setTime(LocalTime.now());
		interview.setInterviewerName(interviewDto.getInterviewerName());
		interview.setInterviewName(interviewDto.getInterviewName());
		interview.setInterviewStatus(interviewDto.getInterviewStatus());
		interview.setRemarks(interviewDto.getRemarks());
		
		for(String skill:interviewDto.getUserSkills()) {
			
			if(interview.getUserSkills()==null)
				interview.setUserSkills(skill);
			else
				interview.setUserSkills(interview.getUserSkills()+","+skill);
				
		}
		
		
		return interview;
	}
	
	public static InterviewDto interviewToInterviewDtoConvertor(Interview interview)
	{
		InterviewDto interviewDto=new InterviewDto();
		String[] skillset=interview.getUserSkills().split(",");
		
		interviewDto.setInterviewerName(interview.getInterviewerName());
		interviewDto.setInterviewId(interview.getInterviewId());
		interviewDto.setInterviewName(interview.getInterviewName());
		interviewDto.setInterviewStatus(interview.getInterviewStatus());
		interviewDto.setRemarks(interview.getRemarks());
		interviewDto.setTime(interview.getTime());
		interviewDto.setDate(interview.getDate());
		
		
		interviewDto.setUserSkills(Arrays.asList(skillset));
		interviewDto.setUsers(interview.getUsers());
		
		
		return interviewDto;
	}

}
