/**
 *
 */
package com.boothen.jsonedit.core;


import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.boothen.jsonedit.log.JsonLog;
import com.boothen.jsonedit.preferences.JsonPreferenceStore;

/**
 * Main plug class that integrates with Eclipse.
 *
 * @author Matt Garner
 *
 */
public class JsonEditorPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.boothen.jsonedit.core";

	// Singleton plugin instance.
	private static JsonEditorPlugin fgInstance;

	public JsonEditorPlugin() {
		new JsonPreferenceStore(getPreferenceStore());
		JsonLog.getInstance(PLUGIN_ID, super.getLog());
		fgInstance = this;
	}

	/**
	 * Returns the default plug-in instance.
	 *
	 * @return the default plug-in instance
	 */
	public static JsonEditorPlugin getDefault() {
		return fgInstance;
	}


	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		fgInstance = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		fgInstance = null;
		super.stop(context);
	}
}
