package com.wipro.umgmtv1.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wipro.umgmtv1.util.AppConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	 
		
    	 

 
	   
    	private Claims validateToken(HttpServletRequest request) {
    		String jwtToken = request.getHeader(AppConstant.AUTH_HEADER).replace(AppConstant.AUTH_PREFIX, "");
    		return Jwts.parser().setSigningKey(AppConstant.JWT_SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    	}
    	private void setUpSpringAuthentication(Claims claims) {
    		@SuppressWarnings("unchecked")
    		List<String> authorities = (List<String>) claims.get("authorities");

    		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
    				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    		SecurityContextHolder.getContext().setAuthentication(auth);

    	}
    	
    	private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
    		String authenticationHeader = request.getHeader(AppConstant.AUTH_HEADER);
     
    		if (authenticationHeader == null || !authenticationHeader.startsWith(AppConstant.AUTH_PREFIX))
    			return false;
    		return true;
    	}
	    
	     

	    



	    	/**
	    	 * Authentication method in Spring flow
	    	 * 
	    	 * @param claims
	    	 */
	    	

	    	

	    	@Override
	    	protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
	    			jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain chain)
	    			throws jakarta.servlet.ServletException, IOException {
	    		try {
	    			if (checkJWTToken(request, response)) {
	    				Claims claims = validateToken(request);
	    				if (claims.get("authorities") != null) {
	    					setUpSpringAuthentication(claims);
	    				} else {
	    					SecurityContextHolder.clearContext();
	    				}
	    			} else {
	    				SecurityContextHolder.clearContext();
	    			}
	    			chain.doFilter(request, response);
	    		}
	    		catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
	    			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	    			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
	    			return;
	    		}
	    		
	    	}

	    }
 
 
