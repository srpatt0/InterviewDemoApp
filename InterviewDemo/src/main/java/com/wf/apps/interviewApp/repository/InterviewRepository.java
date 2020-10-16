package com.wf.apps.interviewApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wf.apps.interviewApp.entity.Interview;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Integer>{
	
	@Query(value = "select * from interview_table where interview_name=?1",nativeQuery = true)
	public Interview findByInterviewName(String technology);
	
	@Query(value = "select * from interview_table where interviewer_name=?1",nativeQuery = true)
	public List<Interview> findAllByInterviewerName(String name);
	
	@Query(value = "delete from interview_table where interview_name=?1",nativeQuery = true)
	public Integer deleteByInterviewName(String technology);
	
}
