/**
 *
 */
package com.boothen.jsonedit.core;


import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.boothen.jsonedit.log.JsonLog;

/**
 * Main plug class that integrates with Eclipse.
 *
 * @author Matt Garner
 *
 */
public class JsonEditorPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.boothen.jsonedit.core";

	public JsonEditorPlugin() {
		JsonLog.getInstance(PLUGIN_ID, super.getLog());
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}
}
