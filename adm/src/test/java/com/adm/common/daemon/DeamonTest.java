package com.adm.common.daemon;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

import com.adm.common.daemon.SocketConfigurationConstant;
import com.adm.common.fs.FileUtil;

public class DeamonTest {

	@Test
	public void testClient() throws InterruptedException {
		try {
			//server ip: 127.0.0.1 port : 2223
			Socket socket = new Socket(SocketConfigurationConstant.SERVER_IP, SocketConfigurationConstant.PORT);
			System.out.println("Client is connecting to the server...");
			System.out.println("socket is connected? "+socket.isConnected());
			
			
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(outputStream);
			InputStream inputStream = socket.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			

			/******************************/
			//monitor the specific folder
			
			FileUtil fileUtil = new FileUtil();
			String sourceDirPathString = "./inputdoc";
			//String distDirPathString = "hdfs://localhost:9000/user/areshero";
			
			
			File dir = new File(sourceDirPathString);
			long currentDirSize = 0;;
			while(currentDirSize < 20000){
				Thread.sleep(1000);
				System.out.println("checking the folder size:" + currentDirSize);
				currentDirSize = fileUtil.getDirSize(dir);
			}
			
			
			/*
			String string1 = "./input";
			String string2 = "hdfs://localhost:9000/user/areshero/output_SmallFilesToSequenceFileConverter";
			printWriter.write(string1);
			printWriter.flush();
			printWriter.write(string2);*/			
			String[] aStrings = {sourceDirPathString,"hdfs://localhost:9000/user/areshero/output.seq"};
			printWriter.println(aStrings[0]);
			printWriter.println(aStrings[1]);
			printWriter.flush();
			System.out.println("write {inputpath ,outputpath} complete");
			
			//socket.shutdownOutput();
			
			
			int progress = 0;
			while(progress<100) {
				progress = Integer.parseInt(bufferedReader.readLine());
				System.out.println("progress - client" + progress +"%");
			}
			
			if(progress == 100){
				System.out.println("done!");
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
