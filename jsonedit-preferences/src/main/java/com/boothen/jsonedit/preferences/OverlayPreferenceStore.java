package com.boothen.jsonedit.preferences;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

/**
 * An overlay to an existing {@link IPreferenceStore}, similar to
 * org.eclipse.ui.internal.editors.text.OverlayPreferenceStore.
 * <p>
 * It does not perfectly adhere to the interface, so use with care.
 * <p>
 * The {@link #writeThrough} method persists changes to this instance to the parent.
 */
public class OverlayPreferenceStore implements IPreferenceStore {

    private final Map<String, Object> data = new LinkedHashMap<>();
    private final List<IPropertyChangeListener> listeners = new ArrayList<>();
    private final IPreferenceStore parent;

    /**
     * Takes a preference store that is used to retrieve data.
     * @param parent the parent preference store
     */
    public OverlayPreferenceStore(IPreferenceStore parent) {
        this.parent = parent;
    }

    @Override
    public void addPropertyChangeListener(IPropertyChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removePropertyChangeListener(IPropertyChangeListener listener) {
        listeners.remove(listener);
    }

    /**
     * Writes are changed properties through to the parent instance. Implicitly calls reset() afterwards.
     */
    public void writeThrough() {
        for (Entry<String, Object> entry : data.entrySet()) {
            String value = entry.getValue().toString();
            String key = entry.getKey();
            parent.setValue(key, value);
        }
        reset();
    }

    /**
     * Clear all overlay changes.
     */
    public void reset() {
        data.clear();
    }

    @Override
    public void firePropertyChangeEvent(String name, Object oldValue, Object newValue) {
        PropertyChangeEvent event = new PropertyChangeEvent(this, name, oldValue, newValue);
        for (IPropertyChangeListener listener : listeners) {
            listener.propertyChange(event);
        }
    }

    @Override
    public boolean contains(String name) {
        return data.containsKey(name);
    }

    @Override
    public boolean getBoolean(String name) {
        Object val = data.get(name);
        if (val != null) {
            return ((Boolean) val).booleanValue();
        }
        return parent.getBoolean(name);
    }

    @Override
    public boolean getDefaultBoolean(String name) {
        return parent.getDefaultBoolean(name);
    }

    @Override
    public double getDefaultDouble(String name) {
        return parent.getDefaultDouble(name);
    }

    @Override
    public float getDefaultFloat(String name) {
        return parent.getDefaultFloat(name);
    }

    @Override
    public int getDefaultInt(String name) {
        return parent.getDefaultInt(name);
    }

    @Override
    public long getDefaultLong(String name) {
        return parent.getDefaultLong(name);
    }

    @Override
    public String getDefaultString(String name) {
        return parent.getDefaultString(name);
    }

    @Override
    public double getDouble(String name) {
        Object val = data.get(name);
        if (val != null) {
            return ((Double) val).doubleValue();
        }
        return parent.getDouble(name);
    }

    @Override
    public float getFloat(String name) {
        Object val = data.get(name);
        if (val != null) {
            return ((Float) val).floatValue();
        }
        return parent.getFloat(name);
    }

    @Override
    public int getInt(String name) {
        Object val = data.get(name);
        if (val != null) {
            return ((Integer) val).intValue();
        }
        return parent.getInt(name);
    }

    @Override
    public long getLong(String name) {
        Object val = data.get(name);
        if (val != null) {
            return ((Long) val).longValue();
        }
        return parent.getLong(name);
    }

    @Override
    public String getString(String name) {
        Object val = data.get(name);
        if (val != null) {
            return ((String) val).toString();
        }
        return parent.getString(name);
    }

    @Override
    public boolean isDefault(String name) {
        if (data.containsKey(name)) {
            return false;
        }
        return parent.isDefault(name);
    }

    @Override
    public boolean needsSaving() {
        return !data.isEmpty();
    }

    @Override
    public void putValue(String name, String value) {
        data.put(name, value);
    }

    @Override
    public void setDefault(String name, double value) {
        parent.setDefault(name, value);
    }

    @Override
    public void setDefault(String name, float value) {
        parent.setDefault(name, value);
    }

    @Override
    public void setDefault(String name, int value) {
        parent.setDefault(name, value);
    }

    @Override
    public void setDefault(String name, long value) {
        parent.setDefault(name, value);
    }

    @Override
    public void setDefault(String name, String defaultObject) {
        parent.setDefault(name, defaultObject);
    }

    @Override
    public void setDefault(String name, boolean value) {
        parent.setDefault(name, value);
    }

    @Override
    public void setToDefault(String name) {
        data.remove(name);
        parent.setToDefault(name);
    }

    @Override
    public void setValue(String name, double value) {
        data.put(name, value);
    }

    @Override
    public void setValue(String name, float value) {
        data.put(name, value);
    }

    @Override
    public void setValue(String name, int value) {
        data.put(name, value);
    }

    @Override
    public void setValue(String name, long value) {
        data.put(name, value);
    }

    @Override
    public void setValue(String name, String value) {
        data.put(name, value);
    }

    @Override
    public void setValue(String name, boolean value) {
        data.put(name, value);
    }
}
