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
package com.boothen.jsonedit.core.util;




/**
 * Utility class to determine if a character is a special Json character.
 *
 * @author Matt Garner
 *
 */
public final class JsonCharUtility {

    public static final char openCurly = '{';
    public static final char closeCurly = '}';
    public static final char openSquare = '[';
    public static final char closeSquare = ']';
    public static final char comma = ',';
    public static final char quote = '"';
    public static final char colon = ':';
    public static final char slash = '\\';
    public static final char minus = '-';
    public static final char plus = '+';
    public static final char point = '.';
    public static final char n = 'n';
    public static final char u = 'u';
    public static final char l = 'l';
    public static final char f = 'f';
    public static final char a = 'a';
    public static final char s = 's';
    public static final char e = 'e';
    public static final char t = 't';
    public static final char r = 'r';
    public static final char E = 'E';
    public static final char eof = (char) -1;
    public static final int space = ' ';
    public static final int eol = '\n';
    public static final int tab = '\t';
    public static final int carriageReturn = '\r';

    public static boolean isWhiteSpace(char ch) {
        if (ch == space || ch == eol || ch == tab || ch == carriageReturn) {
            return true;
        }

        return false;
    }

    public static boolean isNotWhiteSpace(char ch) {
        if (ch != space && ch != eol && ch != tab && ch != carriageReturn) {
            return true;
        }

        return false;
    }

    public static boolean isClosed(char ch) {
        if (ch == comma || ch == closeCurly || ch == closeSquare) {
            return true;
        }

        return false;
    }

    public static boolean isNotClosed(char ch) {
        if (ch != comma && ch != closeCurly && ch != closeSquare) {
            return true;
        }
        return false;
    }

    public static boolean isJsonChar(char ch, char previous) {
        if (ch == comma || ch == openCurly || ch == closeCurly ||
                ch == openSquare || ch == closeSquare || ch == colon ||
                (ch == quote && previous != slash)) {
            return true;
        }

        return false;
    }
}
