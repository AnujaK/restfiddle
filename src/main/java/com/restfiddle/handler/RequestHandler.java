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
package com.restfiddle.handler;

import com.restfiddle.handler.http.DeleteHandler;
import com.restfiddle.handler.http.GenericHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.restfiddle.handler.http.GetHandler;
import com.restfiddle.handler.http.PostHandler;
import com.restfiddle.handler.http.PutHandler;

@Component
@Deprecated
public class RequestHandler extends AbstractRequestHandler {

    @Autowired
    GetHandler getHandler;

    @Autowired
    PostHandler postHandler;

    @Autowired
    PutHandler putHandler;

    @Autowired
    DeleteHandler deleteHandler;

    public GenericHandler getHandler(String methodType) {
	switch (methodType.toLowerCase()) {
	case "get":
	    return getHandler;
	case "put":
	    return putHandler;
	case "delete":
	    return deleteHandler;
	case "post":
	    return postHandler;
	}
	return getHandler;
    }

}
