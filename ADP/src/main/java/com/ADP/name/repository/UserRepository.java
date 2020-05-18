package com.ADP.name.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ADP.name.model.User;

import java.lang.String;
import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
	
	@Query("select u from User u where u.prsId = ?1 and ( UPPER(u.prsStatus) ='ACTIVE' or UPPER(u.prsStatus) ='INACTIVE')")
	public User findByPrsId(String prsId);
}