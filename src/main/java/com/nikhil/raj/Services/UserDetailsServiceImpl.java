package com.nikhil.raj.Services;

import com.nikhil.raj.Models.Users;
import com.nikhil.raj.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Users Not Found."));
		return users;
	}

}
