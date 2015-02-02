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
package com.restfiddle.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restfiddle.entity.BaseNode;

public interface NodeRepository extends RfRepository<BaseNode, Long> {

    // TODO : Also check for hasChildren.
    @Query("SELECT bn FROM BaseNode bn WHERE bn.parentId = :parentId")
    public BaseNode getParent(@Param("parentId") Long parentId);

    @Query("SELECT bn FROM BaseNode bn WHERE bn.parentId = :nodeId")
    public List<BaseNode> getChildren(@Param("nodeId") Long nodeId);

    @Query("SELECT bn FROM BaseNode bn WHERE bn.project.id = :projectId")
    public List<BaseNode> findNodesFromAProject(@Param("projectId") Long projectId);

    @Query("SELECT bn FROM BaseNode bn WHERE bn.starred = :starred")
    public List<BaseNode> findStarredNodes(@Param("starred") Boolean starred);
    
    @Query("SELECT bn FROM BaseNode bn, Tag t WHERE t MEMBER OF bn.tags AND t.id = :tagId")
    public List<BaseNode> findTaggedNodes(@Param("tagId") Long tagId);
    
}
