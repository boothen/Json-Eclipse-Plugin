package org.sourceforge.jsonedit.core.model.jsonnode;

import java.util.ArrayList;
import java.util.List;


import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.sourceforge.jsonedit.core.model.node.Node;
import org.sourceforge.jsonedit.core.model.node.Type;



public class JsonNodeBuilder {
	
	private IDocument fDocument;
	
	private List<Node> nodes;
	
	private List<JsonNode> jsonNodes;
	
	private List<JsonNode> objectArrayStack;
	
	public JsonNodeBuilder(IDocument document, List<Node> nodes) {
		super();
		fDocument = document;
		this.nodes = nodes;
	}
	
	public List<JsonNode> buildJsonNodes() {
		
		try {
			return privateBuildJsonNodes();
		} catch (Exception e) {
//			e.printStackTrace();
//			for (Node node : nodes) {
//				System.out.println(node);
//			}
		}
		
		return null;
	}
	
	private List<JsonNode> privateBuildJsonNodes() {
		
		int index = 0;
		jsonNodes = new ArrayList<JsonNode>();
		objectArrayStack = new ArrayList<JsonNode>();
		boolean parseMatched = false; 
		
		List<Node> errorNodes = new ArrayList<Node>();
		while (index < nodes.size()) {
			
			if (parseMatched) {
				// check for reparse error text.
				if (errorNodes.size() > 0) {
					int oldLength = nodes.size();
					List<JsonNode> reparsedNodes = reparseErrorNodes(errorNodes);
					int stepBack = (jsonNodes.get(jsonNodes.size()-1).getJsonType() == JsonType.End) ? 2 : 1;
					jsonNodes.addAll(jsonNodes.size() - stepBack, reparsedNodes);
					errorNodes.clear();
					int newLength = nodes.size();
					index += (newLength - oldLength);
				}
			}
			
			parseMatched = true;
			if (isStringNode(index)) {
				if (isNodeType(index + 3, nodes, Type.Colon)) {
					int retIndex = doCompare(index, index + 4, index + 1, index + 4, JsonType.Value, Type.Number, Type.Comma);
					if (retIndex != -1) {index = retIndex; continue;}
					retIndex = doCompare(index, index + 4, index + 1, index + 4, JsonType.Null, Type.Null, Type.Comma);
					if (retIndex != -1) {index = retIndex; continue;}
					retIndex = doCompare(index, index + 4, index + 1, index + 4, JsonType.True, Type.True, Type.Comma);
					if (retIndex != -1) {index = retIndex; continue;}
					retIndex = doCompare(index, index + 4, index + 1, index + 4, JsonType.False, Type.False, Type.Comma);
					if (retIndex != -1) {index = retIndex; continue;}
					retIndex = doCompare(index, index + 4, index + 1, index + 5, JsonType.String, Type.Quote, Type.String, Type.Quote, Type.Comma);
					if (retIndex != -1) {index = retIndex; continue;}
					retIndex = doCompare(index, index + 4, index + 1, index + 4, JsonType.Value, Type.Number, Type.CloseObject, Type.Comma);
					if (retIndex != -1) {index = retIndex; continue;}
					retIndex = doCompare(index, index + 4, index + 1, index + 4, JsonType.Null, Type.Null, Type.CloseObject, Type.Comma);
					if (retIndex != -1) {index = retIndex; continue;}
					retIndex = doCompare(index, index + 4, index + 1, index + 4, JsonType.True, Type.True, Type.CloseObject, Type.Comma);
					if (retIndex != -1) {index = retIndex; continue;}
					retIndex = doCompare(index, index + 4, index + 1, index + 4, JsonType.False, Type.False, Type.CloseObject, Type.Comma);
					if (retIndex != -1) {index = retIndex; continue;}
					retIndex = doCompare(index, index + 4, index + 1, index + 5, JsonType.String, Type.Quote, Type.String, Type.Quote, Type.CloseObject, Type.Comma);
					if (retIndex != -1) {index = retIndex; continue;}
					retIndex = doCompare(index, index + 4, index + 1, index + 4, JsonType.Value, Type.Number, Type.CloseObject);
					if (retIndex != -1) {index = retIndex; continue;}
					retIndex = doCompare(index, index + 4, index + 1, index + 4, JsonType.Null, Type.Null, Type.CloseObject);
					if (retIndex != -1) {index = retIndex; continue;}
					retIndex = doCompare(index, index + 4, index + 1, index + 4, JsonType.True, Type.True, Type.CloseObject);
					if (retIndex != -1) {index = retIndex; continue;}
					retIndex = doCompare(index, index + 4, index + 1, index + 4, JsonType.False, Type.False, Type.CloseObject);
					if (retIndex != -1) {index = retIndex; continue;}
					retIndex = doCompare(index, index + 4, index + 1, index + 5, JsonType.String, Type.Quote, Type.String, Type.Quote, Type.CloseObject);
					if (retIndex != -1) {index = retIndex; continue;}
					retIndex = doCompare(index, index + 4, index + 1, index + 4, JsonType.Object, Type.OpenObject);
					if (retIndex != -1) {index = retIndex; continue;}
					retIndex = doCompare(index, index + 4, index + 1, index + 4, JsonType.Array, Type.OpenArray);
					if (retIndex != -1) {index = retIndex; continue;}
				}
				
				// Need to check String value
				int retIndex = doCompare(index, index + 3, -1, index + 1, JsonType.String, Type.CloseArray, Type.Comma);
				if (retIndex != -1) {index = retIndex; continue;}
				
				// Need to check String value
				retIndex = doCompare(index, index + 3, -1, index + 1, JsonType.String, Type.CloseArray);
				if (retIndex != -1) {index = retIndex; continue;}
				
				// Need to check String value
				retIndex = doCompare(index, index + 3, -1, index + 1, JsonType.String, Type.Comma);
				if (retIndex != -1) {index = retIndex; continue;}
				
			}
			
			int retIndex = buildType(index, JsonType.Value, Type.Number);
			if (retIndex != index) { index = retIndex; continue; }
			retIndex = buildType(index, JsonType.Null, Type.Null);
			if (retIndex != index) { index = retIndex; continue; }
			retIndex = buildType(index, JsonType.True, Type.True);
			if (retIndex != index) { index = retIndex; continue; }
			retIndex = buildType(index, JsonType.False, Type.False);
			if (retIndex != index) { index = retIndex; continue; }
			
			retIndex = buildOpen(index, JsonType.Object, Type.OpenObject, errorNodes);
			if (retIndex != index) { index = retIndex; continue; }
			
			retIndex = buildOpen(index, JsonType.Array, Type.OpenArray, errorNodes);
			if (retIndex != index) { index = retIndex; continue; }
			
			retIndex = buildCloseWithComma(index, JsonType.Object, Type.OpenObject, Type.CloseObject);
			if (retIndex != index) { index = retIndex; continue; }
			
			retIndex = buildCloseWithComma(index, JsonType.Array, Type.OpenArray, Type.CloseArray);
			if (retIndex != index) { index = retIndex; continue; }
			
			retIndex = buildClose(index, JsonType.Object, Type.OpenObject, Type.CloseObject);
			if (retIndex != index) { index = retIndex; continue; }
			
			retIndex = buildClose(index, JsonType.Array, Type.OpenArray, Type.CloseArray);
			if (retIndex != index) { index = retIndex; continue; }
			
			// No match so add to error node
			errorNodes.add(nodes.get(index));
			index++;
			parseMatched = false;
		}
		
		// Need to check model at the end in case no parsing was matched.
		int stepBack = 0;
		if (parseMatched) {
			stepBack = 1;
		}
		
		// check for reparse error text.
		if (errorNodes.size() > 0) {
			List<JsonNode> reparsedNodes = reparseErrorNodes(errorNodes);
			stepBack = (jsonNodes.get(jsonNodes.size()-1).getJsonType() == JsonType.End) ? stepBack + 1 : stepBack;
			jsonNodes.addAll(jsonNodes.size() - stepBack, reparsedNodes);
			errorNodes.clear();
		}

		
		return jsonNodes;
	}
	
