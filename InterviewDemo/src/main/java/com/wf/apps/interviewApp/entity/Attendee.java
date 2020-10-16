/**
 * 
 */
package com.wf.apps.interviewApp.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author wellsfargofsd88
 *
 */
@Data
public class Attendee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private User us;
	
	private Interview inter;
	

}
