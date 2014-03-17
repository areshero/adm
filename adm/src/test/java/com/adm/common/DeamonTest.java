package com.adm.common;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

import com.adm.common.fs.FileUtil;

public class DeamonTest {

	@Test
	public void testClient() throws InterruptedException {
		try {
			Socket socket = new Socket("127.0.0.1", 2222);
			System.out.println("Client is connecting to the server...");
			System.out.println("socket is connected? "+socket.isConnected());
			
			
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(outputStream);
			InputStream inputStream = socket.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			
			
			FileUtil fileUtil = new FileUtil();
			File dir = new File("./input");
			long currentDirSize = fileUtil.getDirSize(dir);
			//while (currentDirSize < 2000000000){
			//	Thread.sleep(500);
			//}
		
			String[] aStrings = {"./inputdoc","hdfs://localhost:9000/user/areshero/output_SmallFilesToSequenceFileConverter"};
			/*
			String string1 = "./input";
			String string2 = "hdfs://localhost:9000/user/areshero/output_SmallFilesToSequenceFileConverter";
			printWriter.write(string1);
			printWriter.flush();
			printWriter.write(string2);
*/			
			printWriter.println(aStrings[0]);
			printWriter.println(aStrings[1]);
			printWriter.flush();
			System.out.println("write complete");
			
			//socket.shutdownOutput();
			int progress = 0;
			while(progress<10) {
			
				progress = Integer.parseInt(bufferedReader.readLine());
				System.out.println("progress " + progress);
			}

			bufferedReader.close();
			inputStream.close();
			printWriter.close();
			outputStream.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
