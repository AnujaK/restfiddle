package com.restfiddle.service.util;

import com.restfiddle.entity.User;

/**
 * @author abidk
 * 
 */
public class UserServiceUtil {

	/**
	 * @param email
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @return User
	 */
	public static User getUser(final String email, final String password,
			final String firstName, final String lastName) {

		User user = new User();
		user.setEmail(email);
		user.setPassword(password); // TODO encrypt password
		user.setPassword(firstName);
		user.setLastName(lastName);
		return user;
	}
}
