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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wf.apps.interviewApp.dto.InterviewAttendees;
import com.wf.apps.interviewApp.dto.InterviewConvertor;
import com.wf.apps.interviewApp.dto.InterviewCountDto;
import com.wf.apps.interviewApp.dto.InterviewDto;
import com.wf.apps.interviewApp.dto.UserDto;
import com.wf.apps.interviewApp.entity.Interview;
import com.wf.apps.interviewApp.exceptions.CustomExceptions;
import com.wf.apps.interviewApp.exceptions.UserAlreadyAddedExcecption;
import com.wf.apps.interviewApp.service.InterviewService;
import com.wf.apps.interviewApp.service.UserService;


@RestController
public class InterviewController {
	
	@Autowired
	UserService userservice;
	@Autowired
	InterviewService interviewservice;	

	//interview ops
	
	@PostMapping("/interviews")
	public ResponseEntity<InterviewDto> addInterview(@Valid @RequestBody InterviewDto interviewDto,BindingResult result) {
		
	
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
		
		if(interviewDto.getUserSkills() == null || interviewDto.getUserSkills().size() == 0 || interviewDto.getUserSkills().contains(null) || interviewDto.getUserSkills().contains(""))
		{
			throw new CustomExceptions(LocalDateTime.now(),"Interview Skills Error","Interview Skills should'nt be null or empty or blank");
		}
		return new ResponseEntity<InterviewDto>(interviewservice.saveInterview(interviewDto),HttpStatus.OK);
	}
		
	
	@GetMapping("/getAttendeesByInterviewName/{name}")
	public ResponseEntity<InterviewDto> getAttendees(@PathVariable("name") String intName)
	{
		if(interviewservice.getInterview(intName)==null)
			throw new CustomExceptions(LocalDateTime.now(),"Interview Exception","Interview doesnt exist");
		
		return new ResponseEntity<InterviewDto>(interviewservice.getAttendees(intName),HttpStatus.OK);
	}
	
	@GetMapping("/searchInterviewByInterviewName/{interviewName}")
	public ResponseEntity<InterviewDto> searchInterviewByName(@PathVariable("interviewName") String interviewName) {
		InterviewDto interview=interviewservice.getInterview(interviewName);
		if(interview==null)
			throw new CustomExceptions(LocalDateTime.now(),"Interview Exception","Interview doesnt exist");
		
		return new ResponseEntity<InterviewDto>(interview,HttpStatus.OK);
	}
	
	@GetMapping("/searchInterviewByInterviewerName/{interviewerName}")
	public ResponseEntity<List<InterviewDto>> searchInterview(@PathVariable("interviewerName") String interviewerName) {
		List<InterviewDto> interviews=interviewservice.getInterviewByInterviewor(interviewerName);
		if(interviews==null)
			throw new CustomExceptions(LocalDateTime.now(),"Interview Exception","Interview doesnt exist");
		
		return new ResponseEntity<List<InterviewDto>>(interviews,HttpStatus.OK);
	}
	
	
	@GetMapping("/interviews")
	public ResponseEntity<List<InterviewDto>> searchAllInterviews()
	{
		return new ResponseEntity<List<InterviewDto>>(interviewservice.getAllInterviews(),HttpStatus.OK);
	}
	
	@PutMapping("/modifyStatus/{interviewName}/{status}")
	public ResponseEntity<InterviewDto> modifyInterviewStatus(@PathVariable("interviewName") String interviewName,@PathVariable("status") String status)
	{
		InterviewDto interview=interviewservice.getInterview(interviewName);
		if(interview==null)
			throw new CustomExceptions(LocalDateTime.now(),"Interview Exception","Interview doesnt exist");
		return new ResponseEntity<InterviewDto>(interviewservice.modifyStatus(interviewName,status),HttpStatus.OK);
	}
	
	@DeleteMapping("/removeInterview/{interviewName}")
	public ResponseEntity<InterviewDto> removeInterview(@PathVariable("interviewName") String interviewName) {
		
		if(searchInterview(interviewName)==null)
			if(!interviewservice.isInterviewPresent(interviewName))
			throw new CustomExceptions(LocalDateTime.now(),"Interview Exception", interviewName+" Interview already Deleted Or does'nt exist");
		return new ResponseEntity<InterviewDto>(interviewservice.deleteInterview(interviewName),HttpStatus.OK);
	}
		
	
	@GetMapping("/addAttendees/{interviewName}/{userId}")
	public ResponseEntity<InterviewDto> addAttendees(@PathVariable("interviewName") String interviewName,@PathVariable("userId") Integer userId)
	{

		if(interviewservice.getInterview(interviewName)==null || userservice.getUserById(userId)==null)
			throw new CustomExceptions(LocalDateTime.now(),"Association Exception","Inerview or user doesnt exist");
		
		if(interviewservice.isAttendeeAddedToInterview(interviewName,userId))
			throw new CustomExceptions(LocalDateTime.now(),"Users Exception","One or more of the user Ids in the request are already added to the "+interviewName+" Interview");
		return new ResponseEntity<InterviewDto>(interviewservice.addAttendees(interviewName,userId),HttpStatus.OK);
	}
	
}
