package com.restfiddle.security;

import static com.restfiddle.util.CommonUtil.isEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.restfiddle.dao.UserRepository;
import com.restfiddle.entity.User;

/**
 * @author abidk
 * 
 */
@Service
public class RfUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String userName) throws UsernameNotFoundException {

	if (isEmpty(userName)) {
	    return null;
	}
	return userRepository.findByUserName(userName);
    }

}
