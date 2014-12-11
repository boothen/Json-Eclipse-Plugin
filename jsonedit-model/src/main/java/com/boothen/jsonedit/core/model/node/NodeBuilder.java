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
package com.boothen.jsonedit.core.model.node;

import static com.boothen.jsonedit.core.util.JsonCharUtility.eof;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

import com.boothen.jsonedit.core.util.JsonCharUtility;
import com.boothen.jsonedit.core.util.reader.JsonDocReader;
import com.boothen.jsonedit.core.util.reader.JsonReader;
import com.boothen.jsonedit.core.util.reader.JsonReaderException;

public class NodeBuilder {

	private IDocument fDocument;

	private JsonReader parser;

	private List<Node> nodes;

	public NodeBuilder(IDocument document) {
		super();
		fDocument = document;
	}

	public List<Node> buildNodes(){

		nodes = new LinkedList<Node>();
		parser = new JsonDocReader(fDocument);

		try {
			char ch;
			do {
				ch = parser.getNextClean();
				Node node = buildNode(ch);
				if (node != null) {
					nodes.add(node);
				}
			}
			while (ch != eof);
		} catch (JsonReaderException e) {
			return null;
		}

		parseNodes(nodes);

		return nodes;
	}

	private Node buildNode(char ch) {

		if (JsonCharUtility.isJsonChar(ch, parser.getPrevious())) {
			Node node = new Node(getJsonCharType(ch));
			node.setPosition(parser.getPosition(), 1);
			node.setValue(String.valueOf(ch));
			return node;
		}

		return null;
	}

	private void parseNodes(List<Node> nodes) {

		int i = 0;
		while(i < nodes.size() - 1) {
			Node node = nodes.get(i);
			Node node2 = nodes.get(i+1);

			compareNodes(node, node2, i);
			i++;
		}
	}

	private void compareNodes(Node node, Node node2, int index) {

		if (node.getType() == Type.OpenObject) {
			compareOpenObject(node, node2, index);
		}

		if (node.getType() == Type.Quote) {
			compareQuote(node, node2, index);
		}

		if (node.getType() == Type.OpenArray) {
			compareOpenArray(node, node2, index);
		}

		if (node.getType() == Type.Colon) {
			compareColon(node, node2, index);
		}

		if (node.getType() == Type.Comma) {
			compareComma(node, node2, index);
		}

		if (node.getType() == Type.CloseArray) {
			compareCloseArray(node, node2, index);
		}

		if (node.getType() == Type.CloseObject) {
			compareCloseObject(node, node2, index);
		}
	}

	private void compareCloseObject(Node node, Node node2, int index) {

		if (node2.getType() == Type.Comma || node2.getType() == Type.CloseArray || node2.getType() == Type.CloseObject) {
			checkForText(node, node2, index);
		} else {
			doError(node, node2, index);
		}
	}

	private void compareCloseArray(Node node, Node node2, int index) {

		if (node2.getType() == Type.Comma || node2.getType() == Type.CloseArray
				|| node2.getType() == Type.CloseObject) {
			checkForText(node, node2, index);
		} else {
			doError(node, node2, index);
		}

	}

	private void compareComma(Node node, Node node2, int index) {

		if (node2.getType() == Type.Quote || node2.getType() == Type.OpenArray
				|| node2.getType() == Type.OpenObject) {
			checkForText(node, node2, index);
		} else if (node2.getType() == Type.Comma || node2.getType() == Type.CloseArray) {
			buildValue(node, node2, index);
		} else {
			doError(node, node2, index);
		}
	}

	private void compareColon(Node node, Node node2, int index) {

		if (node2.getType() == Type.Comma || node2.getType() == Type.CloseObject) {
			buildValue(node, node2, index);
		} else if (node2.getType() == Type.Quote) {
			checkForText(node, node2, index);
		} else if (node2.getType() == Type.OpenArray
				|| node2.getType() == Type.OpenObject) {
			return;
		} else {
			doError(node, node2, index);
		}
	}

