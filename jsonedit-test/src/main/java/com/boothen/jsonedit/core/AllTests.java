package com.boothen.jsonedit.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.boothen.jsonedit.core.outline.JsonTextOutlineParserTest;
import com.boothen.jsonedit.core.validation.JsonTextValidationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
JsonTextOutlineParserTest.class,
com.boothen.jsonedit.core.text.JsonReconcilingStrategyTest.class,
com.boothen.jsonedit.core.model.JsonReconcilingStrategyTest.class,
JsonTextValidationTest.class})
public class AllTests {


}
