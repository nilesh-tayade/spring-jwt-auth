package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.config.CustomUserDetails;
import com.test.config.JwtUtil;
import com.test.model.JwtRequest;
import com.test.model.JwtResponse;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
public class JwtController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetails customUserDetails;
	
	
	
	@PostMapping("/token")
	public ResponseEntity<?>  generateToken(@RequestBody JwtRequest jwtRequest)
	{
		
		System.out.println(jwtRequest);
		
		try {
			
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		} catch (Exception e) {
		}
		
	UserDetails userDetails=	this.customUserDetails.loadUserByUsername(jwtRequest.getUsername());
		
	String token=	this.jwtUtil.generateToken(userDetails);
		
	System.out.println("Token : "+token);
	
	
		return ResponseEntity.ok(new JwtResponse(token)); 
	}
	
	

}
