package com.restfiddle.service.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class GenericEntityAuthFilter extends AbstractAuthenticationProcessingFilter  {

    public GenericEntityAuthFilter(String defaultFilterProcessesUrl) {
	super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
	// TODO Auto-generated constructor stub
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	System.err.println("In filter");
	// TODO Auto-generated method stub
	
	chain.doFilter(req, res);
	
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException,
	    IOException, ServletException {
	System.err.println("In filter Auth");
	return null;
    }

}
