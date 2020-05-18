package com.APIMM.etgps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.APIMM.etgps.model.UnknownStudent;
import com.APIMM.mam.model.MamApplication;

public interface UnknownStudentRepository extends JpaRepository<UnknownStudent, Integer> {

}
