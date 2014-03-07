package com.adm.common.daemon;


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
		
		SmallFilesToSequenceFileConverterTask task2 = new SmallFilesToSequenceFileConverterTask();
		task2.setSourceDirectory(sourceDirectory);
		task2.setDestinationDirectory(destinationDirectory);
		xiaoming.addMapReduceTask(task2);
		
		try {
			xiaoming.OnslaughtOfLittleMing();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	

}
