
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
    basePackages = {"com.APIMM.ename.repository"}, 
    entityManagerFactoryRef = "eNameEntityManager", 
    transactionManagerRef = "eNameTransactionManager"
)
@EntityScan(basePackages = "com.APIMM.ename.model")
public class ENAMEDBConfig {
	private final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private Environment env;
     
    @Bean
    public LocalContainerEntityManagerFactoryBean eNameEntityManager() {
        
    	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(eNameDataSource());
        em.setPackagesToScan( new String[] { "com.APIMM.ename.model" });       
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));

		em.setJpaPropertyMap(properties);
		
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//        String DataMode = env.getProperty("mammdm.datamode");
//        if (DataMode.equalsIgnoreCase("sql")){
//        	HashMap<String, Object> properties = new HashMap<>();        
//            properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
//            em.setJpaPropertyMap(properties);
//        }else{
//	        HashMap<String, Object> properties = new HashMap<>();        
//	        properties.put("hibernate.dialect", env.getProperty("hibernate.postgres.dialect"));
//	        em.setJpaPropertyMap(properties);
//        }      
        
        return em;
    }
 
    @Bean
    public DataSource eNameDataSource() {
    	try
    	{		
    		try{
    			String MAMAPI_url = env.getProperty("max.download");
    			com.APIMM.configurations.APIMMConfig.maxdownload=Integer.parseInt(MAMAPI_url);
    		}catch(Exception tmp){
    			
    		}	    	
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();	        
	        String pathGPSP = env.getProperty("gpsprocessor.ini");

			Wini ini = new Wini(new File(pathGPSP));
			String strEText = ini.get("Initialization", "ETEXT_ConnectString");
			String[] arrEText = strEText.split(";");
				    	
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
 
    @Bean
    public PlatformTransactionManager eNameTransactionManager() {
  
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(eNameEntityManager().getObject());
        return transactionManager;
    }
}
