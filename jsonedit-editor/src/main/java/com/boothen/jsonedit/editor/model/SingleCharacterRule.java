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
package com.boothen.jsonedit.editor.model;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class SingleCharacterRule implements IPredicateRule {

    private final char tChar;
    private final IToken success;
    
    public SingleCharacterRule(char tChar, IToken success) {
        this.tChar = tChar;
        this.success = success;
    }

    @Override
    public IToken evaluate(ICharacterScanner scanner) {
        int i = scanner.read();
        if (i == -1) {
            return Token.EOF;
        }
        
        if (i == tChar) {
            return success;
        }
        
        scanner.unread();
        return Token.UNDEFINED;
    }

    @Override
    public IToken getSuccessToken() {
        return success;
    }

    @Override
    public IToken evaluate(ICharacterScanner scanner, boolean resume) {
        return evaluate(scanner);
    }

}
