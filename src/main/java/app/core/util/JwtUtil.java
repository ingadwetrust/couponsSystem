package app.core.util;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
//	this+is+my+key+and+it+must+be+at+least+256+bits+long 
	private String encodedSecretKey = "iBet+you+cannot+remember+this+cOdE+ok+it'sTooShort+How_abOutThis?!";
	private Key decodedSecretKey = new SecretKeySpec(Base64.getMimeDecoder().decode(encodedSecretKey),
			this.signatureAlgorithm);

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", userDetails.id);
		claims.put("userName", userDetails.name);
		claims.put("userType", userDetails.userType);
		return createToken(claims, userDetails.email);
	}

	private String createToken(Map<String, Object> claims, String subject) {

		Instant now = Instant.now();

		return Jwts.builder().setClaims(claims)

				.setSubject(subject)

				.setIssuedAt(Date.from(now))

				.setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))

				.signWith(this.decodedSecretKey)

				.compact();
	}

	private Claims extractAllClaims(String token) throws ExpiredJwtException {
		JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(this.decodedSecretKey).build();
		return jwtParser.parseClaimsJws(token).getBody();
	}

	/** returns the JWT subject - in our case the email address */
	public String extractEmail(String token) {
		return extractAllClaims(token).getSubject();
	}

	public String extractUserName(String token) {
		return extractAllClaims(token).get("userName").toString();
	}

	public int extractUserType(String token) {
		String user = extractAllClaims(token).get("userType").toString();
		switch (user) {
		case "ADMIN": {
			return 0;
		}
		case "COMPANY": {
			return 1;
		}
		case "CUSTOMER": {
			return 2;
		}
		default:
			return -1;
		}
	}

	public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}

	public boolean isTokenExpired(String token) {
		try {
			extractAllClaims(token);
			return false;
		} catch (ExpiredJwtException e) {
			return true;
		}
	}

	/**
	 * returns true if the user (email) in the specified token equals the one in the
	 * specified user details and the token is not expired
	 */
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractEmail(token);
		return (username.equals(userDetails.email) && !isTokenExpired(token));
	}

	public static class UserDetails {
		public String id;
		public String email;
		public String name;
		public String password;
		public String token;
		public UserType userType;

		public UserDetails() {
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public UserType getUserType() {
			return userType;
		}

		public void setUserType(UserType userType) {
			this.userType = userType;
		}

		public enum UserType {
			ADMIN, COMPANY, CUSTOMER
		}

	}

}