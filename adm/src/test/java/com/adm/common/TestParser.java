package com.adm.common;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class TestParser {

	@Test
	public void test() throws IOException, SAXException, TikaException {
		Parser parser = new AutoDetectParser(); // Should auto-detect!
		InputStream stream = new FileInputStream("./inputdoc/Revised GRE.docx");
		ContentHandler handler = new BodyContentHandler(stream.available());
		Metadata metadata = new Metadata();
		ParseContext context = new ParseContext();

		
		//System.out.println(stream.a);
		try {
			parser.parse(stream, handler, metadata, context);
		} finally {
			stream.close();
		}
		
		System.out.println("tostring : " + 
				handler.toString());
		//assertEquals("application/pdf", metadata.get(Metadata.CONTENT_TYPE));
	}

}
