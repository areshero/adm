package com.adm.common.daemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Daemon {

	private static final int SERVER_PORT = 2222;

	public Daemon() throws IOException {
		//super(SERVER_PORT);
		serverSocket = new ServerSocket(SERVER_PORT);
		System.out.println("Daemon started");
	}

	public void serverSocketListen(String[] paths) throws Exception {
		while (true) {
			socket = serverSocket.accept();
			System.out.println("serverSocketListen? "+ socket.isConnected());
			initializeSocket();
			combineFiles(paths);
			// receiveFileImportRequest(paths);
			processFeedback();
			closeSocket();
			//System.out.println("is closed?");
			//System.out.println(socket.isClosed());
			// MemoryMonitor.logUsedMemory(logger, "Before Deamon System.gc()");
			// System.gc();
			// MemoryMonitor.logUsedMemory(logger, "After Deamon System.gc()");
		}
	}
	
	
	public void initializeSocket() throws IOException {
		inputStream = socket.getInputStream();
		outputStream = socket.getOutputStream();
		printWriter = new PrintWriter(outputStream);
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
	}
	private void processFeedback() {
		int i = 1;
		
		try {
			char[] reply = new char[10];
			bufferedReader.read(reply, 0, 10);
		
			System.out.println("in process inputstream:  " + reply[1]);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				Thread.sleep(500);
				System.out.println("in process:" + i);
				i++;
				printWriter.write(i);
				printWriter.flush();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (i > 17) {
				break;
			}

		}

	}

	private void combineFiles(String[] paths) {
		fileImporter = new FileImporter(paths);
		fileImporterThread = new Thread(fileImporter);
		fileImporterThread.start();
	}

	private void closeSocket() throws IOException {
		System.out.println("in cloase socket");
		bufferedReader.close();
		inputStream.close();
		printWriter.close();
		outputStream.close();
		socket.close();
	}
	
	private Socket socket = null;

	private ServerSocket serverSocket = null;
	
	private InputStream inputStream = null;

	private OutputStream outputStream = null;

	private PrintWriter printWriter = null;

	private BufferedReader bufferedReader = null;

	private FileImporter fileImporter = null;

	private Thread fileImporterThread = null;
}
