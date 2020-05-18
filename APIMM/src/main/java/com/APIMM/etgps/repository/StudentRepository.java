package com.APIMM.etgps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.APIMM.etgps.model.Student;
import com.APIMM.etgps.model.StudentId;

public interface StudentRepository extends JpaRepository<Student, StudentId> {

}
