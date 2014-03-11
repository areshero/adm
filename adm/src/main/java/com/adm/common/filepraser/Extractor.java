package com.adm.common.filepraser;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.POIOLE2TextExtractor;
import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.hdgf.extractor.VisioTextExtractor;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.xmlbeans.XmlException;

public class Extractor {
	
	
	public Extractor() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void getcontent() throws IOException, InvalidFormatException, OpenXML4JException, XmlException {
		FileInputStream fis = new FileInputStream("./input/GREwriting.doc");
		POIFSFileSystem fileSystem = new POIFSFileSystem(fis);
		// Firstly, get an extractor for the Workbook
		POIOLE2TextExtractor oleTextExtractor = ExtractorFactory.createExtractor(fileSystem);
		
		
		// Then a List of extractors for any embedded Excel, Word, PowerPoint
		// or Visio objects embedded into it.
		POITextExtractor[] embeddedExtractors = ExtractorFactory.getEmbededDocsTextExtractors(oleTextExtractor);
		
		
		for (POITextExtractor textExtractor : embeddedExtractors) {
			
			// If the embedded object was an Excel spreadsheet.
			if (textExtractor instanceof ExcelExtractor) {
				ExcelExtractor excelExtractor = (ExcelExtractor) textExtractor;
				System.out.println(excelExtractor.getText());
			}
			// A Word Document
			else if (textExtractor instanceof WordExtractor) {
				WordExtractor wordExtractor = (WordExtractor) textExtractor;
				String[] paragraphText = wordExtractor.getParagraphText();
				for (String paragraph : paragraphText) {
					System.out.println(paragraph);
				}
				// Display the document's header and footer text
				System.out.println("Footer text: "
						+ wordExtractor.getFooterText());
				System.out.println("Header text: "
						+ wordExtractor.getHeaderText());
			}
			// PowerPoint Presentation.
			else if (textExtractor instanceof PowerPointExtractor) {
				PowerPointExtractor powerPointExtractor = (PowerPointExtractor) textExtractor;
				System.out.println("Text: " + powerPointExtractor.getText());
				System.out.println("Notes: " + powerPointExtractor.getNotes());
			}
			// Visio Drawing
			else if (textExtractor instanceof VisioTextExtractor) {
				VisioTextExtractor visioTextExtractor = (VisioTextExtractor) textExtractor;
				System.out.println("Text: " + visioTextExtractor.getText());
			}
		}
	}
	
	
	public static void main(String[] args) {
		Extractor extractor = new Extractor();
		try {
			System.out.println("begin");
			extractor.getcontent();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OpenXML4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
