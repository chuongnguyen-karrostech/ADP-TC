package com.APIMM.mam.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.APIMM.mam.model.Condition;

@Repository
public interface ConditionRepository extends JpaRepository<Condition, Integer> {

	@Query("select c from Condition c where c.condOperator like ?1 and c.condType like ?2 and c.condValue1 like ?3")
    List <Condition> findCondition(String condOperator, String condType, String condValue);
	
	@Modifying
	@Transactional
    @Query("delete from Condition c where c.condId = ?1")
    void deletebyId(Integer condId);
	
}
