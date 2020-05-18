package com.ADP.configurations;

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
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackages = {"com.ADP.etmain.repository"}, 
    entityManagerFactoryRef = "etMainEntityManager", 
    transactionManagerRef = "etMainTransactionManager"
)
@EntityScan(basePackages = "com.ADP.etmain.model")
public class ETMaintenanceDBConfig {
	private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private Environment env;
     
    @Bean
    public LocalContainerEntityManagerFactoryBean etMainEntityManager() {
        
    	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(etMaintDataSource());
        em.setPackagesToScan( new String[] { "com.ADP.etmain.model" });       
        
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        
        HashMap<String, Object> properties = new HashMap<>();        
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        
        em.setJpaPropertyMap(properties);       
        
        return em;
    }
 
    @Bean
    public DataSource etMaintDataSource() {
    	try
    	{
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        String pathGPSP = env.getProperty("gpsprocessor.ini");  	        
	        
	        Wini ini = new Wini(new File(pathGPSP));
	        //com.AVL.util.Utilities.ETDBPrefix = ini.get("Initialization", "ETDB_Name");
	        String strEtmain = ini.get("Initialization", "MAINTDB_ConnectString");
	        String[] arrEtmain = strEtmain.split(";");
	        
	        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	        String strEtmainSource = "";
	        String strEtmainDatabase = "";
	        String strEtmainUser = "";
	        String strEtmainPass = "";
	        //logger.info("Length of Etmain: " + arrEtmain.length); 
	        for (int i = 0; i < arrEtmain.length; i++)
	        {
	        	String arrTemp[] = null;
	        	if (arrEtmain[i].toLowerCase().indexOf("data") > -1)
	        	{
	        		arrTemp = arrEtmain[i].split("=");
	        		if (arrTemp.length > 0)
	        		{
	        			strEtmainSource = arrTemp[1];
	        			 
	        			//strEtmainSource = strEtmainSource.replace('\', "\\");
	        		}
	        	}
	        	else if (arrEtmain[i].toLowerCase().indexOf("initial") > -1)
	        	{
	        		arrTemp = arrEtmain[i].split("=");
	        		if (arrTemp.length > 0)
	        		{
	        			strEtmainDatabase = arrTemp[1];
	        		}
	        	}
	        	else if (arrEtmain[i].toLowerCase().indexOf("user") > -1)
	        	{
	        		arrTemp = arrEtmain[i].split("=");
	        		if (arrTemp.length > 0)
	        		{
	        			strEtmainUser = arrTemp[1];
	        		}
	        	}
	        	else if (arrEtmain[i].toLowerCase().indexOf("password") > -1)
	        	{
	        		arrTemp = arrEtmain[i].split("=");
	        		if (arrTemp.length > 0)
	        		{
	        			strEtmainPass= arrTemp[1];
	        		}
	        	}
	        }
	        //jdbc:sqlserver://WIN2012R2ETM\\HOPEWELLVA1;databaseName=Etmainhopewellva1
	        String strURL = env.getProperty("jdbc.url") + strEtmainSource + ";databaseName=" + strEtmainDatabase;
	        dataSource.setUrl(strURL);
	        dataSource.setUsername(strEtmainUser);
	        dataSource.setPassword(strEtmainPass);            	        	         
	        return dataSource;	        
    	}
        catch (Exception ex)
    	{
    		logger.info("Error read ini file:" + ex.toString());
    		return null;
    	}
    }
 
    @Bean
    public PlatformTransactionManager etMainTransactionManager() {
  
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(etMainEntityManager().getObject());
        return transactionManager;
    }
}
