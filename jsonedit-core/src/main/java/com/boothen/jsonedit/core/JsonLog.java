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
package com.boothen.jsonedit.core;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Logging helper class.
 *
 * @author Matt Garner
 *
 */
public class JsonLog {

    /**
     * Log info method.
     * @param message the message to log
     */
    public static void logInfo(String message) {
        report(IStatus.INFO, IStatus.OK, message, null);
    }

    /**
     * Log error method.
     * @param exception the exception to log (can be <code>null</code>)
     */
    public static void logError(Throwable exception) {
        logError("Unexpected Exception", exception);
    }

    /**
     * Log error method.
     * @param message the message to log
     * @param exception the linked exception (can be <code>null</code>)
     */
    public static void logError(String message, Throwable exception) {
        report(IStatus.ERROR, IStatus.OK, message, exception);
    }

    private static void report(int severity, int code, String message, Throwable orgException) {
        String pluginId = JsonCorePlugin.PLUGIN_ID;
        Throwable exception = orgException;
        if (exception == null) {
            // this will provide the stack trace
            exception = new RuntimeException();
        }
        Status status = new Status(severity, pluginId, code, message, exception);
        StatusManager.getManager().handle(status, StatusManager.LOG);
    }
}
