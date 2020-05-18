package com.APIMM.configurations;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ini4j.Wini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackages = {"com.APIMM.etdb.repository"}, 
    entityManagerFactoryRef = "etdbEntityManager", 
    transactionManagerRef = "etdbTransactionManager"
)
@EntityScan(basePackages = {"com.APIMM.etdb.model"})

public class ETDBConfig {
	private final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private Environment env;
     
    @Bean
    public LocalContainerEntityManagerFactoryBean etdbEntityManager() {
        
    	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(etdbDataSource());
        em.setPackagesToScan( new String[] { "com.APIMM.etdb.model" });       
        
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        String DataMode = env.getProperty("mammdm.datamode");
        HashMap<String, Object> properties = new HashMap<>(); 
        if (DataMode.equalsIgnoreCase("sql")){
            properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        }else{        
	        properties.put("hibernate.dialect", env.getProperty("hibernate.postgres.dialect"));
        }      
        //properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        em.setJpaPropertyMap(properties);
        return em;
    }
    
    @Bean
    public DataSource etdbDataSource() {
  
    	try
    	{
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        String DataMode = env.getProperty("mammdm.datamode");
	        
//	        String CalAmp_mode = env.getProperty("calampmode");
//	    	com.APIMM.util.CommonFunction.CalAmp_mode=CalAmp_mode.trim();
//	    	
	        String Edp_url = env.getProperty("urledp");
	        if (!Edp_url.trim().endsWith("/"))Edp_url = Edp_url.trim()+"/";
	    	com.APIMM.util.CommonFunction.EDP_Url=Edp_url.trim();
	    	
	         String clientId = this.env.getProperty("edp.clientId");
	         if (clientId != null && !clientId.trim().equals("")) {
	        	 com.APIMM.util.CommonFunction.ClientId = clientId.trim();
	         }

	         String clientSecret = this.env.getProperty("edp.clientSecret");
	         if (clientSecret != null && !clientSecret.trim().equals("")) {
	        	 com.APIMM.util.CommonFunction.ClientKey = clientSecret.trim();
	         }

	         String tenantuuId = this.env.getProperty("edp.tenantuuId");
	         com.APIMM.util.CommonFunction.tenantId = tenantuuId.trim();
	         
	         String grantType = this.env.getProperty("edp.grantType");
	         if (grantType != null && !grantType.trim().equals("")) {
	        	 com.APIMM.util.CommonFunction.grantType = grantType.trim();
	         }

	         String scope = this.env.getProperty("edp.scope");
	         if (scope != null && !scope.trim().equals("")) {
	        	 com.APIMM.util.CommonFunction.scope = scope.trim();
	         }	    	
	    	
	    	String version = env.getProperty("project.version");
	    	if (version!=null)
	    		com.APIMM.util.CommonFunction.version=version.trim();
	    	
	        String strURL = env.getProperty("jdbc.postgres.url1");
		    String strUser = env.getProperty("jdbc.postgres.user");
		    String strPass = env.getProperty("jdbc.postgres.pass");
		    String strDecodePass = base64Decode(strPass);
		    dataSource.setDriverClassName(env.getProperty("jdbc.postgres.driverClassName"));
		    dataSource.setUrl(strURL);
		    dataSource.setUsername(strUser);
		    dataSource.setPassword(strDecodePass);            	        	         
	       
	        return dataSource;	   
    	}
        catch (Exception ex)
    	{
    		logger.info("Error read application properties: " + ex.toString());
    		return null;
    	}
    }
 
    private String base64Encode(String token) {
		// TODO Auto-generated method stub
    	Base64 base64 = new Base64();
    	byte[] encodedBytes = base64.encode(token.getBytes());
        return new String(encodedBytes, Charset.forName("UTF-8"));	
//    	decodeString = decodeString.substring(6, decodeString.length() - 6);
//    	return decodeString;
	}
    private String base64Decode(String token) {
		// TODO Auto-generated method stub
    	Base64 base64 = new Base64();
    	byte[] decodedBytes = base64.decode(token.getBytes());    	
    	String decodeString =  new String(decodedBytes, Charset.forName("UTF-8"));		
    	int index1 = decodeString.indexOf("3du");
    	int index2 = decodeString.indexOf("l0g");
    	int lengthChar = decodeString.length();    	
    	if((index1 < 3) & (index2 >=3))
    	{
    		lengthChar = lengthChar - 3;
    	}
    	if(lengthChar > 0) {
    		decodeString = decodeString.substring(3, lengthChar);
    	}    	
    	return decodeString;
	}
   
	
    @Bean
    public PlatformTransactionManager etdbTransactionManager() {
  
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(etdbEntityManager().getObject());
        return transactionManager;
    }
}
