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
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = {
		"com.APIMM.etext.repository" }, entityManagerFactoryRef = "eTextEntityManager", transactionManagerRef = "eTextTransactionManager")
@EntityScan(basePackages = "com.APIMM.etext.model")
public class ETEXTDBConfig {
	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private Environment env;

	@Bean
	public LocalContainerEntityManagerFactoryBean eTextEntityManager() {

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(eTextDataSource());
		em.setPackagesToScan(new String[] { "com.APIMM.etext.model" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));

		em.setJpaPropertyMap(properties);

		return em;
	}

	@Bean
	public DataSource eTextDataSource() {
		try {
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			String pathGPSP = env.getProperty("gpsprocessor.ini");
        	File gpsp = new File(pathGPSP);
        	Wini ini = new Wini(gpsp);	        
        	String strEtext = ini.get("Initialization", "ETEXT_ConnectString");
        	try {
	        	String configfolder = gpsp.getParent();
	        	if (!configfolder.endsWith("\\")) configfolder=configfolder + "\\";
	        	String pathETVDC =  configfolder + "etVDC.ini" ;
	        	logger.info("path etVDC file: " + pathETVDC);
	        	Wini eTVDCini = new Wini(new File(pathETVDC));	        
	        	String strVendor = eTVDCini.get("source", "VENDOR_BRAND");
	        	com.APIMM.util.CommonFunction.VendorBrand=strVendor.trim();	        	
        	}catch (Exception e){
        		com.APIMM.util.CommonFunction.VendorBrand="TCM";
        	}
        	logger.info("VENDOR_BRAND: " + com.APIMM.util.CommonFunction.VendorBrand);
        	String[] arrEText = strEtext.split(";");       	
			
			dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
			String strETextSource = "";
			String strETextDatabase = "";
			String strETextUser = "";
			String strETextPass = "";
			for (int i = 0; i < arrEText.length; i++) {
				String arrTemp[] = null;
				if (arrEText[i].toLowerCase().indexOf("data") > -1) {
					arrTemp = arrEText[i].split("=");
					if (arrTemp.length > 0) {
						strETextSource = arrTemp[1];

					}
				} else if (arrEText[i].toLowerCase().indexOf("initial") > -1) {
					arrTemp = arrEText[i].split("=");
					if (arrTemp.length > 0) {
						strETextDatabase = arrTemp[1];
					}
				} else if (arrEText[i].toLowerCase().indexOf("user") > -1) {
					arrTemp = arrEText[i].split("=");
					if (arrTemp.length > 0) {
						strETextUser = arrTemp[1];
					}
				} else if (arrEText[i].toLowerCase().indexOf("password") > -1) {
					arrTemp = arrEText[i].split("=");
					if (arrTemp.length > 0) {
						strETextPass = arrTemp[1];
					}
				}
			}
			// jdbc:sqlserver://WIN2012R2ETM\\HOPEWELLVA1;databaseName=Etmainhopewellva1
			String strURL = env.getProperty("jdbc.url") + strETextSource + ";databaseName=" + strETextDatabase;
			//String strURL = env.getProperty("jdbc.url");
			//strURL = strURL.replace("@server", strETextSource).replace("@dbName", strETextDatabase);
			dataSource.setUrl(strURL);
			dataSource.setUsername(strETextUser);
			dataSource.setPassword(strETextPass);
			return dataSource;
		} catch (Exception ex) {
			logger.info("Error read ini file:" + ex.toString());
			return null;
		}
	}

	@Bean
	public PlatformTransactionManager eTextTransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(eTextEntityManager().getObject());
		return transactionManager;
	}
}
