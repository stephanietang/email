package com.bolehunt.email;

import java.io.Serializable;
import org.apache.commons.codec.binary.Base64;
	
public class EmailServiceTokenModel implements Serializable {
	
	private static final long serialVersionUID = 5416502089975071080L;
	
	private final String emailAddress;
    private final String token;
    private final int tokenType;
    private final String hostNameUrl;

    public EmailServiceTokenModel(VerifyToken token, String hostNameUrl)  {
        this.emailAddress = token.getEmail();
        this.token = token.getToken();
        this.tokenType = token.getTokenType();
        this.hostNameUrl = hostNameUrl;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getToken() {
        return token;
    }

    public int getTokenType() {
        return tokenType;
    }

    public String getHostNameUrl() {
        return hostNameUrl;
    }
    
    public String getEncodedToken() {
        return new String(Base64.encodeBase64(token.getBytes()));
    }
}

