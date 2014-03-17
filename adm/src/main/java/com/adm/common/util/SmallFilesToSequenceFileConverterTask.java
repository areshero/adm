package com.adm.common.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import com.adm.common.mapreducetask.MapReduceTask;
import com.adm.common.filepraser.CovertFile;

public class SmallFilesToSequenceFileConverterTask extends MapReduceTask{

	//静态内部类，作为mapper
    static class SequenceFileMapper extends Mapper<NullWritable, BytesWritable, Text, BytesWritable>
    {
        private Text filenameKey;
         
        //setup在task开始前调用，这里主要是初始化filenamekey
        @Override
        protected void setup(Context context)
        {
            InputSplit split = context.getInputSplit();
            Path path = ((FileSplit) split).getPath();
            filenameKey = new Text(path.toString());
        }
        @Override
        public void map(NullWritable key, BytesWritable value, Context context)
                throws IOException, InterruptedException{
        	Parser parser = new AutoDetectParser(); // Should auto-detect!

        	byte[] originalFileBytes = value.getBytes();
        	InputStream originalFileStream = new ByteArrayInputStream(originalFileBytes);
        	
    		ContentHandler handler = new BodyContentHandler(originalFileStream.available());
    		Metadata metadata = new Metadata();
    		ParseContext parseContext = new ParseContext();
    		
    		//System.out.println(stream.a);
    		try {
    			parser.parse(originalFileStream, handler, metadata, parseContext);
            	String convertedValue = parseContext.toString();
            	byte[] convertedBytes = convertedValue.getBytes();
            	value.set(convertedBytes, 0, convertedBytes.length);
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (TikaException e) {
				e.printStackTrace();
			} finally {
    			originalFileStream.close();
    		}
        	
            context.write(filenameKey, value);
        }
    }

     
//    public static void main(String [] args) throws Exception
//    {
//        int exitCode = ToolRunner.run(new SmallFilesToSequenceFileConverter(), args);
//        System.exit(exitCode);
//    }

	@Override
	public void setUpTheJob() throws IOException {
		
		/*
        Configuration conf = new Configuration();
        Job job = new Job(conf);
        job.setJobName("SmallFilesToSequenceFileConverter");
         */
        job = new Job(getConf(),"SmallFilesToSequenceFileConverter");
        //Path input_path = new Path("hdfs://localhost:9000/user/areshero/input01");
        //Path input_path = new Path("./input");
		//Path output_path = new Path("hdfs://localhost:9000/user/areshero/output_SmallFilesToSequenceFileConverter");

		
		Path input_path = new Path(getSourceDirectory());
		Path output_path = new Path(getDestinationDirectory());
		FileInputFormat.addInputPath(job, input_path);

		FileOutputFormat.setOutputPath(job, output_path);
        
        //System.out.println("reduce progress " + job.reduceProgress()); 
        //再次理解此处设置的输入输出格式。。。它表示的是一种对文件划分，索引的方法
        job.setInputFormatClass(WholeFileInputFormat.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BytesWritable.class);
         
        job.setMapperClass(SequenceFileMapper.class);
		
	}
	
	public String getSourceDirectory() {
		return sourceDirectory;
	}
	public void setSourceDirectory(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory;
	}
	public String getDestinationDirectory() {
		return destinationDirectory;
	}
	public void setDestinationDirectory(String destinationDirectory) {
		this.destinationDirectory = destinationDirectory;
	}

	private String sourceDirectory = null;
	private String destinationDirectory = null;

}
