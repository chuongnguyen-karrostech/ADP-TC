package com.ADP.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ADP.name.model.User;
import com.ADP.name.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository server;
	
	public User findById(String prsId) {
		return server.findByPrsId(prsId);
	}
	
	public void save(User user) {
		this.server.save(user);
	}
}