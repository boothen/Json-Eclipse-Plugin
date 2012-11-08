package com.boothen.jsonedit.core.model.jsonnode;

import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.boothen.jsonedit.core.model.node.Node;
import com.boothen.jsonedit.core.model.node.NodeBuilder;
import com.boothen.jsonedit.core.util.FileToDocUtility;

public class JsonNodeBuilderTest {


	public static int[] failingTests = new int[] {5,6,7, 37, 38, 39, 40, 41, 42};

	@Test
	public void testFile1() {
		test("test1.json", 15);
	}

	private void test(String file, int size) {
		IDocument fDocument = FileToDocUtility.getDocument(file);

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();

		List<JsonNode> jsonNodes = new JsonNodeBuilder(fDocument, nodes).buildJsonNodes();

		for (JsonNode node : jsonNodes) {
			System.out.println(node);
		}

		Assert.assertEquals(size, jsonNodes.size());
	}

	@Test
	public void testFile2() {
		test("test2.json", 48);
	}

	@Test
	public void testFile3() {
		test("test3.json", 63);
	}

	@Test
	public void testFile4() {
		test("test4.json", 30);
	}

	@Test
	@Ignore
	public void testFile5() {
		test("test5.json", 3);
	}

	@Test
	@Ignore
	public void testFile6() {
		test("test6.json", 3);
	}

	@Test
	@Ignore
	public void testFile7() {
		test("test7.json", 4);
	}

	@Test
	public void testFile8() {
		test("test8.json", 3);
	}

	@Test
	public void testFile9() {
		test("test9.json", 8);
	}

	@Test
	public void testFile12() {
		test("test12.json", 8);
	}

	@Test
	public void testFile13() {
		test("test13.json", 3);
	}

	@Test
	public void testFile14() {
		test("test14.json", 4);
	}

	@Test
	public void testFile15() {
		test("test15.json", 4);
	}

	@Test
	public void testFile16() {
		test("test16.json", 3);
	}

	@Test
	public void testFile17() {
		test("test17.json", 4);
	}

	@Test
	public void testFile18() {
		test("test18.json", 4);
	}

	@Test
	public void testFile19() {
		test("test19.json", 4);
	}

	@Test
	public void testFile20() {
		test("test20.json", 4);
	}

	@Test
	public void testFile21() {
		test("test21.json", 5);
	}

	@Test
	public void testFile22() {
		test("test22.json", 6);
	}

	@Test
	public void testFile23() {
		test("test23.json", 7);
	}

	@Test
	public void testFile24() {
		test("test24.json", 5);
	}

	@Test
	public void testFile25() {
		test("test25.json", 3);
	}

	@Test
	public void testFile26() {
		test("test26.json", 14);
	}

	@Test
	public void testFile27() {
		test("test27.json", 7);
	}

	@Test
	public void testFile28() {
		test("test28.json", 8);
	}

	@Test
	public void testFile29() {
		test("test29.json", 7);
	}

	@Test
	public void testFile30() {
		test("test30.json", 3);
	}

	@Test
	public void testFile31() {
		test("test31.json", 8);
	}

	@Test
	public void testFile32() {
		test("test32.json", 8);
	}

	@Test
	public void testFile33() {
		test("test33.json", 3);
	}

	@Test
	public void testFile34() {
		test("test34.json", 3);
	}

	@Test
	public void testFile35() {
		test("test35.json", 4);
	}

	@Test
	public void testFile36() {
		test("test36.json", 2);
	}

	@Test
	@Ignore
	public void testFile37() {
		test("test37.json", 8);
	}

	@Test
	@Ignore
	public void testFile38() {
		test("test38.json", 8);
	}

	@Test
	@Ignore
	public void testFile39() {
		test("test39.json", 3);
	}

	@Test
	@Ignore
	public void testFile40() {
		test("test40.json", 8);
	}

	@Test
	@Ignore
	public void testFile41() {
		test("test41.json", 6);
	}

	@Test
	@Ignore
	public void testFile42() {
		test("test42.json", 4);
	}
}
