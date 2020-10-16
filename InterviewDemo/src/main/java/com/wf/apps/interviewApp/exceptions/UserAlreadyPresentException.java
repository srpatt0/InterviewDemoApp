package com.wf.apps.interviewApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class UserAlreadyPresentException extends RuntimeException {
	
	public UserAlreadyPresentException(String message)
	{
		super(message);
	}

}
