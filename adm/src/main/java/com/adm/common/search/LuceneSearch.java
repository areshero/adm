package com.adm.common.search;
import java.io.File;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
public class LuceneSearch {

		private IndexSearcher searcher = null;
		private Query query = null;
		
		// tell the search to search to contents of the document
		private String field = "contents";

		public LuceneSearch() {
			try {
				IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(LuceneConfConstant.INDEX_STORE_PATH)));
				searcher = new IndexSearcher(reader);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	    //get the search result
		public final TopDocs search(String keyword) {
			System.out.println("Looking up the keyword : " + keyword);
			TopDocs results = null;
			int totalhits = 100;
			
			try {
				//match version : 4.7
				Analyzer analyzer = AnalyzerFactory.createWhitespaceAnalyzer();
				QueryParser parser = new QueryParser(LuceneConfConstant.matchVersion, field,analyzer);
				
				query = parser.parse(keyword);
				
				Date start = new Date();
				
				
				results = searcher.search(query,totalhits);
				Date end = new Date();
				System.out.println("Search complete!! Used time: " + (end.getTime() - start.getTime()) + "millis");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return results;
		}

		
		public void printResult(TopDocs results) {
			ScoreDoc[] hits = results.scoreDocs;
			if (hits.length == 0) {
				System.out.println("Sorry, can't find a mathc!");
			} else {
				for (int i = 0; i < hits.length; i++) {
					try {
						Document doc = searcher.doc(hits[i].doc);
						System.out.print("The " + i + " search result，filename：");
						System.out.println(doc.get("path"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("##############################");
		}

		public static void main(String[] args) throws Exception {
			LuceneSearch test = new LuceneSearch();
			TopDocs hits = test.search("this");
			test.printResult(hits);
		}
}
