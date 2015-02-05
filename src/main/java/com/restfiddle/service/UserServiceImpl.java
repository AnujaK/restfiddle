package com.restfiddle.service;

import static com.restfiddle.util.CommonUtil.isNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restfiddle.dao.UserRepository;
import com.restfiddle.entity.User;

;

/**
 * @author abidk
 * 
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean isPresent(String email) {
		User user = findByEmail(email);
		return isNotNull(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Transactional
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public User create(User user) {
		user = save(user);
		// TODO send notification
		return user;
	}

}