	private List<JsonNode> reparseErrorNodes(List<Node> errorNodes) {
		
		List<JsonNode> reparsedJsonNodes = new ArrayList<JsonNode>();
		List<Node> reparsedNodes = new ArrayList<Node>();
				
		for (int continueIndex = 0; continueIndex < errorNodes.size(); continueIndex++) {
			// Reparse text if starts with Quote 
			
			int startIndex = continueIndex;
			if (isNodeType(continueIndex, errorNodes, Type.Quote)) {
				
				boolean stringBuilt = false;
				if(!isNodeType(continueIndex + 1, errorNodes, Type.String)) {

					// find next quote
					for (int i = continueIndex + 1; i < errorNodes.size(); i++) {
						if (isNodeType(i, errorNodes, Type.Quote)) {
							// build string
							Node newNode = convertToString(errorNodes.get(continueIndex), errorNodes.get(i));
							reparsedNodes.add(errorNodes.get(continueIndex));
							reparsedNodes.add(newNode);
							reparsedNodes.add(errorNodes.get(i));
							continueIndex = i + 1;
							stringBuilt = true;
							break;
						}
					}
				} else if (errorNodes.size() >= continueIndex + 3){
					reparsedNodes.addAll(errorNodes.subList(continueIndex, continueIndex + 3));
					continueIndex += 3;
					stringBuilt = true;
				} 

				if (stringBuilt && continueIndex == errorNodes.size()) {
					JsonNode newJsonNode = new JsonNode(null, reparsedNodes.get(1), JsonType.String);
					reparsedJsonNodes.add(newJsonNode);
					for(Node node : reparsedNodes) {
						node.setOwner(newJsonNode);
					}
					int nodesPos = nodes.indexOf(errorNodes.get(startIndex));
					nodes.removeAll(errorNodes.subList(startIndex, continueIndex));
					nodes.addAll(nodesPos, reparsedNodes);
					reparsedNodes.clear();
					break;
				}


				if (stringBuilt && isNodeType(continueIndex, errorNodes, Type.Comma)) {
					JsonNode newJsonNode = new JsonNode(null, reparsedNodes.get(1), JsonType.String);
					reparsedJsonNodes.add(newJsonNode);
					reparsedNodes.add(errorNodes.get(continueIndex));
					for(Node node : reparsedNodes) {
						node.setOwner(newJsonNode);
					}
					int nodesPos = nodes.indexOf(errorNodes.get(startIndex));
					nodes.removeAll(errorNodes.subList(startIndex, continueIndex + 1));
					nodes.addAll(nodesPos, reparsedNodes);
					reparsedNodes.clear();
					continue;
				}
				
				if (stringBuilt && isNodeType(continueIndex, errorNodes, Type.CloseArray, Type.CloseObject)) {
					JsonNode newJsonNode = new JsonNode(null, reparsedNodes.get(1), JsonType.String);
					reparsedJsonNodes.add(newJsonNode);
					JsonNode endNode = new JsonNode(null, errorNodes.get(continueIndex), JsonType.End);
					reparsedJsonNodes.add(endNode);
					for(Node node : reparsedNodes) {
						node.setOwner(newJsonNode);
					}
					JsonNode parent = (objectArrayStack.size() > 0) ? objectArrayStack.remove(0) : null;
					if (parent != null) {
						errorNodes.get(continueIndex).setOwner(parent);
					}
					reparsedNodes.add(errorNodes.get(continueIndex));
					if (errorNodes.size() > continueIndex + 1 && isNodeType(continueIndex + 1, errorNodes, Type.Comma)) {
						if (parent != null) { errorNodes.get(continueIndex + 1).setOwner(parent); }
						reparsedNodes.add(errorNodes.get(continueIndex + 1));
						continueIndex++;
					}
					int nodesPos = nodes.indexOf(errorNodes.get(startIndex));
					nodes.removeAll(errorNodes.subList(startIndex, continueIndex + 1));
					nodes.addAll(nodesPos, reparsedNodes);
					reparsedNodes.clear();
					continue;
				}

				if (stringBuilt && isNodeType(continueIndex, errorNodes, Type.Colon)) {
					
					if (errorNodes.size() > continueIndex + 1 && 
							isNodeType(continueIndex + 1, errorNodes, Type.False, Type.Null, Type.Number, Type.True, Type.OpenArray, Type.OpenObject)) {
						JsonType jsonType = nodeTypeToJsonType(errorNodes.get(continueIndex + 1).getType());
						JsonNode newJsonNode = new JsonNode(reparsedNodes.get(1), errorNodes.get(continueIndex + 1) , jsonType);
						reparsedJsonNodes.add(newJsonNode);
						reparsedNodes.add(errorNodes.get(continueIndex));
						reparsedNodes.add(errorNodes.get(continueIndex + 1));
						continueIndex++;
						for(Node node : reparsedNodes) {
							node.setOwner(newJsonNode);
						}
						int nodesPos = nodes.indexOf(errorNodes.get(startIndex));
						nodes.removeAll(errorNodes.subList(startIndex, continueIndex + 1));
						nodes.addAll(nodesPos, reparsedNodes);
						reparsedNodes.clear();
						if (jsonType == JsonType.Array || jsonType == JsonType.Object) {
							objectArrayStack.add(0, newJsonNode);
						}
						continue;
					}
					
					if (continueIndex + 2 < errorNodes.size()) {
						if (isNodeType(continueIndex + 1, errorNodes, Type.Quote)) {
							if (!isNodeType(continueIndex + 2, errorNodes, Type.String)) {

								// find next quote
								for (int i = continueIndex + 2; i < errorNodes.size(); i++) {
									if (isNodeType(i, errorNodes, Type.Quote)) {
										// build string
										Node newNode = convertToString(errorNodes.get(continueIndex + 1), errorNodes.get(i));
										reparsedNodes.add(errorNodes.get(continueIndex));
										reparsedNodes.add(errorNodes.get(continueIndex + 1));
										reparsedNodes.add(newNode);
										reparsedNodes.add(errorNodes.get(i));
										continueIndex = i + 1;
										stringBuilt = true;
										break;
									}
								}
							} else if (errorNodes.size() >= continueIndex + 4){
								reparsedNodes.addAll(errorNodes.subList(continueIndex, continueIndex + 4));
								continueIndex += 4;
								stringBuilt = true;
							}
						}
					}
					
					// We've managed to rebuild the node into a string, can it be merged into the string next to it?
					else if (continueIndex + 1 == errorNodes.size()) {
						if (jsonNodes.size() > 0) {
							JsonNode headNode = jsonNodes.get(jsonNodes.size()-1);
							if (headNode.getJsonType() == JsonType.String && headNode.getKey() == null) {
								headNode.setKey(reparsedNodes.get(1));
								reparsedNodes.add(errorNodes.get(continueIndex));
								for(Node node : reparsedNodes) {
									node.setOwner(headNode);
								}
								int nodesPos = nodes.indexOf(errorNodes.get(startIndex));
								nodes.removeAll(errorNodes.subList(startIndex, continueIndex + 1));
								nodes.addAll(nodesPos, reparsedNodes);
								reparsedNodes.clear();
								break;
							}
						}
					}
					
					if (stringBuilt && continueIndex == errorNodes.size()) {
						JsonNode newJsonNode = new JsonNode(reparsedNodes.get(1), reparsedNodes.get(5), JsonType.String);
						reparsedJsonNodes.add(newJsonNode);
						for(Node node : reparsedNodes) {
							node.setOwner(newJsonNode);
						}
						int nodesPos = nodes.indexOf(errorNodes.get(startIndex));
						nodes.removeAll(errorNodes.subList(startIndex, continueIndex));
						nodes.addAll(nodesPos, reparsedNodes);
						reparsedNodes.clear();
						break;
					}


					if (stringBuilt && isNodeType(continueIndex, errorNodes, Type.Comma)) {
						JsonNode newJsonNode = new JsonNode(reparsedNodes.get(1), reparsedNodes.get(5), JsonType.String);
						reparsedJsonNodes.add(newJsonNode);
						reparsedNodes.add(errorNodes.get(continueIndex));
						for(Node node : reparsedNodes) {
							node.setOwner(newJsonNode);
						}
						int nodesPos = nodes.indexOf(errorNodes.get(startIndex));
						nodes.removeAll(errorNodes.subList(startIndex, continueIndex + 1));
						nodes.addAll(nodesPos, reparsedNodes);
						reparsedNodes.clear();
						continue;
					}
					
					if (stringBuilt && isNodeType(continueIndex, errorNodes, Type.CloseArray, Type.CloseObject)) {
						JsonNode newJsonNode = new JsonNode(reparsedNodes.get(1), reparsedNodes.get(5), JsonType.String);
						reparsedJsonNodes.add(newJsonNode);
						JsonNode endNode = new JsonNode(null, errorNodes.get(continueIndex), JsonType.End);
						reparsedJsonNodes.add(endNode);
						for(Node node : reparsedNodes) {
							node.setOwner(newJsonNode);
						}
						JsonNode parent = (objectArrayStack.size() > 0) ? objectArrayStack.remove(0) : null;
						if (parent != null) {
							errorNodes.get(continueIndex).setOwner(parent);
						}
						reparsedNodes.add(errorNodes.get(continueIndex));
						if (errorNodes.size() > continueIndex + 1 && isNodeType(continueIndex + 1, errorNodes, Type.Comma)) {
							if (parent != null) { errorNodes.get(continueIndex + 1).setOwner(parent); }
							reparsedNodes.add(errorNodes.get(continueIndex + 1));
							continueIndex++;
						}
						
						int nodesPos = nodes.indexOf(errorNodes.get(startIndex));
						nodes.removeAll(errorNodes.subList(startIndex, continueIndex + 1));
						nodes.addAll(nodesPos, reparsedNodes);
						reparsedNodes.clear();
						continue;
					}
				}
			}
			
			if (reparsedJsonNodes.size() > 0 && reparsedJsonNodes.get(reparsedJsonNodes.size() - 1).getJsonType() != JsonType.Error) {
				if (isNodeType(continueIndex, errorNodes, Type.CloseArray, Type.CloseObject)) {
					JsonNode endNode = new JsonNode(null, errorNodes.get(continueIndex), JsonType.End);
					reparsedJsonNodes.add(endNode);
					JsonNode parent = (objectArrayStack.size() > 0) ? objectArrayStack.remove(0) : null;
					if (parent != null) {
						errorNodes.get(continueIndex).setOwner(parent);
					}
					if (errorNodes.size() > continueIndex + 1 && isNodeType(continueIndex + 1, errorNodes, Type.Comma)) {
						if (parent != null) { errorNodes.get(continueIndex + 1).setOwner(parent); }
						continueIndex++;
					}
					continue;
				}
				
				if (isNodeType(continueIndex, errorNodes, Type.OpenArray, Type.OpenObject)) {
					JsonType jsonType = errorNodes.get(continueIndex).getType() == Type.OpenArray ? JsonType.Array : JsonType.Object;
					JsonNode openNode = new JsonNode(null, errorNodes.get(continueIndex), jsonType);
					reparsedJsonNodes.add(openNode);
					objectArrayStack.add(0, openNode);
					errorNodes.get(continueIndex).setOwner(openNode);
					continue;
				}
			}
			
			if (reparsedJsonNodes.size() > 0 && reparsedJsonNodes.get(reparsedJsonNodes.size() - 1).getJsonType() == JsonType.Array) {
				if (isNodeType(continueIndex, errorNodes, Type.OpenArray, Type.OpenObject)) {
					JsonType jsonType = errorNodes.get(continueIndex).getType() == Type.OpenArray ? JsonType.Array : JsonType.Object;
					JsonNode openNode = new JsonNode(null, errorNodes.get(continueIndex), jsonType);
					reparsedJsonNodes.add(openNode);
					objectArrayStack.add(0, openNode);
					errorNodes.get(continueIndex).setOwner(openNode);
					continue;
				}
			}
			
			if (reparsedJsonNodes.size() > 0 && reparsedJsonNodes.get(reparsedJsonNodes.size() - 1).getJsonType() == JsonType.Error) {
				JsonNode jsonNode = reparsedJsonNodes.get(reparsedJsonNodes.size() - 1);
				Node node = errorNodes.get(startIndex);
				jsonNode.getValue().setLength(node.getEnd() - jsonNode.getValue().getStart());
				Node mergedNode = jsonNode.getValue();
				mergedNode.setValue(mergedNode.getValue() + node.getValue());
				//nodes.remove(node);
				node.setOwner(jsonNode);
			} else {
				if (reparsedNodes.size() > 0) {
					StringBuilder strBuilder = new StringBuilder();
					for (Node node : reparsedNodes) {
						strBuilder.append(node.getValue());
					}
					Node mergedNode = new Node(Type.Error);
					mergedNode.setValue(strBuilder.toString());
					mergedNode.setStart(reparsedNodes.get(0).getStart());
					mergedNode.setLength(reparsedNodes.get(reparsedNodes.size()-1).getEnd() - mergedNode.getStart());
					JsonNode newJsonNode = new JsonNode(null, mergedNode, JsonType.Error);
					reparsedJsonNodes.add(newJsonNode);
					for(Node node : reparsedNodes) {
						node.setOwner(newJsonNode);
					}
					int nodesPos = nodes.indexOf(errorNodes.get(startIndex));
					nodes.removeAll(errorNodes.subList(startIndex, continueIndex));
					nodes.addAll(nodesPos, reparsedNodes);
					reparsedNodes.clear();
				} else {
					Node node = errorNodes.get(startIndex);
					JsonNode newJsonNode = new JsonNode(null, node, JsonType.Error);
					reparsedJsonNodes.add(newJsonNode);
					node.setOwner(newJsonNode);
				}
				
			}
		}
		
		return reparsedJsonNodes;
	}
	
