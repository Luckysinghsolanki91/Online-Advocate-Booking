package com.onlineadvocatebooking.JWT;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter{
  @Autowired
  private JwtUtil jwtUtil;
  
  private CustomerUserDetailsService service;
  
  Claims claims = null;
  private String userName=null;
  
	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		if(httpServletRequest.getServletPath().matches("/user/login/user/forgetPassword/user/signup")) {
//			filterChain.doFilter(httpServletRequest, httpServletResponse);
//		}else {
//			String authorizationHeader = httpServletRequest.getHeader("Authorization");
//			String token =null;
//			if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer")) {
//				token =authorizationHeader.substring(7);
//				userName = jwtUtil.extractUsername(token);
//				claims=jwtUtil.extractAllClaims(token);
//			}
//			if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
//				UserDetails userDetails = service.loadUserByUsername(userName);
//				if(jwtUtil.vaildateToken(token, userDetails)) {
//					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
//					= new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
//					UsernamePasswordAuthenticationToken.setDetails{
//						new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
//						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//						
//					}
//				}
//				filterChain.doFilter(httpServletRequest, httpServletResponse);
//			}
//	
//		
//		public boolean isAdmin() {
//			return "admin" .equalsIgnoreCase((String) claims.get("role"));
//			
//		}
//		public boolean isUser() {
//			return "User" .equalsIgnoreCase((String) claims.get("role"));
//			
//		}
//		public String getCurrentUser() {
//			return userName;
//		}
	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

	    // Check if the request is for a login, forgot password, or signup page. If it is, then do not filter the request.
	    if (request.getServletPath().matches("/user/login/user/forgetPassword/user/signup")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    // Get the authorization header from the request
	    String authorizationHeader = request.getHeader("Authorization");

	    // If the authorization header is not present, or does not start with "Bearer", then do not filter the request.
	    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    // Get the JWT token from the authorization header
	    String token = authorizationHeader.substring(7);

	    // Get the user name from the JWT token
	    String userName = jwtUtil.extractUsername(token);

	    // Get the claims from the JWT token
	    Claims claims = jwtUtil.extractAllClaims(token);

	    // Check if the user is authenticated
	    if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

	        // Load the user from the database
	        UserDetails userDetails = service.loadUserByUsername(userName);

	        // Validate the JWT token
	        if (jwtUtil.vaildateToken(token, userDetails)) {

	            // Create a UsernamePasswordAuthenticationToken
	            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

	            // Set the details of the UsernamePasswordAuthenticationToken
	            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	            // Set the authentication in the SecurityContextHolder
	            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	        }
	    }

	    // Continue the filter chain
	    filterChain.doFilter(request, response);
	}
	public boolean isAdmin() {
		return "admin" .equalsIgnoreCase((String) claims.get("role"));
		
	}
	public boolean isUser() {
		return "User" .equalsIgnoreCase((String) claims.get("role"));
		
	}
	public String getCurrentUser() {
		return userName;
	}
	
	}
	

