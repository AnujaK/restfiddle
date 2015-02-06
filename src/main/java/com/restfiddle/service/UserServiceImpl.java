/*
 * Copyright 2015 Ranjan Kumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.restfiddle.service;

import static com.restfiddle.util.CommonUtil.isNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restfiddle.dao.UserRepository;
import com.restfiddle.entity.User;

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
