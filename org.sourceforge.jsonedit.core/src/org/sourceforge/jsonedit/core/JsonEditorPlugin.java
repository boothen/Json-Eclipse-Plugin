/**
 * 
 */
package org.sourceforge.jsonedit.core;


import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.sourceforge.jsonedit.core.text.JsonScanner;
import org.sourceforge.jsonedit.core.util.JsonColorProvider;

/**
 * Main plug class that integrates with Eclipse.
 * 
 * @author Matt Garner
 *
 */
public class JsonEditorPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.sourceforge.jsonedit.core";
	
	// Singleton plugin instance.
	private static JsonEditorPlugin fgInstance;
	
	// JsonScanner instance used for text coloring.
	private JsonScanner fJsonScanner;
	
	// Color provider used by JsonScanner.
	private JsonColorProvider fColorProvider;
	
	public static final String SPACES_FOR_TABS = "spaces_for_tabs"; //$NON-NLS-1$
	public static final String NUM_SPACES = "num_spaces"; //$NON-NLS-1$
	public final static String EDITOR_MATCHING_BRACKETS = "matchingBrackets"; //$NON-NLS-1$	
	public final static String EDITOR_MATCHING_BRACKETS_COLOR =  "matchingBracketsColor"; //$NON-NLS-1$
	

	public JsonEditorPlugin() {
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
	
	/**
	 * Returns the Json Scanner used for text coloring.
	 * 
	 * @return JsonScanner
	 */
	public RuleBasedScanner getJsonScanner(){
		if (fJsonScanner == null) {
			fJsonScanner = new JsonScanner(getColorProvider());
		}
		return fJsonScanner;
	}

	/**
	 * Returns the singleton Java color provider.
	 * 
	 * @return the singleton Java color provider
	 */
	public JsonColorProvider getColorProvider() {
		if (fColorProvider == null)
			fColorProvider= new JsonColorProvider();
		return fColorProvider;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		fgInstance = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		fgInstance = null;
		super.stop(context);
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	/**
	 * Returns the default preference store.
	 * 
	 * @return IPreferenceStore
	 */
	public static IPreferenceStore getJsonPreferenceStore() {
		IPreferenceStore store =
			getDefault().getPreferenceStore();
		JsonColorProvider provider = getDefault().getColorProvider();
		store.setDefault(SPACES_FOR_TABS, true);
		store.setDefault(NUM_SPACES, 4);
		store.setDefault(EDITOR_MATCHING_BRACKETS, true);
		store.setDefault(EDITOR_MATCHING_BRACKETS_COLOR, provider.getColor(JsonColorProvider.STRING).toString());
		return store;
	}
}
