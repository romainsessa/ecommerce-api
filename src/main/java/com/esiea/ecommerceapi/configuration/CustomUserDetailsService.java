package com.esiea.ecommerceapi.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.esiea.ecommerceapi.model.InternalUser;
import com.esiea.ecommerceapi.repository.InternalUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private InternalUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		
		InternalUser internalUser = userRepository.findByUsername(username);
		if(internalUser == null) {
			throw new UsernameNotFoundException(username + " not found");
		}	
		User user = new User(
				internalUser.getUsername(), 
				internalUser.getPassword(), 
				getGrantedAuthorities());
		return user;
	}
	
	private List<GrantedAuthority> getGrantedAuthorities() {
		List<GrantedAuthority> authorities = 
				new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}
	   
}