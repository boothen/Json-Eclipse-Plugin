package com.boothen.jsonedit.core.model;




import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.boothen.jsonedit.core.model.jsonnode.JsonNode;
import com.boothen.jsonedit.core.model.jsonnode.JsonNodeBuilder;
import com.boothen.jsonedit.core.model.node.Node;
import com.boothen.jsonedit.core.model.node.NodeBuilder;
import com.boothen.jsonedit.core.util.FileToDocUtility;

public class JsonReconcilingStrategyTest {

	public static int[] failingTests = new int[] {5,6,7, 37, 38, 39};

	@Before
	public void onSetup() {

	}


	public void tearDown() {

	}

	@Test
	public void testFile1() {

		IDocument fDocument = FileToDocUtility.getDocument("test1.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();

		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(75, nodes.size());

		Assert.assertEquals(15, jsonNodes.size());
	}

	@Test
	public void testFile2() {

		IDocument fDocument = FileToDocUtility.getDocument("test2.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();

		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(315, nodes.size());

		Assert.assertEquals(48, jsonNodes.size());
	}

	@Test
	public void testFile3() {

		IDocument fDocument = FileToDocUtility.getDocument("test3.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(386, nodes.size());

		Assert.assertEquals(63, jsonNodes.size());
	}

	@Test
	public void testFile4() {

		IDocument fDocument = FileToDocUtility.getDocument("test4.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(97, nodes.size());

		Assert.assertEquals(30, jsonNodes.size());
	}

	@Test
	@Ignore
	public void testFile5() {

		IDocument fDocument = FileToDocUtility.getDocument("test5.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(4, nodes.size());

		Assert.assertEquals(3, jsonNodes.size());
	}

	@Test
	@Ignore
	public void testFile6() {

		IDocument fDocument = FileToDocUtility.getDocument("test6.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();


		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(7, nodes.size());
		Assert.assertEquals(3, jsonNodes.size());
	}

	@Test
	@Ignore
	public void testFile7() {

		IDocument fDocument = FileToDocUtility.getDocument("test7.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(10, nodes.size());
		Assert.assertEquals(4, jsonNodes.size());
	}

	@Test
	public void testFile8() {

		IDocument fDocument = FileToDocUtility.getDocument("test8.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(7, nodes.size());
		Assert.assertEquals(3, jsonNodes.size());
	}

	@Test
	public void testFile9() {

		IDocument fDocument = FileToDocUtility.getDocument("test9.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(21, nodes.size());
		Assert.assertEquals(8, jsonNodes.size());
	}

	@Test
	public void testFile12() {

		IDocument fDocument = FileToDocUtility.getDocument("test12.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(27, nodes.size());
		Assert.assertEquals(8, jsonNodes.size());
	}

	@Test
	public void testFile13() {

		IDocument fDocument = FileToDocUtility.getDocument("test13.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(9, nodes.size());
		Assert.assertEquals(3, jsonNodes.size());
	}

	@Test
	public void testFile14() {

		IDocument fDocument = FileToDocUtility.getDocument("test14.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();


		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(17, nodes.size());
		Assert.assertEquals(4, jsonNodes.size());
	}

	@Test
	public void testFile15() {

		IDocument fDocument = FileToDocUtility.getDocument("test15.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();


		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(17, nodes.size());
		Assert.assertEquals(4, jsonNodes.size());
	}

	@Test
	public void testFile16() {

		IDocument fDocument = FileToDocUtility.getDocument("test16.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();


		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(9, nodes.size());
		Assert.assertEquals(3, jsonNodes.size());
	}

	@Test
	public void testFile17() {

		IDocument fDocument = FileToDocUtility.getDocument("test17.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(10, nodes.size());
		Assert.assertEquals(4, jsonNodes.size());
	}

	@Test
	public void testFile18() {

		IDocument fDocument = FileToDocUtility.getDocument("test18.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();


		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(11, nodes.size());
		Assert.assertEquals(4, jsonNodes.size());
	}

	@Test
	public void testFile19() {

		IDocument fDocument = FileToDocUtility.getDocument("test19.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		System.out.println("");
		for (Node node : nodes) {
			System.out.println(node);
		}
		System.out.println("");
		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(8, nodes.size());
		Assert.assertEquals(4, jsonNodes.size());
	}

	@Test
	public void testFile20() {

		IDocument fDocument = FileToDocUtility.getDocument("test20.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();


		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(12, nodes.size());
		Assert.assertEquals(4, jsonNodes.size());
	}

	@Test
	public void testFile21() {

		IDocument fDocument = FileToDocUtility.getDocument("test21.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();


		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(16, nodes.size());
		Assert.assertEquals(5, jsonNodes.size());
	}

	@Test
	public void testFile22() {

		IDocument fDocument = FileToDocUtility.getDocument("test22.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();


		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(19, nodes.size());
		Assert.assertEquals(6, jsonNodes.size());
	}

		@Test
	public void testFile23() {

		IDocument fDocument = FileToDocUtility.getDocument("test23.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(27, nodes.size());
		Assert.assertEquals(7, jsonNodes.size());

    }

	@Test
	public void testFile24() {

		IDocument fDocument = FileToDocUtility.getDocument("test24.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();


		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(25, nodes.size());
		Assert.assertEquals(5, jsonNodes.size());
	}

	@Test
	public void testFile25() {

		IDocument fDocument = FileToDocUtility.getDocument("test25.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();


		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(9, nodes.size());
		Assert.assertEquals(3, jsonNodes.size());
	}

	@Test
	public void testFile26() {

		IDocument fDocument = FileToDocUtility.getDocument("test26.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(31, nodes.size());
		Assert.assertEquals(14, jsonNodes.size());
	}

	@Test
	public void testFile27() {

		IDocument fDocument = FileToDocUtility.getDocument("test27.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(17, nodes.size());
		Assert.assertEquals(7, jsonNodes.size());
	}

	@Test
	public void testFile28() {

		IDocument fDocument = FileToDocUtility.getDocument("test28.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(29, nodes.size());
		Assert.assertEquals(8, jsonNodes.size());
	}

	@Test
	public void testFile29() {

		IDocument fDocument = FileToDocUtility.getDocument("test29.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();


		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(17, nodes.size());
		Assert.assertEquals(7, jsonNodes.size());
	}

	@Test
	public void testFile30() {

		IDocument fDocument = FileToDocUtility.getDocument("test30.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(7, nodes.size());
		Assert.assertEquals(3, jsonNodes.size());
	}

	@Test
	public void testFile31() {

		IDocument fDocument = FileToDocUtility.getDocument("test31.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(27, nodes.size());
		Assert.assertEquals(8, jsonNodes.size());
	}

	@Test
	public void testFile32() {

		IDocument fDocument = FileToDocUtility.getDocument("test32.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();


		for (Node node : nodes) {
			System.out.println(node);
		}

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(27, nodes.size());
		Assert.assertEquals(8, jsonNodes.size());
	}

	@Test
	public void testFile33() {

		IDocument fDocument = FileToDocUtility.getDocument("test33.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();


		System.out.println("");
		for (Node node : nodes) {
			System.out.println(node);
		}

		System.out.println("");
		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(9, nodes.size());
		Assert.assertEquals(3, jsonNodes.size());
	}

	@Test
	public void testFile34() {

		IDocument fDocument = FileToDocUtility.getDocument("test34.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();


		System.out.println("");
		for (Node node : nodes) {
			System.out.println(node);
		}

		System.out.println("");
		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(9, nodes.size());
		Assert.assertEquals(3, jsonNodes.size());
	}

	@Test
	public void testFile35() {

		IDocument fDocument = FileToDocUtility.getDocument("test35.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();


		System.out.println("");
		for (Node node : nodes) {
			System.out.println(node);
		}

		System.out.println("");
		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(9, nodes.size());
		Assert.assertEquals(4, jsonNodes.size());
	}

	@Test
	public void testFile36() {

		IDocument fDocument = FileToDocUtility.getDocument("test36.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		System.out.println("");
		for (Node node : nodes) {
			System.out.println(node);
		}

		System.out.println("");
		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(2, nodes.size());
		Assert.assertEquals(2, jsonNodes.size());
	}

	@Test
	@Ignore
	public void testFile37() {

		IDocument fDocument = FileToDocUtility.getDocument("test37.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();


		System.out.println("");
		for (Node node : nodes) {
			System.out.println(node);
		}

		System.out.println("");
		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(2, nodes.size());
		Assert.assertEquals(2, jsonNodes.size());
	}

	@Test
	@Ignore
	public void testFile38() {

		IDocument fDocument = FileToDocUtility.getDocument("test38.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		System.out.println("");
		for (Node node : nodes) {
			System.out.println(node);
		}

		System.out.println("");
		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(2, nodes.size());
		Assert.assertEquals(2, jsonNodes.size());
	}

	@Test
	@Ignore
	public void testFile39() {

		IDocument fDocument = FileToDocUtility.getDocument("test39.json");

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		System.out.println("");
		for (Node node : nodes) {
			System.out.println(node);
		}

		System.out.println("");
		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(5, nodes.size());
		Assert.assertEquals(3, jsonNodes.size());
	}
}
