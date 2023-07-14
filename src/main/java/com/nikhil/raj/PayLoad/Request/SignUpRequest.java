package com.nikhil.raj.PayLoad.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

	private String name;

	private String role;

	private String username;

	private String password;
}
