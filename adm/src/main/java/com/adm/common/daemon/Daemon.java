package com.adm.common.daemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Daemon extends ServerSocket {

	private static final int SERVER_PORT = 2222;

	public Daemon() throws IOException {
		super(SERVER_PORT);
		System.out.println("Daemon started");
	}

	public void serverSocketListen() throws Exception {
		while (true) {
			socket = accept();
			initializeSocket();
			combineFiles();
			// receiveFileImportRequest(paths);
			processFeedback();
			closeSocket();
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
		while (true) {
			try {
				Thread.sleep(1000);
				printWriter.write("aaa");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void combineFiles() {
		fileImporter = new FileImporter();
		fileImporterThread = new Thread(fileImporter);
		fileImporterThread.start();
	}

	private void closeSocket() throws IOException {
		bufferedReader.close();
		inputStream.close();
		printWriter.close();
		outputStream.close();
		socket.close();
	}


	private Socket socket = null;

	private InputStream inputStream = null;

	private OutputStream outputStream = null;

	private PrintWriter printWriter = null;

	private BufferedReader bufferedReader = null;

	private FileImporter fileImporter = null;
	/**
	 * FileImporter的线程包装对象。
	 */
	private Thread fileImporterThread = null;
}
