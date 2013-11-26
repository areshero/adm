package com.adm.common;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(TestFileStatus.class);
		suite.addTestSuite(TestInputStream.class);
		//$JUnit-END$
		return suite;
	}

}
