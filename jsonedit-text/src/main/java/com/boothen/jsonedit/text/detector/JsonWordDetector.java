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
/**
 *
 */
package com.boothen.jsonedit.text.detector;

import org.eclipse.jface.text.rules.IWordDetector;

/**
 * Word detector for JsonScanner
 *
 * @author Matt Garner
 *
 */
public class JsonWordDetector implements IWordDetector {

    @Override
    public boolean isWordPart(char character) {
        return Character.isJavaIdentifierPart(character);
    }

    @Override
    public boolean isWordStart(char character) {
        return Character.isJavaIdentifierPart(character);
    }

}
