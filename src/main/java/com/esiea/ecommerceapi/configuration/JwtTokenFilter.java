package com.esiea.ecommerceapi.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {
		
		String header = request.getHeader(
				HttpHeaders.AUTHORIZATION.toLowerCase());
		if(header == null 
				|| header.isEmpty() 
				|| !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = header.split(" ")[1].trim();
		System.out.println("Extracted token is " + token);
		
		if(!jwtTokenUtil.validate(token)) {
			filterChain.doFilter(request, response);
			return;
		}
		UserDetails userDetails = 
				userDetailsService.loadUserByUsername(
						jwtTokenUtil.getUsername(token)
						);
		
		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(
						userDetails, 
						null, 
						userDetails.getAuthorities());
		authentication.setDetails(
				new WebAuthenticationDetailsSource()
					.buildDetails(request)
				);
		SecurityContextHolder.getContext()
			.setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

}
