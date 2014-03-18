package com.adm.common.search;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;

public class AnalyzerFactory {

	public static WhitespaceAnalyzer createWhitespaceAnalyzer(){
		return new WhitespaceAnalyzer(LuceneConfConstant.matchVersion);
	}
}
