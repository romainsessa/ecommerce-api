package com.esiea.ecommerceapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esiea.ecommerceapi.configuration.JwtTokenUtil;
import com.esiea.ecommerceapi.model.InternalUser;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/public")
public class PublicController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(
			@RequestBody InternalUser user) {
		
		try  {
			Authentication authenticate = 
					authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(
									user.getUsername(),
									user.getPassword()
									)
							);
			User authenticatedUser = (User) authenticate.getPrincipal();
			String token = 
					jwtTokenUtil.generateAccessToken(authenticatedUser);
			System.out.println("Token is : " + token);
			
			ResponseEntity<String> response = 
					ResponseEntity.ok()
					.header(HttpHeaders.AUTHORIZATION, token)
					.body(token);
			return response;
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(
					HttpStatus.UNAUTHORIZED).build();
		}
	}	
	
	@GetMapping("/securitynone")
	public String securityNone() {
		return "open";
	}
}
