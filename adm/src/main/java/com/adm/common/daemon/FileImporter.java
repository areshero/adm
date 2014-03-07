package com.adm.common.daemon;

import java.util.List;

import com.adm.common.mapreducejob.WordCount;
import com.adm.common.mapreducetask.LittleMing;
import com.adm.common.mapreducetask.MapReduceTask;
import com.adm.common.util.SmallFilesToSequenceFileConverterTask;

public class FileImporter implements Runnable{

	
	private String sourceDirectory;
	private String destinationDirectory;
	
	
	public FileImporter(String[] paths) {
		sourceDirectory = paths[0];
		destinationDirectory = paths[1];
	}
	public FileImporter() {

	}
	
	
	
	@Override
	public void run() {
		LittleMing xiaoming = new LittleMing();

		
		//MapReduceTask task = new WordCount();
		//xiaoming.addMapReduceTask(task);
		
		
		MapReduceTask task2 = new SmallFilesToSequenceFileConverterTask();
		xiaoming.addMapReduceTask(task2);
		
		try {
			xiaoming.OnslaughtOfLittleMing();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	

}
