package com.bolehunt.email;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

public class VerifyTokenRowMapper implements RowMapper<VerifyToken> {

    public VerifyToken mapRow(ResultSet rs, int rowNum) throws SQLException {
    	int id = rs.getInt("id");
    	int userId = rs.getInt("user_id");
    	String token = rs.getString("token");
        int tokenType = rs.getInt("token_type");
        Date expiryDate = rs.getDate("expiry_date");
        int sent = rs.getInt("sent");
        int verified = rs.getInt("verified");
        String email = rs.getString("email");
        
        VerifyToken verifyToken = new VerifyToken();
        verifyToken.setId(id);
        verifyToken.setUserId(userId);
        verifyToken.setToken(token);
        verifyToken.setTokenType(tokenType);
        verifyToken.setExpiryDate(expiryDate);
        verifyToken.setSent(sent);
        verifyToken.setVerified(verified);
        verifyToken.setEmail(email);
        return verifyToken;
    }
}
