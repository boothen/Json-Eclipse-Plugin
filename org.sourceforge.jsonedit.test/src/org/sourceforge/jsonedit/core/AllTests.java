package org.sourceforge.jsonedit.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.sourceforge.jsonedit.core.outline.JsonTextOutlineParserTest;
import org.sourceforge.jsonedit.core.validation.JsonTextValidationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
JsonTextOutlineParserTest.class,
org.sourceforge.jsonedit.core.text.JsonReconcilingStrategyTest.class,
org.sourceforge.jsonedit.core.model.JsonReconcilingStrategyTest.class,
JsonTextValidationTest.class})
public class AllTests {


}
