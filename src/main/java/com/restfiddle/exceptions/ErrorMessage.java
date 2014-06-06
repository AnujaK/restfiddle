/*
 * Copyright 2014 Santosh Mishra
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

package com.restfiddle.exceptions;

/**
 * Created by santoshm1 on 06/06/14.
 */
public class ErrorMessage {

    private String reason;

    public ErrorMessage(String reason) {
        this.reason = reason;
    }

    public ErrorMessage(ApiException ex) {
       this.reason = ex.getMessage();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
