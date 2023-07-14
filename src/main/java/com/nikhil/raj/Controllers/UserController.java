package com.nikhil.raj.Controllers;

import com.nikhil.raj.Models.Users;
import com.nikhil.raj.PayLoad.Request.SignUpRequest;
import com.nikhil.raj.Repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserRepo uRepo;

	@Autowired
	private PasswordEncoder encoder;

	@GetMapping("/user/acc")
	public ResponseEntity<String> userAccess() {
		String s = "Only Users can Acesses.";
		return new ResponseEntity<String>(s, HttpStatus.OK);
	}

	@GetMapping("/admin/acc")
	public ResponseEntity<String> adminAccess() {
		String s = "Only admin can Acesses";
		return new ResponseEntity<String>(s, HttpStatus.OK);
	}

	@GetMapping("/all/acc")
	public ResponseEntity<String> allAccess() {
		String s = "No authentication is needed";
		return new ResponseEntity<String>(s, HttpStatus.OK);
	}

	@PostMapping("/createUser")
	public ResponseEntity<String> createUser(@RequestBody SignUpRequest user) {

		user.setPassword(encoder.encode(user.getPassword()));

		ModelMapper mapper = new ModelMapper();
		Users realUsers = mapper.map(user, Users.class);

		Users saveUsers = uRepo.save(realUsers);
		return new ResponseEntity<String>("Created", HttpStatus.CREATED);

	}

}
