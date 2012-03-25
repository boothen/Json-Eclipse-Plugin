/**
 * 
 */
package org.sourceforge.jsonedit.core.outline;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.sourceforge.jsonedit.core.model.jsonnode.JsonNode;
import org.sourceforge.jsonedit.core.model.jsonnode.JsonType;
import org.sourceforge.jsonedit.core.outline.node.JsonTreeNode;


/**
 * @author garner_m
 *
 */
public class JsonModelOutlineParser {
	
	
	public JsonModelOutlineParser() {
		super();
	}

	public JsonTreeNode parse(List<JsonNode> jsonNodes) {
		
		if (jsonNodes == null) {
			return null;
		}
		
		JsonTreeNode root = null;
		JsonTreeNode parent = null;
		
		for (JsonNode jsonNode : jsonNodes) {
			
			if (isNodeOfType(jsonNode, JsonType.End)) {
				if (parent != null) {
					parent = parent.getParent();
				}
				continue;
			}
			
			JsonTreeNode treeNode = new JsonTreeNode(jsonNode, parent);
			if (root == null) {
				root = treeNode;
				parent = root;
			} else {
				if (parent == null) {
					parent = root;
				}
				parent.addChild(treeNode);
			}
			
			if (isNodeOfType(jsonNode, JsonType.Object, JsonType.Array)) {
				parent = treeNode;
			}
		}
		return root;
	}
	
	public JsonTreeNode mergeNodes(JsonTreeNode oldRootObject, List<JsonNode> jsonNodes) {
		
		if (jsonNodes == null) {
			return oldRootObject;
		}
		
		if (oldRootObject == null && jsonNodes != null) {
			return parse(jsonNodes);
		}
		
		JsonTreeNode root = oldRootObject;
		
		JsonTreeNode parent = null;
		
		JsonTreeNode head = root;
		
		List<Integer> childIterStack = new ArrayList<Integer>();
		
		try {
			Integer childIter = 0;
			for (JsonNode jsonNode : jsonNodes) {
				//System.out.println(jsonNode);
				
				if (isNodeOfType(jsonNode, JsonType.End)) {
					
					// TODO: Check no more children if so remove
					List<JsonTreeNode> nodesToRemove = new LinkedList<JsonTreeNode>();
					while(childIter < parent.getChildren().size()) {
						head = parent.getChildren().get(childIter);
						nodesToRemove.add(head);
						childIter++;
					}
						
					//System.out.println(nodesToRemove.size());
					for (JsonTreeNode nodeToRemove: nodesToRemove) {
						nodeToRemove.removeFromParent();
					}
					
					if (childIterStack.size() > 0) {
						childIter = childIterStack.remove(0);
					} else {
						childIter = root.getChildren().size();
					}

					parent = parent.getParent();
					if (parent == null) {
						parent = root;
					}
					continue;
				}
				
				if (parent != null && childIter < parent.getChildren().size()) {
					head = parent.getChildren().get(childIter);
					//System.out.println("Next");
					childIter++;
				} else if (head != root || (head == root && parent != null)) {
					//System.out.println("Null");
					head = null;
				}

				if (head == null) {
					head = new JsonTreeNode(jsonNode, parent);
					parent.addChild(head);
					childIter++;
				}
				
				if (!jsonNode.equals(head.getJsonNode())) {
					head.setJsonNode(jsonNode);
				}
				
				if (isNodeOfType(jsonNode, JsonType.Object, JsonType.Array)) {
					parent = head;
					childIterStack.add(0, childIter);
					childIter = 0;
				} else {
					if (head.hasChildren()) {
						head.clearChildren();
					}
				}
			}
		//	System.out.println("returning");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return root;
	}
	
	private boolean isNodeOfType(JsonNode jsonNode, JsonType ... jsonTypes) {
		
		for (JsonType jsonType :jsonTypes) {
			if (jsonNode.getJsonType() == jsonType) {
				return true;
			}
		}
		return false;
	}
}
