package com.adm.common.search;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class AnalyzerFactory {

	public static WhitespaceAnalyzer createWhitespaceAnalyzer(){
		return new WhitespaceAnalyzer(LuceneConfConstant.matchVersion);
	}
	
	public static StandardAnalyzer createStandardAnalyzer(){
		return new StandardAnalyzer(LuceneConfConstant.matchVersion);
	}
	
}
