package com.nikhil.raj.Security.JWT;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.nikhil.raj.Models.Users;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {

	private final String jwtSecret = "Nikhil";

	private final Integer jwtExpirationMs = 86400000;

	private final String jwtCookie = "JWT";

	public String getJwtFromCookies(HttpServletRequest request) {
		Cookie cookie = WebUtils.getCookie(request, jwtCookie);
		if (cookie != null) {
			System.out.println(cookie.getName());
			return cookie.getValue();
		} else
			return null;
	}

	public String getUsernameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public String generateTokenFromUsername(String username) {

		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	// To generate cookie with jwt token (will be sent with response)
	public ResponseCookie generateJwtCookie(Users users) {
		String jwt = generateTokenFromUsername(users.getUsername());
		ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true)
				.build();
		return cookie;
	}

	// to validate JWT token
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			System.out.println(e.getMessage());
		} catch (ExpiredJwtException e) {
			System.out.println(e.getMessage());
		} catch (MalformedJwtException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (UnsupportedJwtException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public boolean isTokenExpired(String authtoken) {
		Date date = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authtoken).getBody().getExpiration();
		return (new Date()).after(date);
	}

	public boolean validateJwtToken(String authToken, Users users) {
		String username = getUsernameFromJwtToken(authToken);
		return (username.equals(users.getUsername()) && !isTokenExpired(authToken));
	}

}
