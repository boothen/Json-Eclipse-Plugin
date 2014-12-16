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
/**
 *
 */
package com.boothen.jsonedit.outline.node;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

import com.boothen.jsonedit.core.model.jsonnode.JsonNode;
import com.boothen.jsonedit.type.JsonDocumentType;


/**
 * @author garner_m
 *
 */
public class JsonTreeNode {

	private JsonNode jsonNode;

	private JsonTreeNode parent;

	private boolean textSelection;

	private static final Map<String, String> imageMap = new HashMap<String, String>();
	private static final Map<String, StyledString.Styler> styleMap = new HashMap<String, StyledString.Styler>();
	
	private static ImageRegistry imageRegistry;

	static {
		imageMap.put(JsonDocumentType.JSON_ARRAY_OPEN, "/icons/JsonArray.gif");
		imageMap.put(JsonDocumentType.JSON_OBJECT_OPEN, "/icons/JsonObject.gif");
		imageMap.put(JsonDocumentType.JSON_TRUE, "/icons/JsonBoolean.gif");
		imageMap.put(JsonDocumentType.JSON_FALSE, "/icons/JsonBoolean.gif");
		imageMap.put(JsonDocumentType.JSON_NUMBER, "/icons/JsonNumber.gif");
		imageMap.put(JsonDocumentType.JSON_NULL, "/icons/JsonNull.gif");
		imageMap.put(JsonDocumentType.JSON_STRING, "/icons/JsonString.gif");
		imageMap.put(JsonDocumentType.JSON_ERROR, "/icons/JsonError.gif");

		styleMap.put(JsonDocumentType.JSON_STRING, StyledString.createColorRegistryStyler("GREEN", "WHITE"));
		styleMap.put(JsonDocumentType.JSON_TRUE, StyledString.createColorRegistryStyler("BLACK", "WHITE"));
		styleMap.put(JsonDocumentType.JSON_FALSE, StyledString.createColorRegistryStyler("BLACK", "WHITE"));
		styleMap.put(JsonDocumentType.JSON_ERROR, StyledString.createColorRegistryStyler("RED", "WHITE"));
		styleMap.put(JsonDocumentType.JSON_NUMBER, StyledString.createColorRegistryStyler("BLUE", "WHITE"));
		styleMap.put(JsonDocumentType.JSON_NULL, StyledString.createColorRegistryStyler("BLUE", "WHITE"));
		styleMap.put(JsonDocumentType.JSON_OBJECT_OPEN, StyledString.createColorRegistryStyler("BLUE", "WHITE"));
		styleMap.put(JsonDocumentType.JSON_ARRAY_OPEN, StyledString.createColorRegistryStyler("BLUE", "WHITE"));
	}

	public JsonTreeNode(JsonNode jsonNode, JsonTreeNode parent) {
		super();
		this.jsonNode = jsonNode;
		this.parent = parent;
	}

	private List<JsonTreeNode> children = new LinkedList<JsonTreeNode>();

	public void removeEntry(JsonTreeNode jsonTreeNode) {
		children.remove(jsonTreeNode);
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public void clearChildren() {
		children.clear();
	}

	public List<JsonTreeNode> getChildren() {
		return children;
	}

	public void addChild(JsonTreeNode jsonTreeNode) {
		children.add(jsonTreeNode);
	}

	public void setParent(JsonTreeNode parent) {
		this.parent = parent;
	}

	public JsonTreeNode getParent() {
		return parent;
	}

	public void removeFromParent() {
		if (getParent() != null) {
			getParent().removeEntry(this);
		}
	}

	/**
	 * Returns true if the JsonElement was selected from a text event.
	 * @return
	 */
	public boolean isTextSelection() {
		return textSelection;
	}

	/**
	 * Set to true if the JsonElement is about to fire a notification event.
	 * @param textSelection
	 */
	public void setTextSelection(boolean textSelection) {
		this.textSelection = textSelection;
	}

	public StyledString getStyledString() {
		StyledString styledString = new StyledString();
		if (jsonNode.getKey() != null) {
			StyledString.Styler style1 = StyledString.createColorRegistryStyler("BLACK", "WHITE");
			styledString.append(jsonNode.getKey(), style1);
			StyledString.Styler style2 = StyledString.createColorRegistryStyler("BLACK", "WHITE");
			styledString.append(" : ", style2);
		}

		if (jsonNode.getValue() != null && (!jsonNode.getJsonType().equals(JsonDocumentType.JSON_ARRAY_OPEN )
				&& !jsonNode.getJsonType().equals(JsonDocumentType.JSON_OBJECT_OPEN))) {
			StyledString.Styler style3 = styleMap.get(jsonNode.getJsonType());
			styledString.append(jsonNode.getValue(), style3);
		}
		return styledString;
	}

	public Image getImage() {
		return imageRegistry.get(jsonNode.getJsonType());
	}

	public static void initializeImageRegistry(ImageRegistry reg) {
		imageRegistry = reg;
		for (Map.Entry<String, String> entry : imageMap.entrySet()) {
			Image image = createMyImage(entry.getValue());
			imageRegistry.put(entry.getKey(), image);
		}
	}
	
	private static Image createMyImage(String urlPath) {
		URL resource = JsonTreeNode.class.getResource(urlPath);
		ImageDescriptor imgDescriptor = ImageDescriptor.createFromURL(resource);
		if (imgDescriptor == null) {
			return null;
		}

		return imgDescriptor.createImage();
	}

	public int getStart() {
		return jsonNode.getStart();
	}

	public int getLength() {
		return jsonNode.getEnd() - jsonNode.getStart();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((jsonNode == null) ? 0 : jsonNode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JsonTreeNode other = (JsonTreeNode) obj;
		if (jsonNode == null) {
			if (other.jsonNode != null)
				return false;
		} else if (!jsonNode.equals(other.jsonNode))
			return false;
		return true;
	}

	public JsonNode getJsonNode() {
		return jsonNode;
	}

	public void setJsonNode(JsonNode jsonNode) {
		this.jsonNode = jsonNode;
	}

}