	private JsonType nodeTypeToJsonType(Type type) {
		if (type == Type.Number) return JsonType.Value;
		if (type == Type.Null) return JsonType.Null;
		if (type == Type.True) return JsonType.True;
		if (type == Type.False) return JsonType.False;
		if (type == Type.OpenObject) return JsonType.Object;
		if (type == Type.OpenArray) return JsonType.Array;
		return null;
	}
	
	private int buildClose(int index, JsonType jsonType, Type nodeOpenType, Type nodeCloseType) {
		
		if (isNodeType(index, nodes, nodeCloseType)) {
			JsonNode lastNode = jsonNodes.get(jsonNodes.size()-1);
			if (lastNode.getJsonType() == JsonType.End || (lastNode.getJsonType() == jsonType && nodes.get(index -1).getType() == nodeOpenType)) {
			//if (errorNodes.size() == 0) { 
				JsonNode endNode = new JsonNode(null, nodes.get(index), JsonType.End);
				jsonNodes.add(endNode);
				JsonNode parent = (objectArrayStack.size() > 0) ? objectArrayStack.remove(0) : null;
				if (parent != null) {
					nodes.get(index).setOwner(parent);
				}
				index++;
			}
		}
		
		return index;
	}
	
	private int buildCloseWithComma(int index, JsonType jsonType, Type nodeOpenType, Type nodeCloseType) {
		
		if (isNodeType(index, nodes, nodeCloseType) && isNodeType(index+1, nodes, Type.Comma)) {
			JsonNode lastNode = jsonNodes.get(jsonNodes.size()-1);
			if (lastNode.getJsonType() == JsonType.End || (lastNode.getJsonType() == jsonType && nodes.get(index -1).getType() == nodeOpenType)) {
			//if (errorNodes.size() == 0) { 
				JsonNode endNode = new JsonNode(null, nodes.get(index), JsonType.End);
				jsonNodes.add(endNode);
				JsonNode parent = (objectArrayStack.size() > 0) ? objectArrayStack.remove(0) : null;
				if (parent != null) {
					nodes.get(index).setOwner(parent);
					nodes.get(index+1).setOwner(parent);
				}
				index+=2;
			}
		}
		
		return index;
	}
	
