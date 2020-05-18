package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.APIMM.mam.model.BillingActivity;

@Repository
public interface BillingActivityRepo  extends JpaRepository<BillingActivity, Integer>{

}
