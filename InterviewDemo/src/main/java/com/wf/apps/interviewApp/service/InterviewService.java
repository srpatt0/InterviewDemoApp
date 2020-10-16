package com.wf.apps.interviewApp.service;

import java.util.List;

import javax.validation.Valid;

import com.wf.apps.interviewApp.dto.InterviewAttendees;
import com.wf.apps.interviewApp.dto.InterviewCountDto;
import com.wf.apps.interviewApp.dto.InterviewDto;
import com.wf.apps.interviewApp.entity.Interview;

public interface InterviewService {
	
	public InterviewDto saveInterview(InterviewDto interviewDto);
	public InterviewDto addAttendees(String interviewName,Integer userId);
	public InterviewDto getAttendees(String intName);
	public InterviewDto getInterview(String technology);
	public InterviewDto deleteInterview(String technology);
	public List<InterviewDto> getAllInterviews();
	public InterviewDto modifyStatus(String interviewName, String status);
	public boolean isAttendeeAddedToInterview(String interviewName,Integer userId);
	public List<InterviewDto> getInterviewByInterviewor(String interviewerName);
	public boolean isExists(String fname, String lname);
	public boolean isInterviewPresent(String interviewName);
	
}
