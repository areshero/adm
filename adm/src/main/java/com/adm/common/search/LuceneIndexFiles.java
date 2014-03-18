package com.adm.common.search;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * @author areshero
 * @version Lucene 4.7
 * */
public class LuceneIndexFiles {

	private IndexWriter writer = null;
	private boolean create = true;
	
	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public LuceneIndexFiles() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() throws IOException {

		//dir where the index files are saved 
		Directory indexDir = FSDirectory.open(new File(LuceneConfConstant.INDEX_STORE_PATH));
		
		//Substitute desired Lucene version for XY
		//Version matchVersion = Version.LUCENE_47; 
		//match version : 4.7
		
		//Analyzer analyzer = new WhitespaceAnalyzer(LuceneConfConstant.matchVersion);
		Analyzer analyzer = AnalyzerFactory.createWhitespaceAnalyzer();
		
		IndexWriterConfig iwc = new IndexWriterConfig(LuceneConfConstant.matchVersion,analyzer);
		
		if(isCreate()){
			iwc.setOpenMode(OpenMode.CREATE);
		}else {
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		}
		
		writer = new IndexWriter(indexDir, iwc);
	}

	private Document file2Document(File file) throws Exception {

		Document doc = new Document();
		FileInputStream is = new FileInputStream(file);
		Reader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		Field pathField = new StringField("path", file.getAbsolutePath() ,Field.Store.YES);
		Field contenField = new TextField("contents", reader);
		LongField modifiedField = new LongField("modified", file.lastModified(), Field.Store.NO);
		
		doc.add(contenField);
		doc.add(modifiedField);
		doc.add(pathField);

		return doc;
	}

	public boolean writeToIndex() throws Exception {
		
		File dir = new File(LuceneConfConstant.INDEX_FILE_PATH);
		
		if(dir.canRead()){
			if (dir.isDirectory()) {
				System.out.println("Build the index right now!");
				
				for (File file : dir.listFiles()) {
					writeFileToIndex(file);
				}
				// NOTE: if you want to maximize search performance,
				// you can optionally call forceMerge here.  This can be
				// a terribly costly operation, so generally it's only
				// worth it when your index is relatively static (ie
				// you're done adding documents to it):
				//
				// writer.forceMerge(1);
				
				return true;
			} else{
				writeFileToIndex(dir);
				return true;
			}
		}
		return false;
		
	}

	private void writeFileToIndex(File file) throws Exception, IOException {
		Document doc = file2Document(file);
		
		if(writer.getConfig().getOpenMode() == OpenMode.CREATE){
			System.out.println("adding file: " + file);
			writer.addDocument(doc);
		}else{
			System.out.println("updating file: " + file);
			writer.updateDocument(new Term("path", file.getPath()), doc);
		}
	}

	public void close() throws Exception {
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		// 声明一个对象
		LuceneIndexFiles indexer = new LuceneIndexFiles();
		// 建立索引
		Date start = new Date();
		indexer.writeToIndex();
		Date end = new Date();

		System.out.println("建立索引用时 time of building the index: " + (end.getTime() - start.getTime()) + " millis");

		indexer.close();
	}
}
