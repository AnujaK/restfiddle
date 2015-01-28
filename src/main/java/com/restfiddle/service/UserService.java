package com.restfiddle.service;

import com.restfiddle.entity.User;

/**
 * @author abidk
 * 
 */
public interface UserService {

	/**
	 * This method is used to determine if user with email exists or not.
	 * Returns true if use exists with email. Otherwise return false
	 * 
	 * @param email
	 * @return boolean
	 */
	boolean isPresent(String email);

	/**
	 * This method returns User if exists for email. Otherwise return null
	 * 
	 * @param email
	 * @return User
	 */
	User findByEmail(String email);

	/**
	 * @param user
	 * @return User
	 */
	User save(User user);

	/**
	 * @param user
	 * @return User
	 */
	User create(User user);

}
