package com.restfiddle.config.persistence;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.restfiddle.entity.User;

public class SpringSecurityAuditorAware implements AuditorAware<User> {

    public User getCurrentAuditor() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	if (authentication == null || !authentication.isAuthenticated()) {
	    return null;
	}
	if(!(authentication.getPrincipal() instanceof User)){
	    return null;
	}
	return (User)authentication.getPrincipal();
    }
}
