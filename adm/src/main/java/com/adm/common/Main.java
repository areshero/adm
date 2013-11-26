package com.adm.common;

import com.adm.common.mapreducejob.WordCount;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		LittleMing xiaoming = new LittleMing();
		MapReduceTask task = new WordCount();
		xiaoming.addMapReduceTask(task);
		xiaoming.OnslaughtOfLittleMing();

	}

}
