package com.adm.common.fs;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public FileUtil() {
		// TODO Auto-generated constructor stub
	}

	public List<String> getAllFiles(File dir) {
		List<String> files = new ArrayList<>();
		
		
		
		return files;
	}
	
	
	public long getFileSize(File file) {

		if (!file.exists()) {
			return 0;
		}
		return file.length();
	}

	public long getDirSize(File dir) {
		if (dir == null) {
			return 0;
		}
		if (!dir.isDirectory()) {
			return 0;
		}
		long dirSize = 0;
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				dirSize += file.length();
			} else if (file.isDirectory()) {
				dirSize += getDirSize(file); // 如果遇到目录则通过递归调用继续统计
			}
		}
		return dirSize;
	}

	public String getDirSizeString(File dir) {
		long size = getDirSize(dir);
		return FormetFileSize(size);
	}

	public String FormetFileSize(long fileSize) {
		
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileSize < 1024) {
			fileSizeString = decimalFormat.format((double) fileSize) + "B";
		} else if (fileSize < 1048576) {
			fileSizeString = decimalFormat.format((double) fileSize / 1024)
					+ "K";
		} else if (fileSize < 1073741824) {
			fileSizeString = decimalFormat.format((double) fileSize / 1048576)
					+ "M";
		} else {
			fileSizeString = decimalFormat
					.format((double) fileSize / 1073741824) + "G";
		}
		return fileSizeString;
	}

	public static void main(String[] args) {
		FileUtil fileUtil = new FileUtil();
		File file = new File("./input");
		File file1 = new File("./input/Bill Clinton.txt");
		System.out.println("size : " + fileUtil.getDirSize(file));
		System.out.println("size : " + fileUtil.getDirSizeString(file));
		System.out.println("file size : " + fileUtil.getFileSize(file1));
	}
}