	private int buildOpen(int index, JsonType jsonType, Type nodeType, List<Node> errorNodes) {
		
		if (isNodeType(index, nodes, nodeType)) {
			if (errorNodes.size() == 0) {
				JsonNode jsonNode = new JsonNode(null, nodes.get(index), jsonType);
				jsonNodes.add(jsonNode);
				objectArrayStack.add(0, jsonNode);
				nodes.get(index).setOwner(jsonNode);
				index++;
				
			}
		}
		
		return index;
	}
	
	private int buildType(int index, JsonType jsonType, Type nodeType) {
		
		if (isNodeType(index, nodes, nodeType)) {
			
			// Need to check False value
			int retIndex = doCompare(index, index + 1, -1, index, jsonType, Type.CloseArray, Type.Comma);
			if (retIndex != -1) {return retIndex;}
			
			// Need to check False value
			retIndex = doCompare(index, index + 1, -1, index, jsonType, Type.CloseArray);
			if (retIndex != -1) {return retIndex;}
			
			// Need to check False value
			retIndex = doCompare(index, index + 1, -1, index, jsonType, Type.Comma);
			if (retIndex != -1) {return retIndex;}
			
		}
		
		return index;
	}
	
	private Node convertToString(Node node, Node node2) {
		
		int offset = node.getEnd();
		int length = node2.getStart() - offset;
		
		try {
			String text = fDocument.get(offset, length);
			Node newNode = new Node(Type.String);
			newNode.setPosition(offset, length);
			newNode.setValue(text);
			return newNode;
		} catch (BadLocationException e) {
			//e.printStackTrace();
		}
		
		return null;
	}
	
