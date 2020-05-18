package com.ADP.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ADP.name.model.Role;
import com.ADP.name.model.User;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
    	return new JwtUser(
                Integer.parseInt(user.getSysId().trim()),
                user.getPrsId().trim(),                
                user.getPrsPassword(),
                true,
                mapToGrantedAuthorities()
        );
    }
    
	private static List<GrantedAuthority> mapToGrantedAuthorities() {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        list.add(new SimpleGrantedAuthority("ROLE_USER"));

        return list;
    }
}
