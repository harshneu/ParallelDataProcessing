package nocombinerpattern;

import java.io.IOException;
import au.com.bytecode.opencsv.CSVParser;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

// Program implements a simple Mapper and a Reducer for 
// Weather Data Analysis 
public class NoCombiner {

	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		String otherArgs[] = new GenericOptionsParser(conf, args).getRemainingArgs();

		if (otherArgs.length < 2) {
			System.err.println("Please make sure you have added enough arguments to run the code");
			System.exit(2);
		}
		// Job Configuration for Hadoop
		Job job = new Job(conf, "Please enter valid data");
		job.setJarByClass(NoCombiner.class);
		job.setMapperClass(MapperStationClass.class);
		job.setReducerClass(TempDeducter.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Station.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);

		for (int i = 0; i < otherArgs.length - 1; ++i) {
			FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
		}
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

	// Mapper emits StationId and station details
	public static class MapperStationClass extends Mapper<Object, Text, Text, Station> {
		private Text word = new Text();

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			String stationDetail[] = new CSVParser().parseLine(value.toString());

			if (stationDetail[2].equals("TMAX")) {
				word.set(stationDetail[0]);
				Station obj = new Station(new Text(stationDetail[0]),
						new DoubleWritable(Double.parseDouble(stationDetail[3])), new Text("TMAX"), new IntWritable(1));
				context.write(word, obj);
			}

			else if (stationDetail[2].equals("TMIN")) {
				word.set(stationDetail[0]);
				Station obj = new Station(new Text(stationDetail[0]),
						new DoubleWritable(Double.parseDouble(stationDetail[3])), new Text("TMIN"), new IntWritable(1));
				context.write(word, obj);
			}
		}

	}

	// Reducer takes Station Id and List of records as input,
	// calculates average of minimum and max temperature
	public static class TempDeducter extends Reducer<Text, Station, Text, NullWritable> {
		public void reduce(Text key, Iterable<Station> records, Context context)
				throws IOException, InterruptedException {

			int minTempCount = 0;
			double minTemptotal = 0.0;
			int maxTempCount = 0;
			double maxtempTotal = 0.0;
			double maxTempAverage = 0.0;
			double minTempAverage = 0.0;

			for (Station temp : records) {
				if (temp.getType().toString().equals("TMAX")) {
					maxtempTotal += temp.getVal().get();
					maxTempCount += temp.getCount().get();
				} else if (temp.getType().toString().equals("TMIN")) {
					minTemptotal += temp.getVal().get();
					minTempCount += temp.getCount().get();
				}
			}

			// Average calculator for maximum and minimum temprature
			maxTempAverage = maxtempTotal / maxTempCount;

			minTempAverage = minTemptotal / minTempCount;

			Text result = new Text();

			result.set(key.toString() + ", " + minTempAverage + ", " + maxTempAverage);

			// Emit the result
			context.write(result, NullWritable.get());

		}
	}
}
