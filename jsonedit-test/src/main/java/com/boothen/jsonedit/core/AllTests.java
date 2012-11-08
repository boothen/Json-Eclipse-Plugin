package com.boothen.jsonedit.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.boothen.jsonedit.core.model.jsonnode.JsonNodeBuilderTest;
import com.boothen.jsonedit.core.model.node.NodeBuilderTest;
import com.boothen.jsonedit.core.validation.JsonTextValidationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
NodeBuilderTest.class,
JsonNodeBuilderTest.class,
JsonTextValidationTest.class})
public class AllTests {


}
