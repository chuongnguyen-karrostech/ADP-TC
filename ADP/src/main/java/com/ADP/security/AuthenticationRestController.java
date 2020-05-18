package com.ADP.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ADP.name.model.NameSubCategory;
import com.ADP.name.model.User;
import com.ADP.service.DriverService;
import com.ADP.service.PrsTypeService;
import com.ADP.service.UserService;
import com.ADP.util.DateFunction;
import com.ADP.util.Utilities;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
@Configuration
@PropertySource("classpath:application.properties")
public class AuthenticationRestController {
	private final Log logger = LogFactory.getLog(this.getClass());	
	@Autowired
	private Environment env;
    @Value("Authorization")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DriverService driverService;
    
    @Autowired
    private PrsTypeService prsTypeService;
    
    @RequestMapping(value = "version", method = { RequestMethod.GET })
    public String Version() {
    	return "1.0.0.1";
    }
    
    @RequestMapping(value = "auth", method = { RequestMethod.POST, RequestMethod.OPTIONS })
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, Device device) throws AuthenticationException {
    	long timekey = DateFunction.TimeKey();	
    	logger.info(Utilities.LogReceive(timekey, "Login", "Driver", authenticationRequest.getUsername()));
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails, device);
        
        User user = this.userService.findById(authenticationRequest.getUsername());
        
        // Return job class
        String jobClass = "";
        try {
        	NameSubCategory subCategory = prsTypeService.findById(user.getSysId().trim()).getNameSubCategory();
        	jobClass = subCategory.getName().trim();
        } catch (Exception ex) {
        	jobClass = "";
        }
        
        // login 3 times.
        if(user.getPrsStatus().trim().equalsIgnoreCase("Inactive")) {
        	ResponseEntity<?> result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new 
        			JwtAuthenticationResponseError(System.currentTimeMillis() / 1000L,401,"org.springframework.security.authentication.BadCredentialsException",
        					"This account has been deactivated. Please contact the Administrator to reset your password",env.getProperty("server.contextPath")+"/auth"));
        	logger.info(Utilities.LogReturn(timekey, "Login", "Driver", authenticationRequest.getUsername()));
        	
        	return result;
        } else { // success
	        // Return the token        
	        ResponseEntity<?> result = ResponseEntity.ok(new JwtAuthenticationResponse(token, user.getPrsLastname().trim(), 
	        		user.getPrsFirstname().trim(),jobClass));
	        logger.info(Utilities.LogReturn(timekey, "Login", "Driver", authenticationRequest.getUsername()));
	        return result;
        }
		
    }
    
    @RequestMapping(value = "lockAccount/{id}", method = { RequestMethod.GET})
    public ResponseEntity<?> lockAccount(@PathVariable String id) throws AuthenticationException {
    	long timekey = DateFunction.TimeKey();	
    	logger.info(Utilities.LogReceive(timekey, "LockAccount", "Driver", id));
        
    	User user = this.userService.findById(id.trim());
    	
    	if(user == null) {
    		ResponseEntity<?> result = ResponseEntity.status(HttpStatus.OK).body(new JwtLockAccountResponse("failed","The DriverID is not exist"));
    		logger.info(Utilities.LogReturn(timekey, "LockAccount", "Driver", id));
    		return result;
    	}
    	//change Status of User
    	user.setPrsStatus("Inactive");
    	this.userService.save(user);
        // Return the token        
    	ResponseEntity<?> result = ResponseEntity.status(HttpStatus.OK).body(new JwtLockAccountResponse("success","The driver "+id.trim()+" is locked successfully."));
    	logger.info(Utilities.LogReturn(timekey, "LockAccount", "Driver", id));
        return result;
    }
    
    @RequestMapping(value = "empauth", method = { RequestMethod.POST, RequestMethod.OPTIONS })
    public ResponseEntity<?> EmployeeAuthentication(@RequestBody JwtAuthenticationRequest empauthenticationRequest, Device device) throws AuthenticationException {
    	long timekey = DateFunction.TimeKey();	
    	logger.info(Utilities.LogReceive(timekey, "EmpAuth", "Employee", empauthenticationRequest.getUsername()));
        // Perform the security
        final Authentication empauthentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		empauthenticationRequest.getUsername(),
                		empauthenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(empauthentication);

        // Reload password post-security so we can generate token
        //final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        //final String token = jwtTokenUtil.generateToken(userDetails, device);
        
        User user = this.userService.findById(empauthenticationRequest.getUsername());
        
        // Return job class
        String jobClass = "";
        try {
        	NameSubCategory subCategory = prsTypeService.findById(user.getSysId().trim()).getNameSubCategory();
        	jobClass = subCategory.getName().trim();
        } catch (Exception ex) {
        	jobClass = "";
        }
         
               
        // login 3 times.
        if(user.getPrsStatus().trim().equalsIgnoreCase("Inactive")) {
        	ResponseEntity<?> result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new 
        			JwtAuthenticationResponseError(System.currentTimeMillis() / 1000L,401,"org.springframework.security.authentication.BadCredentialsException",
        					"This account has been deactivated. Please contact the Administrator to reset your password",env.getProperty("server.contextPath")+"/auth"));
            logger.info(Utilities.LogReturn(timekey, "EmpAuth", "Employee", empauthenticationRequest.getUsername()));
            return result;
        } else { // Return the token 
        	ResponseEntity<?> result = ResponseEntity.ok(new JwtEmpAuthenticationResponse(user.getPrsLastname().trim(), 
            		user.getPrsFirstname().trim(),jobClass));
            logger.info(Utilities.LogReturn(timekey, "EmpAuth", "Employee", empauthenticationRequest.getUsername()));
            return result;
        }
    }
    
    @RequestMapping(value = "refresh", method = {RequestMethod.GET})
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            User userN = this.userService.findById(username);
            // Return job class
            String jobClass = "";
            try {
            	NameSubCategory subCategory = driverService.findById(userN.getSysId().trim()).getNameSubCategory();
            	jobClass = subCategory.getName().trim();
            } catch (Exception ex) {
            	jobClass = "";
            }
            
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken,userN.getPrsLastname().trim(),
            		userN.getPrsFirstname().trim(),jobClass));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(value = "edtawebauth", method = { RequestMethod.POST, RequestMethod.OPTIONS })
    public ResponseEntity<?> edtaWebAuthentication(@RequestBody edtaWeb authenticationRequest) throws AuthenticationException {
    	long timekey = DateFunction.TimeKey();	
    	logger.info(Utilities.LogReceive(timekey, "Login", "Driver", authenticationRequest.getEmployeeId()));
         
        User user = this.userService.findById(authenticationRequest.getEmployeeId());
        
        // Return job class
        String jobClass = "";
        try {
        	NameSubCategory subCategory = prsTypeService.findById(user.getSysId().trim()).getNameSubCategory();
        	jobClass = subCategory.getName().trim();
        } catch (Exception ex) {
        	jobClass = "";
        }
        if(jobClass.isEmpty()) {
        	ResponseEntity<?> result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("false");
        	logger.info(Utilities.LogReturn(timekey, "Login", "edta web error : ", authenticationRequest.getEmployeeId()));
        	return result;
        }
        else
        {
        	ResponseEntity<?> result = ResponseEntity.ok("true");
        	logger.info(Utilities.LogReturn(timekey, "Login", "edta web", authenticationRequest.getEmployeeId()));
        	return result;
        }        
    }
}
