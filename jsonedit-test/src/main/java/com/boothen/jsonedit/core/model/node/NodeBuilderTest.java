package com.boothen.jsonedit.core.model.node;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.junit.Ignore;
import org.junit.Test;

import com.boothen.jsonedit.core.util.FileToDocUtility;

@Ignore
public class NodeBuilderTest {

	public static int[] failingTests = new int[] {5, 6, 7, 39};

	@Test
	public void testFile1() {
		test("test1.json", 75);
	}

	@Test
	public void testFile2() {
		test("test2.json", 315);
	}

	@Test
	public void testFile3() {
		test("test3.json", 386);
	}

	@Test
	public void testFile4() {
		test("test4.json", 97);
	}

	@Test
	public void testFile5() {
		test("test5.json", 4);
	}

	@Test
	public void testFile6() {
		test("test6.json", 7);
	}

	@Test
	public void testFile7() {
		test("test7.json", 11);
	}

	@Test
	public void testFile8() {
		test("test8.json", 7);
	}

	@Test
	public void testFile9() {
		test("test9.json", 21);
	}

	@Test
	public void testFile12() {
		test("test12.json", 27);
	}

	@Test
	public void testFile13() {
		test("test13.json", 19);
	}

	@Test
	public void testFile14() {
		test("test14.json", 17);
	}

	@Test
	public void testFile15() {
		test("test15.json", 17);
	}

	@Test
	public void testFile16() {
		test("test16.json", 9);
	}

	@Test
	public void testFile17() {
		test("test17.json", 10);
	}

	@Test
	public void testFile18() {
		test("test18.json", 11);
	}

	@Test
	public void testFile19() {
		test("test19.json", 8);
	}

	@Test
	public void testFile20() {
		test("test20.json", 12);
	}

	@Test
	public void testFile21() {
		test("test21.json", 16);
	}

	@Test
	public void testFile22() {
		test("test22.json", 19);
	}

	@Test
	public void testFile23() {
		test("test23.json", 27);
	}

	@Test
	public void testFile24() {
		test("test24.json", 25);
	}

	@Test
	public void testFile25() {
		test("test25.json", 9);
	}

	@Test
	public void testFile26() {
		test("test26.json", 31);
	}

	@Test
	public void testFile27() {
		test("test27.json", 17);
	}

	@Test
	public void testFile28() {
		test("test28.json", 29);
	}

	@Test
	public void testFile29() {
		test("test29.json", 17);
	}

	@Test
	public void testFile30() {
		test("test30.json", 7);
	}

	@Test
	public void testFile31() {
		test("test31.json", 27);
	}

	@Test
	public void testFile32() {
		test("test32.json", 27);
	}

	@Test
	public void testFile33() {
		test("test33.json", 9);
	}

	@Test
	public void testFile34() {
		test("test34.json", 9);
	}

	@Test
	public void testFile35() {
		test("test35.json", 9);
	}

	@Test
	public void testFile36() {
		test("test36.json", 2);
	}

	@Test
	public void testFile37() {
		test("test37.json", 16);
	}

	@Test
	public void testFile38() {
		test("test38.json", 24);
	}

	@Test
	@Ignore
	public void testFile39() {
		test("test39.json", 5);
	}

	@Test
	public void testFile40() {
		test("test40.json", 19);
	}

	@Test
	public void testFile41() {
		test("test41.json", 9);
	}

	@Test
	public void testFile42() {
		test("test42.json", 5);
	}

	private void test(String file, int size) {
		IDocument fDocument = FileToDocUtility.getDocument(file);

		List<Node> nodes = new NodeBuilder(fDocument).buildNodes();
		for (Node node : nodes) {
			System.out.println(node);
		}

		assertEquals(size, nodes.size());
	}
}
