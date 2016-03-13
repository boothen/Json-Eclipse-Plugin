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


import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * Main plug class that integrates with Eclipse.
 * The plugin is a singleton and only one instance exists at a time.
 * @author Matt Garner
 *
 */
public class JsonCorePlugin extends AbstractUIPlugin {

    /**
     * The plug-in ID
     */
//    public static final String PLUGIN_ID = "com.boothen.jsonedit.core";
    public static final String PLUGIN_ID = "jsonedit-core";

    private static final JsonColorProvider colorProvider = new JsonColorProvider();

    private static JsonCorePlugin plugin;

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        colorProvider.purge();
        plugin = null;
    }

    /**
     * Returns the shared instance
     * @return the shared instance
     */
    public static JsonCorePlugin getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in relative path
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    /**
     * @return the color manager/provider
     */
    public static JsonColorProvider getColorProvider() {
        return colorProvider;
    }
}
