package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.APIMM.mam.model.CheckHardware;


@Repository
public interface  CheckHardwareRepository  extends JpaRepository<CheckHardware, Integer> {

}
