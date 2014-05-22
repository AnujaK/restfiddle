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
package com.restfiddle.entity;

import java.util.ArrayList;
import java.util.List;

//NOTE : TreeNode is NOT an Entity.
public class TreeNode extends BaseNode {
    private static final long serialVersionUID = 1L;

    private BaseNode parent;

    private List<BaseNode> children = new ArrayList<BaseNode>();

    public BaseNode getParent() {
	return parent;
    }

    public void setParent(BaseNode parent) {
	this.parent = parent;
    }

    public List<BaseNode> getChildren() {
	return children;
    }

    public void setChildren(List<BaseNode> children) {
	this.children = children;
    }
}
