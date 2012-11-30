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
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

import com.boothen.jsonedit.core.model.jsonnode.JsonNode;
import com.boothen.jsonedit.core.model.jsonnode.JsonType;
import com.boothen.jsonedit.core.model.node.Node;


/**
 * @author garner_m
 *
 */
public class JsonTreeNode {

	private JsonNode jsonNode;

	private JsonTreeNode parent;

	private boolean textSelection;

	private Image image;

	private static final Map<JsonType, String> imageMap = new HashMap<JsonType, String>();
	private static final Map<JsonType, StyledString.Styler> styleMap = new HashMap<JsonType, StyledString.Styler>();

	static {
		imageMap.put(JsonType.Array, "/icons/JsonArray.gif");
		imageMap.put(JsonType.Object, "/icons/JsonObject.gif");
		imageMap.put(JsonType.True, "/icons/JsonBoolean.gif");
		imageMap.put(JsonType.False, "/icons/JsonBoolean.gif");
		imageMap.put(JsonType.Value, "/icons/JsonNumber.gif");
		imageMap.put(JsonType.Null, "/icons/JsonNull.gif");
		imageMap.put(JsonType.String, "/icons/JsonString.gif");
		imageMap.put(JsonType.Error, "/icons/JsonError.gif");

		styleMap.put(JsonType.String, StyledString.createColorRegistryStyler("GREEN", "WHITE"));
		styleMap.put(JsonType.True, StyledString.createColorRegistryStyler("BLACK", "WHITE"));
		styleMap.put(JsonType.False, StyledString.createColorRegistryStyler("BLACK", "WHITE"));
		styleMap.put(JsonType.Error, StyledString.createColorRegistryStyler("RED", "WHITE"));
		styleMap.put(JsonType.Value, StyledString.createColorRegistryStyler("BLUE", "WHITE"));
		styleMap.put(JsonType.Null, StyledString.createColorRegistryStyler("BLUE", "WHITE"));
		styleMap.put(JsonType.Object, StyledString.createColorRegistryStyler("BLUE", "WHITE"));
		styleMap.put(JsonType.Array, StyledString.createColorRegistryStyler("BLUE", "WHITE"));
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
			styledString.append(jsonNode.getKey().getValue(), style1);
			StyledString.Styler style2 = StyledString.createColorRegistryStyler("BLACK", "WHITE");
			styledString.append(" : ", style2);
		}

		if (jsonNode.getValue() != null && (jsonNode.getJsonType() != JsonType.Object && jsonNode.getJsonType() != JsonType.Array)) {
			StyledString.Styler style3 = styleMap.get(jsonNode.getJsonType());
			styledString.append(jsonNode.getValue().getValue(), style3);
		}
		return styledString;
	}

	public Image getImage() {
		if (image != null) {
			return image;
		}

		image = createMyImage(imageMap.get(jsonNode.getJsonType()));
		return image;
	}

	private Image createMyImage(String urlPath) {
		URL resource = JsonTreeNode.class.getResource(urlPath);
		ImageDescriptor imgDescriptor = ImageDescriptor.createFromURL(resource);
		if (imgDescriptor == null) {
			return null;
		}

		return imgDescriptor.createImage();
	}

	public int getStart() {
		Node startNode = jsonNode.getKey();
		if (startNode == null) {
			startNode = jsonNode.getValue();
		}

		return startNode.getStart();
	}

	public int getLength() {
		Node startNode = jsonNode.getKey();
		if (startNode == null) {
			startNode = jsonNode.getValue();
		}
		Node endNode = jsonNode.getValue();
		if (endNode == null) {
			endNode = jsonNode.getKey();
		}

		return endNode.getEnd() - startNode.getStart();
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
		image = null;
		this.jsonNode = jsonNode;
	}

}
