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
package com.boothen.jsonedit.log;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Logging helper class.
 *
 * @author Matt Garner
 *
 */
public class JsonLog {

    private static JsonLog singleton;

    private String pluginId;
    private ILog log;

    private JsonLog(String pluginId, ILog log) {
        this.pluginId = pluginId;
        this.log = log;
    }

    public static JsonLog getInstance(String pluginId, ILog log) {
        if (singleton != null) {
            return singleton;
        }
        singleton = new JsonLog(pluginId, log);
        return singleton;
    }
    /**
     * Log info method.
     * @param message
     */
    public static void logInfo(String message) {
        log(IStatus.INFO, IStatus.OK, message, null);
    }

    /**
     * Log error method.
     * @param exception
     */
    public static void logError(Throwable exception) {
        logError("Unexpected Exception", exception);
    }

    /**
     * Log error method.
     * @param message
     * @param exception
     */
    public static void logError(String message, Throwable exception) {
        log(IStatus.ERROR, IStatus.OK, message, exception);
    }

    /**
     * Log method.
     *
     * @param severity
     * @param code
     * @param message
     * @param exception
     */
    public static void log(int severity, int code, String message, Throwable exception) {
        log(createStatus(severity, code, message, exception));
    }

    /**
     * Create status method.
     *
     * @param severity
     * @param code
     * @param message
     * @param exception
     * @return
     */
    public static IStatus createStatus(int severity, int code, String message, Throwable exception) {
        return new Status(severity, singleton.pluginId, code, message, exception);
    }

    /**
     * Log status method.
     * @param status
     */
    public static void log(IStatus status) {
        singleton.log.log(status);
    }
}
