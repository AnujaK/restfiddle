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

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AbstractRequestHandler {
    private static int TIME_OUT = 30000;

    public HttpURLConnection makeHttpConnection(String requestUrl, String method, String query) throws IOException {
	URL httpUrl = new URL(requestUrl);

	HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
	connection.setConnectTimeout(TIME_OUT);
	connection.setReadTimeout(TIME_OUT);
	connection.setUseCaches(false);
	connection.setDoOutput(true);
	connection.setRequestMethod(method);
	connection.setRequestProperty("Content-Type", String.format("application/x-www-form-urlencoded;charset=%s", new Object[] { "UTF-8" }));
	OutputStream output = null;
	try {
	    output = connection.getOutputStream();
	    output.write(query.getBytes("UTF-8"));
	} finally {
	    if (output != null)
		output.close();
	}
	return connection;
    }
}
