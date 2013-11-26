package com.adm.common.mapreducetask;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;


public abstract class MapReduceTask extends Configured implements Tool{
	/**	
	 * 	代表任务执行成功。
	 */
	public static final int SUCCESS = 0;
	/**	
	 * 	代表任务执行失败。
	 */
	public static final int FAILURE = 1;

	protected final Job getJob() {
		return job;
	}

	protected Job job;
	public String[] getCommandArguments() {
		return commandArguments;
	}

	public void setCommandArguments(String[] commandArguments) {
		this.commandArguments = commandArguments;
	}

	private String[] commandArguments;
	
	@Override
	public Configuration getConf() {
		if(job == null){
			return super.getConf();
		}
		return job.getConfiguration();
	}
	
	public abstract void setUpTheJob() throws IOException ;

	@Override
	public int run(String[] arg0) throws Exception {
		
		setUpTheJob();
		boolean result = job.waitForCompletion(true) ;
		cleanup();
		return result ? SUCCESS : FAILURE;
	}
	
	public void cleanup(){
	}

}