	private boolean isStringNode(int index){
		
		if (nodes.size() > (index + 2) && nodes.get(index).getType() == Type.Quote && nodes.get(index+1).getType() == Type.String
				&& nodes.get(index+2).getType() == Type.Quote) {
			return true;
		}
		
		return false;
	}
	
	private boolean isNodeType(int index, List<Node> nodeList, Type ... types) {
		
		for (Type type : types) {
			if (nodeList.size() > index && nodeList.get(index).getType() == type) {
				return true;
			}
		}
		
		return false;
	}
	
	private int doCompare(int start, int index, int keyNodePos, int valueNodePos, JsonType jsonType, Type ... types ) {
		
		int count = index;
		for (Type type : types) {
			if (count >= nodes.size() || nodes.get(count).getType() != type) {
				return -1;
			}
			count++;
		}
		
		Node keyNode = (keyNodePos != -1) ? nodes.get(keyNodePos) : null;
		Node valueNode = (valueNodePos != -1) ? nodes.get(valueNodePos) : null;
		JsonNode node = new JsonNode(keyNode, valueNode, jsonType);
		jsonNodes.add(node);
		
		for (int i = start; i < types.length + index; i++) {
			if (nodes.get(i).getType() == Type.CloseArray || nodes.get(i).getType() == Type.CloseObject) {
				if (!objectArrayStack.isEmpty()) {
					JsonNode endNode = new JsonNode(null, nodes.get(i), JsonType.End);
					jsonNodes.add(endNode);
					
					JsonNode parent = (objectArrayStack.size() > 0) ? objectArrayStack.remove(0) : null;
					if (parent != null) {
						nodes.get(i).setOwner(parent);
						node = parent;
					}
				}
			} else {
				nodes.get(i).setOwner(node);
			}
		}
		
		if (jsonType == JsonType.Array || jsonType == JsonType.Object) {
			objectArrayStack.add(0, node);
		}
		
		return count;
	}
}
