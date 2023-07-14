package com.nikhil.raj.Controllers;

import com.nikhil.raj.Models.Users;
import com.nikhil.raj.PayLoad.Request.LoginRequest;
import com.nikhil.raj.PayLoad.Response.UserResponse;
import com.nikhil.raj.Repositories.UserRepo;
import com.nikhil.raj.Security.JWT.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserRepo userRepository;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		Users users = (Users) authentication.getPrincipal();

		ResponseCookie cookie = jwtUtils.generateJwtCookie(users);

		UserResponse response = new UserResponse(users.getName(), users.getRole(), users.getUsername());


		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(response);
	}

}
