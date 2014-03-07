package com.adm.common.util;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.HarFileSystem;
import org.apache.hadoop.fs.Path;

public class MergeHarFile {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.set("fs.default.name", "hdfs://xxx.xxx.xxx.xxx:9000");

		HarFileSystem fs = new HarFileSystem();
		fs.initialize(new URI("har:///user/heipark/20120108_15.har"), conf);
		FileStatus[] listStatus = fs.listStatus(new Path("sub_dir"));
		for (FileStatus fileStatus : listStatus) {
			System.out.println(fileStatus.getPath().toString());
		}
	}
}
