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
package com.restfiddle.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.restfiddle.model.User;

@Controller
public class AppController {
    @Value("${application.message:REST Fiddle}")
    private String message = "REST Fiddle";

    @RequestMapping("/")
    public String home(Map<String, Object> model) {
	model.put("time", new Date());
	model.put("message", this.message);
	return "home";
    }

    @RequestMapping(value = "/api/rf", method = RequestMethod.GET)
    public String userForm(Model model) {
	System.out.println("URL : /api/rf and method : GET");
	model.addAttribute("user", new User());
	model.addAttribute("time", new Date());
	model.addAttribute("message", this.message);
	return "welcome";
    }

    @RequestMapping(value = "/api/rf", method = RequestMethod.POST)
    public String userSubmit(@ModelAttribute User user, Model model) {
	System.out.println("URL : /api/rf and method : POST");
	model.addAttribute("user", user);
	model.addAttribute("time", new Date());
	model.addAttribute("message", this.message);
	return "result";
    }

}
