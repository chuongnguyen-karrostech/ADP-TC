package com.APIMM.ename.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.APIMM.ename.model.NameSubCategory;

@Repository
public interface NameSubCategoryRepo extends JpaRepository<NameSubCategory, String> {

}
