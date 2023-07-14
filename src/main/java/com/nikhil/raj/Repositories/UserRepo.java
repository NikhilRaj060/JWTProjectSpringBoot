package com.nikhil.raj.Repositories;

import java.util.Optional;

import com.nikhil.raj.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users, Integer> {

	public Optional<Users> findByUsername(String username);
}
