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

import com.boothen.jsonedit.antlr.JSONParser.JsonContext;

public class JsonFoldingPositionsBuilder {

    public List<Position> getFoldingPositions(JsonContext jsonContext) {
        List<Position> positions = new LinkedList<Position>();
        jsonContext.accept(new FoldingVisitor(positions));
        return positions;
    }

}
