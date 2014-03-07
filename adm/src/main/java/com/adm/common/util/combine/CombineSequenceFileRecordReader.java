package com.adm.common.util.combine;

import java.io.IOException;

import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileRecordReader;
import org.apache.hadoop.util.ReflectionUtils;

public class CombineSequenceFileRecordReader<K, V> extends RecordReader<K, V> {
	
	
	private CombineFileSplit split;
	private TaskAttemptContext context;
	private int index;
	private RecordReader<K, V> recordReader;

	@SuppressWarnings("unchecked")
	public CombineSequenceFileRecordReader(CombineFileSplit split,
			TaskAttemptContext context, Integer index) throws IOException,
			InterruptedException {
		this.index = index;
		this.split = (CombineFileSplit) split;
		this.context = context;

		this.recordReader = ReflectionUtils.newInstance(SequenceFileRecordReader.class,
				context.getConfiguration());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(InputSplit curSplit, TaskAttemptContext curContext)
			throws IOException, InterruptedException {
		this.split = (CombineFileSplit) curSplit;
		this.context = curContext;

		if (null == recordReader) {
			recordReader = ReflectionUtils.newInstance(SequenceFileRecordReader.class,
					context.getConfiguration());
		}

		FileSplit fileSplit = new FileSplit(this.split.getPath(index),
				this.split.getOffset(index), this.split.getLength(index),
				this.split.getLocations());

		this.recordReader.initialize(fileSplit, this.context);
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		return recordReader.getProgress();
	}

	@Override
	public void close() throws IOException {
		if (null != recordReader) {
			recordReader.close();
			recordReader = null;
		}
	}

	@Override
	public K getCurrentKey() throws IOException, InterruptedException {
		return recordReader.getCurrentKey();
	}

	@Override
	public V getCurrentValue() throws IOException, InterruptedException {
		return recordReader.getCurrentValue();
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		return recordReader.nextKeyValue();
	}
}