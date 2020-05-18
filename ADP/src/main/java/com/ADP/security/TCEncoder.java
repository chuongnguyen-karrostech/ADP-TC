package com.ADP.security;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ADP.util.Utilities;

public class TCEncoder implements PasswordEncoder {
	//private final Log logger = LogFactory.getLog(this.getClass());
	@Override
	public String encode(CharSequence rawPassword) {
		String temp = Utilities.ConvertAsciiPassword_69(rawPassword.toString());
		//System.out.println("code:"+temp);
		//logger.info("checking authentication for user " + temp);
		return temp;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (encodedPassword == null || encodedPassword.length() == 0) {
			return false;
		}
		//logger.info("checking encodedpw for user " + encodedPassword);
		//logger.info("checking raw for user " + rawPassword);
		//logger.info("decode " + Utilities.Decode_Pass_69(" 127091299805     0     0     0"));
		
		if( Utilities.ConvertAsciiPassword_69(rawPassword.toString()).equals(encodedPassword)) {
			return true;
		}
		return false;
	}

}
