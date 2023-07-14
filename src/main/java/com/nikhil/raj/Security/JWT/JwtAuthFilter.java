package com.nikhil.raj.Security.JWT;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nikhil.raj.Models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nikhil.raj.Services.UserDetailsServiceImpl;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// logic of how to filter requests containing jwt token
		try {
			String jwt = jwtUtils.getJwtFromCookies(request);
			if (jwt != null) {

				String username = jwtUtils.getUsernameFromJwtToken(jwt);

				if (username != null) {

					Users users = (Users) userDetailsServiceImpl.loadUserByUsername(username);

					if (users != null && jwtUtils.validateJwtToken(jwt, users)) {

						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								users, null, users.getAuthorities());
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authentication);

					} else
						throw new Exception("Users not found1.");
				} else
					throw new Exception("Users not found2.");
			}
		} catch (Exception e) {
			System.out.println("Could not set the authentication");
		}

		filterChain.doFilter(request, response);

	}

	//	Bhavesh Sir method below.

	//	@Override
	//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	//			throws ServletException, IOException {
	//		// logic of how to filter requests containing jwt token
	//		try {
	//		String jwt = jwtUtils.getJwtFromCookies(request);
	//		if(jwt != null) {
	//			if(jwtUtils.validateJwtToken(jwt)) {
	//				String username = jwtUtils.getUsernameFromJwtToken(jwt);
	//				UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
	//				
	//				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	//				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	//				SecurityContextHolder.getContext().setAuthentication(authentication);
	//			}
	//		}
	//		}catch(Exception e) {
	//			System.out.println("Could not set the authentication");
	//		}
	//		
	//		filterChain.doFilter(request, response);
	//	}

}
