package com.adm.common.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.Text;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;



public class SmallFilestoSequenceFileConverter implements Runnable{
	
	private Configuration configuration = null;
	private Path distPath = null;
	private String distDir = null;
	private String sourceDir = null;
	private FileSystem fileSystem = null;
	private Text key = null;
	private Text value = null;
	private SequenceFile.Writer seqWriter = null;
	private int doneFiles = 0;
	private int totalFiles = 0;
	
	public SmallFilestoSequenceFileConverter() {
	}
	
	public SmallFilestoSequenceFileConverter(String sourceDirPath, String distDirPath){
		sourceDir = sourceDirPath;
		distDir = distDirPath;
	}
	
	public int getPorgress(){
		return (int)(((float)doneFiles / totalFiles ) * 100);
	}

	private void start() throws IOException{
		init();
		System.out.println("begin combining files");
		File sourceDirFile = new File(sourceDir);
		totalFiles = sourceDirFile.listFiles().length;
		for(File currentFile : sourceDirFile.listFiles()){
			//combine small file to sequencefile
			key.set(currentFile.getName());
			
			Parser parser = new AutoDetectParser(); // Should auto-detect!
        	InputStream originalFileStream = new FileInputStream(currentFile);
        	
        	//TODO figure out how to set the limit!!!!!!
    		ContentHandler handler = new BodyContentHandler(300000);
    		
    		Metadata metadata = new Metadata();
    		//metadata.set("content type ","doc" );
    		ParseContext parseContext = new ParseContext();
    		parseContext.set(Parser.class, parser);
    		
    		try {
    			parser.parse(originalFileStream, handler, metadata, parseContext);
    			
    			//System.out.println("hander " + handler.toString());
            	//String convertedValue = parseContext.toString();
            	//System.out.println("convert value " + convertedValue);
            	//byte[] convertedBytes = convertedValue.getBytes();
            	value.set(handler.toString());
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (TikaException e) {
				e.printStackTrace();
			} finally {
    			originalFileStream.close();
    		}
			
    		seqWriter.append(key, value);
    		seqWriter.sync();
			
			doneFiles++;
		}
		
		IOUtils.closeStream(seqWriter);
		

	
	}

	private void init() throws IOException {
		configuration = new Configuration();
		fileSystem = FileSystem.get(URI.create(distDir),configuration);
		distPath = new Path(distDir);
		key = new Text();
		value = new Text();
		seqWriter = SequenceFile.createWriter(fileSystem, 
				configuration, distPath, key.getClass(), value.getClass(), CompressionType.BLOCK);
	}

	@Override
	public void run() {
		try {
			start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
