package com.ADP.service.Athena;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

public class AthenaService {

	protected final Log logger = LogFactory.getLog(this.getClass());

    @Value("${athena_middle_url}/ivin")
    protected String ivinUrl;

    @Value("${athena_middle_url}")
    protected String athena_middle_url;

    @Value("${athena_email}")
    protected String athena_email;

    @Value("${athena_password}")
    protected String athena_password;

    @Value("${athena_scope}")
    protected String athena_scope;

    protected RestTemplate restTemplate = new RestTemplate();

    public HttpHeaders createHttpHeaders() {
        String athena_signin_url = athena_middle_url + "/signin";
        Date curent = new Date();
        if (com.ADP.security.JWT.expiredDatetime == null || com.ADP.security.JWT.expiredDatetime.before(curent)) {
            com.ADP.security.JWT.gettoken(athena_signin_url, athena_email, athena_password, athena_scope);
        }
        if (com.ADP.security.JWT.accessToken != null) {
            System.out.println("** token: " + com.ADP.security.JWT.accessToken);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", com.ADP.security.JWT.accessToken);
            return headers;
        }
        return null;
    }

    public Object getRestTemplate(String url) {
    	Object retObject = getRestTemplate(url, Object.class);
    	logger.info(retObject);
    	return retObject;
    }

    public Object getRestTemplate(String url, Class<?> objClass) {
        HttpHeaders headers = createHttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        Object retObject = restTemplate.exchange(url, HttpMethod.GET, entity, objClass).getBody();
        logger.info(retObject);
        return retObject;
    }

    public Object postRestTemplate(String url, Object object) {
        HttpHeaders headers = createHttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(object, headers);
        Object retObject = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class).getBody();
        logger.info(retObject);
        return retObject;
    }

    public Object deleteRestTemplate(String url) {
        HttpHeaders headers = createHttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        Object retObject = restTemplate.exchange(url, HttpMethod.DELETE, entity, Object.class);
        logger.info(retObject);
        return retObject;
    }
}
