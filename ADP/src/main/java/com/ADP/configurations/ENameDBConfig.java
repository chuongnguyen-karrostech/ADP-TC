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
//@PropertySource({ "classpath:database.properties" })
@EnableJpaRepositories(
    basePackages = {"com.ADP.name.repository"}, 
    entityManagerFactoryRef = "ramEntityManager", 
    transactionManagerRef = "ramTransactionManager"
)
@EntityScan(basePackages = "com.ADP.name.model")
public class ENameDBConfig {
	private final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private Environment env;
//    @Bean
//    public int readsetting(){
//    	//int zone = Integer.parseInt(env.getProperty("mapzone"));
//    	//com.ADP.util.Utilities.zone=zone;    	
//    	String MAMAPI_url = env.getProperty("urlmamapi");
//    	com.ADP.util.Utilities.MMAPI_Url=MAMAPI_url;
//    	return -1;
//    }
    
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean ramEntityManager() {
        
    	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ramDataSource());
        em.setPackagesToScan( new String[] { "com.ADP.name.model" });       
        
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        
        HashMap<String, Object> properties = new HashMap<>();        
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        
        em.setJpaPropertyMap(properties);       
        
        return em;
    }
 
    @Primary
    @Bean
    public DataSource ramDataSource() {    	
    	try
    	{			       	    
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();	        
	        
	    	String MAMAPI_url = env.getProperty("urlmamapi");
	    	com.ADP.util.Utilities.MMAPI_Url=MAMAPI_url;	        
	        
	        String pathGPSP = env.getProperty("gpsprocessor.ini"); 	        	        
	        Wini ini = new Wini(new File(pathGPSP));
	        //com.AVL.util.Utilities.ETDBPrefix = ini.get("Initialization", "ETDB_Name");
	        int intZone = ini.get("Initialization", "EDUNT_StatePlaneZone", int.class);
	        if (intZone <= 0)
	        {
	        	intZone = ini.get("Initialization", "EDUNT_UTMZone", int.class);
	        }
	        com.ADP.util.Utilities.zone= intZone; 
	        
	        String strEtext = ini.get("Initialization", "ETEXT_ConnectString");
	        String[] arrEtext = strEtext.split(";");	        
	        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	        String strEtextSource = "";
	        String strEtextDatabase = "";
	        String strEtextUser = "";
	        String strEtextPass = "";
	        //logger.info("Length of etext: " + arrEtext.length); 
	        for (int i = 0; i < arrEtext.length; i++)
	        {
	        	String arrTemp[] = null;
	        	if (arrEtext[i].toLowerCase().indexOf("data") > -1)
	        	{
	        		arrTemp = arrEtext[i].split("=");
	        		if (arrTemp.length > 0)
	        		{
	        			strEtextSource = arrTemp[1];
	        			 
	        			//strEtextSource = strEtextSource.replace('\', "\\");
	        		}
	        	}
	        	else if (arrEtext[i].toLowerCase().indexOf("initial") > -1)
	        	{
	        		arrTemp = arrEtext[i].split("=");
	        		if (arrTemp.length > 0)
	        		{
	        			strEtextDatabase = arrTemp[1];
	        		}
	        	}
	        	else if (arrEtext[i].toLowerCase().indexOf("user") > -1)
	        	{
	        		arrTemp = arrEtext[i].split("=");
	        		if (arrTemp.length > 0)
	        		{
	        			strEtextUser = arrTemp[1];
	        		}
	        	}
	        	else if (arrEtext[i].toLowerCase().indexOf("password") > -1)
	        	{
	        		arrTemp = arrEtext[i].split("=");
	        		if (arrTemp.length > 0)
	        		{
	        			strEtextPass= arrTemp[1];
	        		}
	        	}
	        }
	        
	        String strENameDatabase = env.getProperty("ename.database");
	        String strURL = env.getProperty("jdbc.url") + strEtextSource + ";databaseName=" + strENameDatabase;
	        dataSource.setUrl(strURL);
	        dataSource.setUsername(strEtextUser);
	        dataSource.setPassword(strEtextPass);    

	        return dataSource;	        
    	}
        catch (Exception ex)
    	{
    		logger.info("Error read ini file:" + ex.toString());
    		return null;
    	}
    }
 
    @Primary
    @Bean
    public PlatformTransactionManager ramTransactionManager() {
  
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(ramEntityManager().getObject());
        return transactionManager;
    }
}
