package com.restfiddle.security;

import static com.restfiddle.util.CommonUtil.isEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.restfiddle.dao.UserRepository;
import com.restfiddle.entity.User;

@Service
public class RfUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {

	if (isEmpty(email)) {
	    return null;
	}
	return userRepository.findByEmail(email);
    }

}
