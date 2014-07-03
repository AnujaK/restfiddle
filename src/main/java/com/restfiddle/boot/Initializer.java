package com.restfiddle.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

import com.restfiddle.entity.User;
import com.restfiddle.service.UserService;
import com.restfiddle.service.util.UserServiceUtil;

public class Initializer implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Initializer.class);

	final String SUPER_USER_EMAIL = "abid@viewics.com";

	final String DEAFULT_PASSWORD = "abcd12345678";

	final String SUPER_USER_FIRST_NAME = "Super";

	final String SUPER_USER_LAST_NAME = "User";

	@Autowired
	private UserService userService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {

		LOGGER.info("Initializing application...");
		createSeedUser();
	}

	/**
	 * Create super user. This user will be the seed user for the application
	 * 
	 */
	@Transactional
	public void createSeedUser() {

		if (userService.isPresent(SUPER_USER_EMAIL)) {
			LOGGER.info("Seed user already exists...");
			return;
		}

		LOGGER.info("Creating seed user...");
		User user = UserServiceUtil.getUser(SUPER_USER_EMAIL, DEAFULT_PASSWORD,
				SUPER_USER_FIRST_NAME, SUPER_USER_LAST_NAME);
		user = userService.create(user);
		LOGGER.info("Seed user has been successfully created...");

	}
}
