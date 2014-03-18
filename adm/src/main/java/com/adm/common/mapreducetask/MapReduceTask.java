package com.adm.common.mapreducetask;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;


public abstract class MapReduceTask extends Configured implements Tool{

	public static final int SUCCESS = 0;
	public static final int FAILURE = 1;

	protected final Job getJob() {
		return job;
	}

	protected Job job;
	private String[] args;
	
	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

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
