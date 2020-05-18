package com.APIMM.etmain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.APIMM.etmain.model.StudentTagIdmappings;

public interface StudentTagIDMappingRepository extends JpaRepository<StudentTagIdmappings, Long> {
	@Query("select u from StudentTagIdmappings u where u.edulogStudentId = ?1 and (u.endDate is null or u.endDate > current_date()) order by u.id desc")
	List<StudentTagIdmappings> findByEdulogStudentId(String studentId);
	
	@Query("select u from StudentTagIdmappings u where u.tagId = ?1 and (u.endDate is null or u.endDate > current_date()) order by u.id desc")	
	List<StudentTagIdmappings> findBytagId(String tagId);	
	
}