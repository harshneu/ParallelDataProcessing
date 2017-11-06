package main;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import PageRank.NodePageRank;
import PageRank.PageRankDetermCombiner;
import PageRank.PageRankDetermMapper;
import PageRank.PageRankDetermReducer;
import PageRank.PreProcessorMapper;
import PageRank.PreProcessorReducer;
import Top100.Top100Mapper;
import Top100.Top100Reducer;

import java.io.IOException;

// Driver class implements the desired results.
// Runs all 3 methods i.e. preprocessing, pagerank calculator and top100 records together to yield a result.
public class Driver extends Configured implements Tool {

	// Constants declaration
	public static final int incrementor = 1;
	public static final int zero = 0;
	public static final int two = 2;

	public static String iterator = "iterator";
	public static String pageCounter = "pageCounter";
	public static String change = "changeSum";

	// global counters ,these will be used throughout the program
	public static enum globalCounter {
		pageCount, changer
	}

	public int run(String[] args) throws Exception {

		// Configuration file
		Configuration conf = new Configuration();
		String[] newArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		// variable declarations,used in the program to determine the running
		// time.
		long startTime;
		long endTime;
		long execTime;

		// logic to handle incorrect number of commandline arguments
		if (newArgs.length < two) {
			System.out.println("Arguments<input><output>");
			return 1;
		}

		// Map reduce job to pre-process data
		startTime = System.currentTimeMillis();
		Job preprocessingJob = preProcessingJob(newArgs, conf);
		preprocessingJob.waitForCompletion(true);
		endTime = System.currentTimeMillis();
		execTime = endTime - startTime;
		// Gives the time of execution
		System.out.println("PreProcessing Time " + execTime + " milliseconds");
		System.out.println("*****************************************************************");
		long pageCount = preprocessingJob.getCounters().findCounter(globalCounter.pageCount).getValue();

		// initialising variables
		double delta = 0;
		int count = 0;

		// Map reduce job to compute page Rank

		startTime = System.currentTimeMillis();
		for (int i = zero; i < 10; i++) {
			count++;
			Job pageRankJob = pageRankJob(args, conf, pageCount, i, delta);
			pageRankJob.waitForCompletion(true);
			delta = Double.longBitsToDouble(pageRankJob.getCounters().findCounter(globalCounter.changer).getValue());
		}
		endTime = System.currentTimeMillis();
		execTime = endTime - startTime;
		// Gives the time of execution
		System.out.println("Iteration Time " + execTime + " milliseconds");
		System.out.println("*****************************************************************");

		// Map reduce job to find top 100 records with highest page ranks
		startTime = System.currentTimeMillis();
		// Takes the last output of the processed data as an input.
		Job topKPages = TopKRanksJob(args[1] + count, newArgs[newArgs.length - incrementor], conf);
		topKPages.waitForCompletion(true);
		endTime = System.currentTimeMillis();
		execTime = endTime - startTime;
		// Gives the time of execution
		System.out.println("Top 100 pages Determination Time " + execTime + " milliseconds");
		System.out.println("*****************************************************************");

		return 0;
	}

	// Function to create map reduce job to get Top-100 Records
	public static Job TopKRanksJob(String inputPath, String outputPath, Configuration conf) throws IOException {

		Job job2 = new Job(conf, "TopKRanks");
		job2.setJarByClass(Driver.class);
		job2.setMapperClass(Top100Mapper.class);
		job2.setOutputKeyClass(NullWritable.class);
		job2.setOutputValueClass(Text.class);
		job2.setReducerClass(Top100Reducer.class);
		job2.setInputFormatClass(SequenceFileInputFormat.class);
		// Input for top 100 jobs
		FileInputFormat.addInputPath(job2, new Path(inputPath));
		FileOutputFormat.setOutputPath(job2, new Path(outputPath));
		return job2;
	}

	// Function to create map reduce job to preprocess data set
	public static Job preProcessingJob(String[] args, Configuration conf) throws IOException {

		Job job1 = new Job(conf, "PreProcessing");
		job1.setJarByClass(Driver.class);
		job1.setReducerClass(PreProcessorReducer.class);
		job1.setMapperClass(PreProcessorMapper.class);
		job1.setMapOutputKeyClass(Text.class);
		job1.setMapOutputValueClass(NodePageRank.class);
		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(NodePageRank.class);
		job1.setOutputFormatClass(SequenceFileOutputFormat.class);
		// input and outputs on the basis of the arguments
		for (int i = zero; i < args.length - 1; ++i) {
			FileInputFormat.addInputPath(job1, new Path(args[i]));
		}
		FileOutputFormat.setOutputPath(job1, new Path(args[1] + zero));
		return job1;

	}

	// Function to create map reduce job to compute page rank
	public static Job pageRankJob(String[] args, Configuration conf, long pageCount, int iterationCount, double delta)
			throws IOException {

		conf.setLong("pageCount", pageCount);
		conf.setDouble("delta", delta);
		if (iterationCount == zero) {
			conf.setBoolean(iterator, true);
		} else {
			conf.setBoolean(iterator, false);
		}
		Job job = new Job(conf, "PageRank");
		job.setJarByClass(Driver.class);
		job.setReducerClass(PageRankDetermReducer.class);
		job.setCombinerClass(PageRankDetermCombiner.class);
		job.setMapperClass(PageRankDetermMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(NodePageRank.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NodePageRank.class);
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);

		// input and outputs on the basis of the arguments

		FileInputFormat.addInputPath(job, new Path(args[1] + iterationCount));
		FileOutputFormat.setOutputPath(job, new Path(args[1] + (iterationCount + incrementor)));
		return job;

	}

	// Main function This is to start the execution.
	public static void main(String[] args) throws Exception {
		int end = ToolRunner.run(new Driver(), args);
		System.exit(end);
	}
}
