package com.adm.common.daemon;

import java.io.IOException;

public class DaemonScheduler {
	public static void main(String[] args) {
		
		try {
			
			//Path input_path = new Path("./input");
			//Path output_path = new Path("hdfs://localhost:9000/user/areshero/output_SmallFilesToSequenceFileConverter");

			//String[] aStrings = {"./inputdoc","hdfs://localhost:9000/user/areshero/output_SmallFilesToSequenceFileConverter"};
			Daemon daemon = new Daemon();
			
			
			//daemon.serverSocketListen(aStrings);
			
			//start daemon
			daemon.serverSocketListen();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
