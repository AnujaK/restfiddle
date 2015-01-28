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
package com.restfiddle.controller.rest.sample;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.restfiddle.entity.User;

@Controller
@EnableAutoConfiguration
@ComponentScan
public class SampleWebController {
    @Value("${application.message:REST Fiddle}")
    private String message = "REST Fiddle";

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/web")
    public String home(Map<String, Object> model) {
	model.put("time", new Date());
	model.put("message", this.message);
	return "welcome";
    }

    @RequestMapping("/data")
    public @ResponseBody
    User sayHello(@RequestParam(value = "name", required = false, defaultValue = "JSON") String name) {
	User user = new User();
	user.setId(counter.incrementAndGet());
	user.setName(String.format(template, name));
	return user;
    }
}
