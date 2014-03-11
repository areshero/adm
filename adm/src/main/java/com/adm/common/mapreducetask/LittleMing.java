package com.adm.common.mapreducetask;

import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.util.ToolRunner;



public class LittleMing {
	
	public int OnslaughtOfLittleMing() throws Exception{
		for(MapReduceTask task : tasks){
			int result = runMapReduceTask(task);
			if ( result != MapReduceTask.SUCCESS ) {
				return result;
			}
		}
		
		return MapReduceTask.SUCCESS;
	}
	
	public int runMapReduceTask(MapReduceTask task) throws Exception{
		
		int result = ToolRunner.run(task, task.getCommandArguments());
		
		if ( result != MapReduceTask.SUCCESS ) {
			System.err.println("ERROR! in runMapReduceTask");
		}
		
		return result;
	}
	
	public void addMapReduceTask(MapReduceTask task){
		tasks.add(task);
	}
	
	/**	
	 * 	Map/Reduce任务队列。
	 */
	List<MapReduceTask> tasks = new LinkedList<MapReduceTask>();

}
