package com.adm.common.search;

import org.apache.lucene.util.Version;

public class LuceneConfConstant {
	public final static String INDEX_FILE_PATH = "./lucene/txt"; //索引的文件的存放路径 
    public final static String INDEX_STORE_PATH = "./lucene/index"; //索引的存放位置 
    public final static Version matchVersion = Version.LUCENE_47; // lucene version
}
