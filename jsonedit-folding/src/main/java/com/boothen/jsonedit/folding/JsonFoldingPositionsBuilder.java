/*******************************************************************************
 * Copyright 2014 Boothen Technology Ltd.
 *
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://eclipse.org/org/documents/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.boothen.jsonedit.folding;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.text.Position;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import com.boothen.jsonedit.antlr.JSONBaseVisitor;
import com.boothen.jsonedit.antlr.JSONParser.ArrayContext;
import com.boothen.jsonedit.antlr.JSONParser.JsonContext;
import com.boothen.jsonedit.antlr.JSONParser.ObjectContext;
import com.boothen.jsonedit.core.model.jsonnode.JsonNode;
import com.boothen.jsonedit.type.JsonDocumentType;

/**
 * @author Matt Garner
 *
 */
public class JsonFoldingPositionsBuilder {

    private JsonContext jsonContext;

    public JsonFoldingPositionsBuilder(JsonContext jsonContext) {
        this.jsonContext = jsonContext;
    }

    public List<Position> buildFoldingPositions() {

        final List<Position> positions = new LinkedList<Position>();

        jsonContext.accept(new JSONBaseVisitor<Object>() {
            @Override
            public Object visitObject(ObjectContext ctx) {
                Object result = super.visitObject(ctx);
                int startIndex = ctx.start.getStartIndex();
                int stopIndex = ctx.stop.getStopIndex();
                positions.add(new Position(startIndex, stopIndex - startIndex));
                return result;
            }

            @Override
            public Object visitArray(ArrayContext ctx) {
                Object result = super.visitArray(ctx);
                int startIndex = ctx.start.getStartIndex();
                int stopIndex = ctx.stop.getStopIndex();
                positions.add(new Position(startIndex, stopIndex - startIndex));
                return result;
            }
        });

        return positions;
    }

    private boolean isJsonNodeType(JsonNode jsonNode, String ... jsonTypes) {

        for (String jsonType : jsonTypes) {
            if (jsonNode.getJsonType().equals(jsonType)) {
                return true;
            }
        }

        return false;
    }


}
