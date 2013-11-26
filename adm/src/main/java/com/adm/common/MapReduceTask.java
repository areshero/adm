package com.adm.common;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;

import com.adm.common.WordCount.IntSumReducer;
import com.adm.common.WordCount.TokenizerMapper;

public class MapReduceTask extends Configured implements Tool{
	/**	
	 * 	代表任务执行成功。
	 */
	public static final int SUCCESS = 0;
	/**	
	 * 	代表任务执行失败。
	 */
	public static final int FAILURE = 1;

	public Job getJob() {
		return job;
	}

	private Job job;
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
	
	public void setUpTheJob() throws IOException{
		job = new Job(getConf(),"word count");
		
		job.setJarByClass(WordCount.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		Path input_path = new Path("hdfs://localhost:9000/user/areshero/input01");
		Path output_path = new Path("hdfs://localhost:9000/user/areshero/output01");
		
		FileInputFormat.addInputPath(job, input_path);
		FileOutputFormat.setOutputPath(job, output_path);
	}

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
