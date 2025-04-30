package com.wipro.product_service.config;

import com.wipro.product_service.util.AppConstant;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	 
		
    	 

 
	   
    	private Claims validateToken(HttpServletRequest request) {
    		String jwtToken = request.getHeader(AppConstant.HEADER).replace(AppConstant.PREFIX, "");
    		return Jwts.parser().setSigningKey(AppConstant.SECRET_KEY.getBytes()).parseClaimsJws(jwtToken).getBody();
    	}
    	private void setUpSpringAuthentication(Claims claims) {
    		@SuppressWarnings("unchecked")
    		List<String> authorities = (List<String>) claims.get("authorities");

    		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
    				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    		SecurityContextHolder.getContext().setAuthentication(auth);

    	}
    	
    	private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
    		String authenticationHeader = request.getHeader(AppConstant.HEADER);
     
    		if (authenticationHeader == null || !authenticationHeader.startsWith(AppConstant.PREFIX))
    			return false;
    		return true;
    	}
	    
	     

	    


	    	

	    	@Override
	    	protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain)
	    			throws ServletException, IOException {
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
 
 
