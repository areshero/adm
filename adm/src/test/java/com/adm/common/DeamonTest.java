package com.adm.common;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

public class DeamonTest {

	@Test
	public void testDaemonConnect() throws InterruptedException {
		try {
			Socket socket = new Socket("localhost", 2222);
			System.out.println("Client is connecting to the server...");
			
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(outputStream);
			InputStream inputStream = socket.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			
			
			
			printWriter.write('0');
			printWriter.flush();
			/*
			char[] reply = new char[1];
			while ( bufferedReader.read(reply, 0, 1) > 0 ) {
				int process = (int)reply[0];
				System.out.println("来自服务器的进度: " + process + "%");
				Thread.sleep(750);
				
				if ( process == 100 ) {
					break;
				} 
			}
			*/
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
