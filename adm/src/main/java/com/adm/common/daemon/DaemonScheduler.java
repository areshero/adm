package com.adm.common.daemon;

import java.io.IOException;

public class DaemonScheduler {
	public static void main(String[] args) {
		
		try {
			Daemon daemon = new Daemon();
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
