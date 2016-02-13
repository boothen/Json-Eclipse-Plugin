/*
 * Copyright 2016 Martin Steiger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.boothen.jsonedit.preferences;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The bundle activator class.
 */
public class JsonPreferencesPlugin extends AbstractUIPlugin {

    /**
     * The plug-in ID
     */
    public static final String PLUGIN_ID = "jsonedit-preferences";
//    public static final String PLUGIN_ID = "com.boothen.jsonedit.preferences";

    private static JsonPreferencesPlugin plugin;

    @Override
    public void start(BundleContext context) throws Exception {
        plugin = this;
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
    }

    /**
     * Returns the shared instance
     * @return the shared instance
     */
    public static JsonPreferencesPlugin getDefault() {
        return plugin;
    }

}
