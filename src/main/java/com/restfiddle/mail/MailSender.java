/*
 * Copyright 2014 Ranjan Kumar
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
package com.restfiddle.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Email;
import com.sendgrid.SendGridException;

@Component
public class MailSender {
    Logger logger = LoggerFactory.getLogger(MailSender.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SendGrid sendGrid;

    public void sendMail(String from, String to, String subject, String msg) {
	SimpleMailMessage message = new SimpleMailMessage();
	message.setFrom(from);
	message.setTo(to);
	message.setSubject(subject);
	message.setText(msg);

	javaMailSender.send(message);
    }

    public void sendUsingSendGrid(String from, String to, String subject, String msg) {
	Email email = new Email();
	email.setFrom(from);
	email.addTo(to);
	email.setSubject(subject);
	email.setText(msg);

	try {
	    sendGrid.send(email);
	} catch (SendGridException e) {
	    logger.error(e.getMessage(), e);
	}
    }

}
