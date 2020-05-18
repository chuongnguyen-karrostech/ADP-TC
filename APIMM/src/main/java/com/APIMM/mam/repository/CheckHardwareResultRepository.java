package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.APIMM.mam.model.CheckHardwareResult;

@Repository
public interface CheckHardwareResultRepository extends JpaRepository<CheckHardwareResult, Integer> , JpaSpecificationExecutor<CheckHardwareResult>{

}
