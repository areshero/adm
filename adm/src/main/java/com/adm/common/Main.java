package com.adm.common;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		LittleMing xiaoming = new LittleMing();
		MapReduceTask task = new MapReduceTask();
		xiaoming.addMapReduceTask(task);
		xiaoming.OnslaughtOfLittleMing();

	}

}
