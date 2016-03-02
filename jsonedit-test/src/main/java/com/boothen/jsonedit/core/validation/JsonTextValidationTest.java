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
package com.boothen.jsonedit.core.validation;

import java.io.FileNotFoundException;

import org.junit.Test;

/**
 * @author Matt Garner
 *
 */
public class JsonTextValidationTest {

    public void testPassing(String fileName) throws FileNotFoundException {
//        File file = FileToDocUtility.getFile(fileName);
//        JsonTextValidator jtop = new JsonTextValidator(file) {
//
//            @Override
//            public void reportProblem(String msg, Location loc, int violation, boolean isError) {
//                fail();
//            }
//        };
//        jtop.parse();
    }

    public void testFail(String fileName, final int location) throws FileNotFoundException {

//        File file = FileToDocUtility.getFile(fileName);
//        JsonTextValidator jtop = new JsonTextValidator(file) {
//
//            @Override
//            public void reportProblem(String msg, Location loc, int violation, boolean isError) {
//                assertEquals(location, loc.charStart);
//                throw new RuntimeException("Expected");
//            }
//        };
//
//        try {
//            jtop.parse();
//            fail();
//        } catch (RuntimeException e) {
//            Assert.assertEquals("Expected", e.getMessage());
//        }
    }

    @Test
    public void testFile1() throws Exception {
        testPassing("test1.json");
    }

    @Test
    public void testFile2() throws Exception {
        testPassing("test2.json");
    }

    @Test
    public void testFile3() throws Exception {
        testPassing("test3.json");
    }

    @Test
    public void testFile4() throws Exception {
        testPassing("test4.json");
    }

    @Test
    public void testFile5() throws Exception {
        testFail("test5.json", 16);
    }

    @Test
    public void testFile6() throws Exception {
        testFail("test6.json", 19);
    }

    @Test
    public void testFile7() throws Exception {
        testFail("test7.json", 20);
    }

    @Test
    public void testFile8() throws Exception {
        testFail("test8.json", 18);
    }

    @Test
    public void testFile9() throws Exception {
        testPassing("test9.json");
    }

    @Test
    public void testFile12() throws Exception {
        testFail("test12.json", 5);
    }

    @Test
    public void testFile13() throws Exception {
        testPassing("test13.json");
    }

    @Test
    public void testFile14() throws Exception {
        testPassing("test14.json");
    }

    @Test
    public void testFile15() throws Exception {
        testPassing("test15.json");
    }

    @Test
    public void testFile16() throws Exception {
        testPassing("test16.json");
    }

    @Test
    public void testFile17() throws Exception {
        testFail("test17.json", 14);
    }

    @Test
    public void testFile18() throws Exception {
        testFail("test18.json", 17);
    }

    @Test
    public void testFile19() throws Exception {
        testPassing("test19.json");
    }

    @Test
    public void testFile20() throws Exception {
        testFail("test20.json", 17);
    }

    @Test
    public void testFile21() throws Exception {
        testPassing("test21.json");
    }

    @Test
    public void testFile22() throws Exception {
        testFail("test22.json", 25);
    }

    @Test
    public void testFile23() throws Exception {
        testFail("test23.json", 12);
    }

    @Test
    public void testFile24() throws Exception {
        testPassing("test24.json");
    }

    @Test
    public void testFile25() throws Exception {
        testPassing("test25.json");
    }

    @Test
    public void testFile26() throws Exception {
        testPassing("test26.json");
    }

    @Test
    public void testFile27() throws Exception {
        testPassing("test27.json");
    }

    @Test
    public void testFile28() throws Exception {
        testPassing("test28.json");
    }

    @Test
    public void testFile29() throws Exception {
        testPassing("test29.json");
    }

    @Test
    public void testFile30() throws Exception {
        testPassing("test30.json");
    }

    @Test
    public void testFile31() throws Exception {
        testPassing("test31.json");
    }

    @Test
    public void testFile32() throws Exception {
        testPassing("test32.json");
    }

    @Test
    public void testFile33() throws Exception {
        testPassing("test33.json");
    }

    @Test
    public void testFile34() throws Exception {
        testPassing("test34.json");
    }

    @Test
    public void testFile35() throws Exception {
        testPassing("test35.json");
    }

    @Test
    public void testFile36() throws Exception {
        testPassing("test36.json");
    }

    @Test
    public void testFile37() throws Exception {
        testFail("test37.json", 50);
    }

    @Test
    public void testFile38() throws Exception {
        testFail("test38.json", 103);
    }

    @Test
    public void testFile39() throws Exception {
        testPassing("test39.json");
    }

    @Test
    public void testFile40() throws Exception {
        testFail("test40.json", 20);
    }

    @Test
    public void testFile41() throws Exception {
        testFail("test41.json", 15);
    }

    @Test
    public void testFile42() throws Exception {
        testFail("test42.json", 4);
    }

    @Test
    public void testFile43() throws Exception {
        testFail("test43.json", 21);
    }

    @Test
    public void testFile44() throws Exception {
        testFail("test44.json", 18);
    }

    @Test
    public void testFile45() throws Exception {
        testFail("test45.json", 30);
    }

    @Test
    public void testFile46() throws Exception {
        testFail("test46.json", 58);
    }

    @Test
    public void testFile47() throws Exception {
        testFail("test47.json", 21);
    }

    @Test
    public void testFile48() throws Exception {
        testFail("test48.json", 72);
    }

    @Test
    public void testFile49() throws Exception {
        testPassing("test49.json");
    }
}