	private void compareOpenArray(Node node, Node node2, int index) {

		if (node2.getType() == Type.CloseArray || node2.getType() == Type.Comma) {
			buildValue(node, node2, index);
		} else if (node2.getType() == Type.Quote || node2.getType() == Type.OpenArray
				|| node2.getType() == Type.OpenObject ) {
			checkForText(node, node2, index);
		} else {
			doError(node, node2, index);
		}

	}

	private void compareOpenObject(Node node, Node node2, int index) {

		if (node2.getType() == Type.Quote || node2.getType() == Type.CloseObject) {
			checkForText(node, node2, index);
		} else {
			doError(node, node2, index);
		}
	}

	private void compareQuote(Node node, Node node2, int index) {

		if (node2.getType() == Type.Quote) {
			buildString(node, node2, index);
		} else if (node2.getType() == Type.Colon || node2.getType() == Type.Comma
				|| node2.getType() == Type.CloseArray || node2.getType() == Type.CloseObject) {
			checkForText(node, node2, index);
		} else {
			doError(node, node2, index);
		}

	}

	private void checkForText(Node node, Node node2, int index) {

		int offset = node.getEnd();
		int length = node2.getStart() - offset;

		try {
			String text = fDocument.get(offset, length);
			if (!text.trim().isEmpty()) {
				addErrorNode(text, offset, length, index);
			}
		} catch (BadLocationException e) {

		}
	}

	private void buildString(Node node, Node node2, int index) {

		int offset = node.getEnd();
		int length = node2.getStart() - offset;

		try {
			String text = fDocument.get(offset, length);
			if (text != null && !text.trim().isEmpty()) {
				addNode(text, offset, length, index, Type.String);
			}
		} catch (BadLocationException e) {

		}

	}

	private void buildValue(Node node, Node node2, int index) {

		int offset = node.getEnd();
		int length = node2.getStart() - offset;

		try {
			String text = fDocument.get(offset, length);
			String trimmedText = text.trim();

			if ("null".equals(trimmedText)) {
				addNode(trimmedText, offset, length, index, Type.Null);
			} else if ("true".equals(trimmedText)) {
				addNode(trimmedText, offset, length, index, Type.True);
			} else if ("false".equals(trimmedText)) {
				addNode(trimmedText, offset, length, index, Type.False);
			} else if (isANumber(trimmedText)) {
				addNode(trimmedText, offset, length, index, Type.Number);
			} else {
				checkForText(node, node2, index);
			}

		} catch (BadLocationException e) {
			return;
		}
	}

	private boolean isANumber(String text) {

		try {
			Float.parseFloat(text);
			return true;
		} catch (Exception e) {

		}

		return false;
	}

	private void addNode(String text, int offset, int length, int index, Type type) {
		Node node = new Node(type);
		node.setPosition(offset, length);
		node.setValue(text);
		nodes.add(index + 1, node);
	}

	private void doError(Node node, Node node2, int index) {

		int offset = node.getEnd();
		int length = node2.getStart() - offset;

		addErrorNode("Error", offset, length, index);
	}

	private void addErrorNode(String text, int offset, int length, int index) {

		Node errorNode = new Node(Type.Error);
		errorNode.setPosition(offset, length);
		errorNode.setValue(text.trim());
		nodes.add(index + 1, errorNode);
	//	errorNodeIndex.add(index + 1);
	}

	public static Type getJsonCharType(char ch) {

		if (ch == JsonCharUtility.comma) {

			return Type.Comma;
		}

		if (ch == JsonCharUtility.openCurly) {
			return Type.OpenObject;
		}

		if (ch == JsonCharUtility.closeCurly) {
			return Type.CloseObject;
		}

		if (ch == JsonCharUtility.openSquare) {
			return Type.OpenArray;
		}

		if (ch == JsonCharUtility.closeSquare) {
			return Type.CloseArray;
		}


		if (ch == JsonCharUtility.colon) {
			return Type.Colon;
		}

		if (ch == JsonCharUtility.quote) {
			return Type.Quote;
		}

		return null;
	}
}
