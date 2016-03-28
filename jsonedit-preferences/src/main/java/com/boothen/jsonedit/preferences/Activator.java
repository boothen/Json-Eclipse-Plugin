package com.boothen.jsonedit.preferences;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * This plugin's activator.
 */
public class Activator extends AbstractUIPlugin {

    /**
     * The plug-in ID
     */
    public static final String PLUGIN_ID = "jsonedit-preferences";

    /**
     * The font ID of JSON text editor. Query with {@link JFaceResources#getFont}.
     */
    public static final String FONT_ID = "com.boothen.jsonedit.fonts.textfont";

    private static Activator plugin;

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }
}
