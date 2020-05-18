package com.APIMM.configurations;

import java.io.File;
import java.util.HashMap;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ini4j.Wini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackages = {"com.APIMM.etgps.repository"}, 
    entityManagerFactoryRef = "etgpsEntityManager", 
    transactionManagerRef = "etgpsTransactionManager"
)
@EntityScan(basePackages = "com.APIMM.etgps.model")
public class ETGPSDBConfig {
	private final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private Environment env;
     
    @Bean
    public LocalContainerEntityManagerFactoryBean etgpsEntityManager() {
        
    	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(etgpsDataSource());
        em.setPackagesToScan( new String[] { "com.APIMM.etgps.model" });       
        
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        
        HashMap<String, Object> properties = new HashMap<>();        
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        
        em.setJpaPropertyMap(properties);       
        
        return em;
    }
 
    @Bean
    public DataSource etgpsDataSource() {
  
    	try
    	{
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        String pathGPSP = env.getProperty("gpsprocessor.ini");  	        
	        
	        Wini ini = new Wini(new File(pathGPSP));	        
	        String strEtgps = ini.get("Initialization", "GPS_ConnectString");
	        String[] arrEtext = strEtgps.split(";");
	        
	        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	        String strEtgpsSource = "";
	        String strEtgpsDatabase = "";
	        String strEtgpsUser = "";
	        String strEtgpsPass = "";
	        //logger.info("Length of etext: " + arrEtext.length); 
	        for (int i = 0; i < arrEtext.length; i++)
	        {
	        	String arrTemp[] = null;
	        	if (arrEtext[i].toLowerCase().indexOf("data") > -1)
	        	{
	        		arrTemp = arrEtext[i].split("=");
	        		if (arrTemp.length > 0)
	        		{
	        			strEtgpsSource = arrTemp[1];
	        			 
	        			//strEtgpsSource = strEtgpsSource.replace('\', "\\");
	        		}
	        	}
	        	else if (arrEtext[i].toLowerCase().indexOf("initial") > -1)
	        	{
	        		arrTemp = arrEtext[i].split("=");
	        		if (arrTemp.length > 0)
	        		{
	        			strEtgpsDatabase = arrTemp[1];
	        		}
	        	}
	        	else if (arrEtext[i].toLowerCase().indexOf("user") > -1)
	        	{
	        		arrTemp = arrEtext[i].split("=");
	        		if (arrTemp.length > 0)
	        		{
	        			strEtgpsUser = arrTemp[1];
	        		}
	        	}
	        	else if (arrEtext[i].toLowerCase().indexOf("password") > -1)
	        	{
	        		arrTemp = arrEtext[i].split("=");
	        		if (arrTemp.length > 0)
	        		{
	        			strEtgpsPass= arrTemp[1];
	        		}
	        	}
	        }

			//String strURL = env.getProperty("jdbc.url");
			String strURL = env.getProperty("jdbc.url") + strEtgpsSource + ";databaseName=" + strEtgpsDatabase;
			//strURL = strURL.replace("@server", strEtgpsSource).replace("@dbName", strEtgpsDatabase);
	        dataSource.setUrl(strURL);
	        dataSource.setUsername(strEtgpsUser);
	        dataSource.setPassword(strEtgpsPass);            	        	         
	        return dataSource;   
    	}
        catch (Exception ex)
    	{
    		logger.info("Error read ini file:" + ex.toString());
    		return null;
    	}
    }
 
    @Bean
    public PlatformTransactionManager etgpsTransactionManager() {
  
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(etgpsEntityManager().getObject());
        return transactionManager;
    }
}
