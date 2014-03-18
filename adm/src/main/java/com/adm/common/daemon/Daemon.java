package com.adm.common.daemon;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.print.DocFlavor.STRING;

import com.adm.common.util.SmallFilestoSequenceFileConverter;

public class Daemon {

	private static final int SERVER_PORT = 2223;

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
			receivePath();
			
			
			//use mapreduce task to combine small files
			//combineFiles(paths);
			
			//use sequence writer to combine small files
			combineFilesBySeqWriter();
			
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
	
	
	private void combineFilesBySeqWriter(){
		converter = new SmallFilestoSequenceFileConverter(inputPath, outputPath);
		converterThread = new Thread(converter);
		converterThread.start();
	}
	
	
	public void serverSocketListen() throws Exception{
		while (true) {
			socket = serverSocket.accept();
			System.out.println("serverSocketListen? "+ socket.isConnected());
			initializeSocket();
			receivePath();
			
			
			//use mapreduce task to combine small files
			//String[] aStrings = {inputPath, outputPath};
			//combineFiles(aStrings);
			
			//use sequence writer to combine small files
			combineFilesBySeqWriter();
			
			
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
	
	
	public void receivePath(){
		//char[] reply = new char[10];
		System.out.println("in receive path");
	
		try {
			inputPath = bufferedReader.readLine();
			outputPath = bufferedReader.readLine();
			System.out.println("in process receive inputstream:  " + inputPath);
			System.out.println("in process receive inputstream:  " + outputPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	
	public void initializeSocket() throws IOException {
		inputStream = socket.getInputStream();
		outputStream = socket.getOutputStream();
		printWriter = new PrintWriter(outputStream);
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
	}
	private void processFeedback() throws IOException {
		/*
		try {
			char[] reply = new char[10];
			bufferedReader.read(reply, 0, 10);
		
			System.out.println("in process inputstream:  " + reply[1]);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		*/
		int i = 0;
		
		while (true) {
			try {
				Thread.sleep(750);
				//TODO:get the file import progress
				i = converter.getPorgress();
				
				System.out.println("process - server:" + i + "%");
				
				printWriter.println(i);
				printWriter.flush();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			if (i == 100) {
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
	
	private String inputPath = null;
	private String outputPath = null;
	
	
	private Socket socket = null;

	private ServerSocket serverSocket = null;
	
	private InputStream inputStream = null;

	
	private OutputStream outputStream = null;

	private PrintWriter printWriter = null;

	private BufferedReader bufferedReader = null;

	private FileImporter fileImporter = null;

	private SmallFilestoSequenceFileConverter converter = null;
	
	private Thread converterThread = null;
	
	private Thread fileImporterThread = null;
}
