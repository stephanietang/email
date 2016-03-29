package com.bolehunt.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component
public class MessageHandler {
	
	private static Logger log = LoggerFactory.getLogger(MessageHandler.class);
	
	@Autowired
	private VelocityEngine velocityEngine;
	
    public void handleMessage(List<VerifyToken> verifyTokenList) {
        
        for(VerifyToken verifyToken : verifyTokenList) {
        	try{
        		log.info("Sending token to : {}, token type = {}", verifyToken.getEmail(), verifyToken.getTokenType());
        		sendEmail(verifyToken);
        	} catch(Exception e) {
        		log.error("Exception in sending email to " +  verifyToken.getEmail());
        	}
        }
    }
    
	// http client basic authentication
    // refer to https://hc.apache.org/httpcomponents-client-ga/httpclient/examples/org/apache/http/examples/client/ClientAuthentication.java
    public void sendEmail(VerifyToken verifyToken) throws Exception {
    	
    	CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    	credentialsProvider.setCredentials(AuthScope.ANY, 
    	    new UsernamePasswordCredentials("api", "key-e583d89ebc8d4437e5f51d85867dea3d"));
    	
    	String url = "https://api.mailgun.net/v3/sandbox4f6869a6285c4e999372f9ccefe1d917.mailgun.org/messages";
    	
    	CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build();
    	
    	try{
    		HttpPost httpPost = new HttpPost(url);
        	
        	List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        	urlParameters.add(new BasicNameValuePair("from", "<admin@bolehunt.com>"));
        	urlParameters.add(new BasicNameValuePair("to", "<stephanie.capricorn@gmail.com>"));
        	urlParameters.add(new BasicNameValuePair("subject", "Welcome to BoleHunt"));
        	Map<String, Object> model = new HashMap<String ,Object>();
        	EmailServiceTokenModel emailServiceTokenModel = new EmailServiceTokenModel(verifyToken, "localhost:8080/gene");
            model.put("model", emailServiceTokenModel);
        	String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "VerificationEmail.vm", "UTF-8", model);
            urlParameters.add(new BasicNameValuePair("text", text));
            
            HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
            httpPost.setEntity(postParams);
            
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            
            try{
            	log.info("Response Code : " + httpResponse.getStatusLine().getStatusCode());
            	
            	log.info("Entity : " + EntityUtils.toString(httpResponse.getEntity()));
            } finally {
            	httpResponse.close();
            }
    	} finally{
    		httpClient.close();
    	}
    }
}
