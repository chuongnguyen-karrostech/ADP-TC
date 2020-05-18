package com.ADP.etext.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ADP.etext.model.DriverPayrollMessagebk;

@Repository
public interface DriverPayrollMessagebkRepository extends JpaRepository<DriverPayrollMessagebk, Long> {

}
