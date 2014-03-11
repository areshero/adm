package com.adm.common.fs;

import org.apache.hadoop.fs.FileSystem;

public class FileSystemUtil {
	private String path;
	private FileSystem fileSystem;
	
	
	public FileSystemUtil() {
		path="hdfs://localhost:9000";
	}
	
	
	public FileSystemUtil(String path) {
		this.path = path;
	}

	
	
	
	
}