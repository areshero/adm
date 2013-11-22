package com.adm.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import junit.framework.TestCase;

public class TestInputStream extends TestCase {

	protected static void setUpBeforeClass() throws Exception {
	}

	protected static void tearDownAfterClass() throws Exception {
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void test() throws IOException {
		String uri = "hdfs://127.0.0.1:9000/user/areshero/input01/hadoop_input_test.txt";
    	Configuration conf = new Configuration();
    	FileSystem fs = FileSystem.get(URI.create(uri), conf);
    	InputStream in = null;
    	FSDataInputStream fsDataInputStream = null;
    	try {
			in = fs.open(new Path(uri));
			fsDataInputStream = fs.open(new Path(uri));
			System.err.println("inputstream");
			org.apache.hadoop.io.IOUtils.copyBytes(in, System.out, 4096, false);
			
			System.err.println("fsdatainputstream");
			org.apache.hadoop.io.IOUtils.copyBytes(fsDataInputStream, System.out, 4096, false);
			System.err.println("go back from the start of the file");
			fsDataInputStream.seek(0);
			org.apache.hadoop.io.IOUtils.copyBytes(fsDataInputStream, System.out, 4096, false);
			fsDataInputStream.seek(1);
			org.apache.hadoop.io.IOUtils.copyBytes(fsDataInputStream, System.out, 4096, false);
			/**/
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			org.apache.hadoop.io.IOUtils.closeStream(in);
			org.apache.hadoop.io.IOUtils.closeStream(fsDataInputStream);
		}
	}
}
