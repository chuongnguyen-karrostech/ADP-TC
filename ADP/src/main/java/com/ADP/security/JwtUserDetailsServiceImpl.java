package com.ADP.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ADP.name.repository.UserRepository;
import com.ADP.name.model.User;

/**
 * Created by stephan on 20.03.16.
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired    
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String prsId){    
    	try {
        User user = userRepository.findByPrsId(prsId);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with name '%s'.", prsId));
        } else {
            return JwtUserFactory.create(user);
        }
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
		return null;
    }  

}
