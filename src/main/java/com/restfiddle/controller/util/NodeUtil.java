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
package com.restfiddle.controller.util;

import java.util.List;

import com.restfiddle.entity.BaseNode;

public class NodeUtil {

    private NodeUtil() {}

    public static long findLastChildPosition(List<BaseNode> children) {
	if (children == null || children.isEmpty()) {
	    return 0;
	}
	long temp = 0;
	for (BaseNode childNode : children) {
	    if (childNode.getPosition() > temp) {
		temp = childNode.getPosition();
	    }
	}
	return temp;
    }
}
