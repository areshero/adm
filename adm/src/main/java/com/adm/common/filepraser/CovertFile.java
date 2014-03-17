package com.adm.common.filepraser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.POITextExtractor;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
/**
 * 
 * @author areshero
 * 
 */
public class CovertFile {

	/**
	 * 从word 2003文档中提取纯文本
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String extractTextFromDOC(InputStream is) throws IOException {
		WordExtractor ex = new WordExtractor(is); // is是WORD文件的InputStream
		return ex.getText();
	}

	/**
	 * 从word 2007文档中提取纯文本
	 * 
	 * @param fileName
	 * @return
	 */
	public static String extractTextFromDOC2007(String fileName) {
		try {
			OPCPackage opcPackage = POIXMLDocument.openPackage(fileName);
			POIXMLTextExtractor ex = new XWPFWordExtractor(opcPackage);
			return ex.getText();
		} catch (Exception e) {
			return "";
		}
	}

	// POITextExtractor extractor = ExtractorFactory.createExtractor(f);
	// return extractor.getText();

	/**
	 * 从excel 2003文档中提取纯文本
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private static String extractTextFromXLS(InputStream is) throws IOException {
		StringBuffer content = new StringBuffer();
		HSSFWorkbook workbook = new HSSFWorkbook(is); // 创建对Excel工作簿文件的引用

		for (int numSheets = 0; numSheets < workbook.getNumberOfSheets(); numSheets++) {
			if (null != workbook.getSheetAt(numSheets)) {
				HSSFSheet aSheet = workbook.getSheetAt(numSheets); // 获得一个sheet

				for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet
						.getLastRowNum(); rowNumOfSheet++) {
					if (null != aSheet.getRow(rowNumOfSheet)) {
						HSSFRow aRow = aSheet.getRow(rowNumOfSheet); // 获得一行

						for (short cellNumOfRow = 0; cellNumOfRow <= aRow
								.getLastCellNum(); cellNumOfRow++) {
							if (null != aRow.getCell(cellNumOfRow)) {
								HSSFCell aCell = aRow.getCell(cellNumOfRow); // 获得列值

								if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
									content.append(aCell.getNumericCellValue());
								} else if (aCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
									content.append(aCell.getBooleanCellValue());
								} else {
									content.append(aCell.getStringCellValue());
								}
							}
						}
					}
				}
			}
		}
		return content.toString();
	}

	/**
	 * 从excel 2007文档中提取纯文本
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	private static String extractTextFromXLS2007(String fileName)
			throws Exception {
		StringBuffer content = new StringBuffer();

		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		XSSFWorkbook xwb = new XSSFWorkbook(fileName);

		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < xwb.getNumberOfSheets(); numSheet++) {
			XSSFSheet xSheet = xwb.getSheetAt(numSheet);
			if (xSheet == null) {
				continue;
			}

			// 循环行Row
			for (int rowNum = 0; rowNum <= xSheet.getLastRowNum(); rowNum++) {
				XSSFRow xRow = xSheet.getRow(rowNum);
				if (xRow == null) {
					continue;
				}

				// 循环列Cell
				for (int cellNum = 0; cellNum <= xRow.getLastCellNum(); cellNum++) {
					XSSFCell xCell = xRow.getCell(cellNum);
					if (xCell == null) {
						continue;
					}

					if (xCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
						content.append(xCell.getBooleanCellValue());
					} else if (xCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						content.append(xCell.getNumericCellValue());
					} else {
						content.append(xCell.getStringCellValue());
					}
				}
			}
		}

		return content.toString();
	}

	/**
	 * 从excel 2007文档中提取纯文本
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getXLS2007(String fileName) {
		String doc = "";
		try {
			doc = extractTextFromXLS2007(fileName);
			return doc;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 从ppt 2003、2007文档中提取纯文本
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getPPTX(String fileName) {
		String doc = "";
		try {
			File inputFile = new File(fileName);
			POITextExtractor extractor = ExtractorFactory
					.createExtractor(inputFile);
			doc = extractor.getText();
			return doc;
		} catch (Exception e) {
			return "";
		}
	}

	public static void main(String[] args) {
		try {
			String wordFile = "./input/test.docx";
			String wordText2007 = CovertFile.extractTextFromDOC2007(wordFile);
			System.out.println("wordText2007=======" + wordText2007);
			//
			// InputStream is = new FileInputStream("D:/XXX研发中心技术岗位职位需求.xls");
			// String excelText = CovertFile.extractTextFromXLS(is);
			// System.out.println("text2003==========" + excelText);

			// String excelFile = "D:/zh.xlsx";
			// String excelText2007 =
			// CovertFile.extractTextFromXLS2007(excelFile);
			// System.out.println("excelText2007==========" + excelText2007);

			// String pptFile = "D:/zz3.ppt";
			// String pptx = CovertFile.getPPTX(pptFile);
			// System.out.println("pptx==========" + pptx);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
