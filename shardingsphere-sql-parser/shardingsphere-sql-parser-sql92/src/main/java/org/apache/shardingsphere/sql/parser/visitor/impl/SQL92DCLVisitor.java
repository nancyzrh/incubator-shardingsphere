/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.sql.parser.visitor.impl;

import org.apache.shardingsphere.sql.parser.api.visitor.DCLVisitor;
import org.apache.shardingsphere.sql.parser.autogen.SQL92StatementParser.GrantContext;
import org.apache.shardingsphere.sql.parser.autogen.SQL92StatementParser.PrivilegeClauseContext;
import org.apache.shardingsphere.sql.parser.autogen.SQL92StatementParser.RevokeContext;
import org.apache.shardingsphere.sql.parser.sql.ASTNode;
import org.apache.shardingsphere.sql.parser.sql.segment.generic.TableSegment;
import org.apache.shardingsphere.sql.parser.sql.statement.dcl.GrantStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.dcl.RevokeStatement;
import org.apache.shardingsphere.sql.parser.visitor.SQL92Visitor;

import java.util.Collection;
import java.util.Collections;

/**
 * DCL visitor for SQL92.
 */
public final class SQL92DCLVisitor extends SQL92Visitor implements DCLVisitor {
    
    @Override
    public ASTNode visitGrant(final GrantContext ctx) {
        GrantStatement result = new GrantStatement();
        if (null != ctx.privilegeClause()) {
            for (TableSegment each : getTableFromPrivilegeClause(ctx.privilegeClause())) {
                result.getTables().add(each);
            }
        }
        return result;
    }
    
    @Override
    public ASTNode visitRevoke(final RevokeContext ctx) {
        RevokeStatement result = new RevokeStatement();
        if (null != ctx.privilegeClause()) {
            for (TableSegment each : getTableFromPrivilegeClause(ctx.privilegeClause())) {
                result.getTables().add(each);
            }
        }
        return result;
    }
    
    private Collection<TableSegment> getTableFromPrivilegeClause(final PrivilegeClauseContext ctx) {
        return Collections.singletonList((TableSegment) visit(ctx.onObjectClause().privilegeLevel().tableName()));
    }
}
