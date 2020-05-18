package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.APIMM.mam.model.NoLocationIssue;

public interface NoLocationIssueRepository extends JpaRepository<NoLocationIssue, Integer>, JpaSpecificationExecutor<NoLocationIssue> {

}
