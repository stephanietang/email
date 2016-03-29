DROP TABLE IF EXISTS verify_token;

CREATE TABLE verify_token(
	id INT NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
	token VARCHAR(60),
	token_type TINYINT,
	expiry_date DATE,
	sent TINYINT NOT NULL DEFAULT 0,
	verified TINYINT NOT NULL DEFAULT 0,
	
	PRIMARY KEY (id),
	
	CONSTRAINT fk_verify_token FOREIGN KEY (user_id) REFERENCES user(id)
);