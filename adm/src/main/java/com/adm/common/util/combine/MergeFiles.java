package com.adm.common.util.combine;

import java.io.IOException;  
  
  
import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.conf.Configured;  
import org.apache.hadoop.fs.Path;  
  
import org.apache.hadoop.io.BytesWritable;  
import org.apache.hadoop.io.Text;  
import org.apache.hadoop.mapreduce.Job;  
import org.apache.hadoop.mapreduce.Mapper;  
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;  
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;  
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;  
import org.apache.hadoop.util.Tool;  
import org.apache.hadoop.util.ToolRunner;  
  
public class MergeFiles extends Configured implements Tool {   
    public static class MapClass extends Mapper<BytesWritable, Text, BytesWritable, Text> {  
  
        public void map(BytesWritable key, Text value, Context context)  
                throws IOException, InterruptedException {  
            context.write(key, value);  
        }  
    } // END: MapClass  
  
      
    public int run(String[] args) throws Exception {  
        Configuration conf = new Configuration();  
        conf.set("mapred.max.split.size", "157286400");  
        conf.setBoolean("mapred.output.compress", true);  
        Job job = new Job(conf);  
        job.setJobName("MergeFiles");  
        job.setJarByClass(MergeFiles.class);  
  
        job.setMapperClass(MapClass.class);  
        job.setInputFormatClass(CombineSequenceFileInputFormat.class);  
        job.setOutputFormatClass(SequenceFileOutputFormat.class);  
        job.setOutputKeyClass(BytesWritable.class);  
        job.setOutputValueClass(Text.class);  
  
        FileInputFormat.addInputPaths(job, args[0]);  
        FileOutputFormat.setOutputPath(job, new Path(args[1]));  
  
        job.setNumReduceTasks(0);  
  
        return job.waitForCompletion(true) ? 0 : 1;  
    } // END: run  
  
    public static void main(String[] args) throws Exception {  
        int ret = ToolRunner.run(new MergeFiles(), args);  
        System.exit(ret);  
    } // END: main  
} //   
