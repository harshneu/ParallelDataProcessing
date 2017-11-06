package patterncombiner;

import java.io.IOException;

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
import au.com.bytecode.opencsv.CSVParser;

//Program implementation of the combiner pattern
public class Combiner {
	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		String otherArgs[] = new GenericOptionsParser(conf, args).getRemainingArgs();

		if (otherArgs.length < 2) {
			System.err.println("Please make sure you have added enough arguments to run the code");
			System.exit(2);
		}

		// Job Configuration for Hadoop
		Job job = new Job(conf, "Please enter valid data");
		job.setJarByClass(Combiner.class);
		job.setMapperClass(MapperStationClass.class);
		job.setCombinerClass(StationCombiner.class);
		job.setReducerClass(TempDeducter.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Station.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);

		for (int i = 0; i < otherArgs.length - 1; i++) {
			FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
		}
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

	// Mapper emits StationId as key and Record object as value containing
	// Station details
	public static class MapperStationClass extends Mapper<Object, Text, Text, Station> {
		private Text word = new Text();

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			// Parse the Station details from the input line
			String stationDetail[] = new CSVParser().parseLine(value.toString());

			// If input line type is of TMAX feed the Station details as a
			// Record object with TMAX type and emit it
			if (stationDetail[2].equals("TMAX")) {
				word.set(stationDetail[0]);
				Station obj = new Station(new Text(stationDetail[0]),
						new DoubleWritable(Double.parseDouble(stationDetail[3])), new Text("TMAX"), new IntWritable(1));
				context.write(word, obj);
			}
			// If input line type is of TMIN feed the Station details as a
			// Record object with TMIN type and emit it
			else if (stationDetail[2].equals("TMIN")) {
				word.set(stationDetail[0]);
				Station obj = new Station(new Text(stationDetail[0]),
						new DoubleWritable(Double.parseDouble(stationDetail[3])), new Text("TMIN"), new IntWritable(1));
				context.write(word, obj);
			}
		}

	}

	// Intermediate Combiner that just accumulates similar records i.e of same
	// type(either "TMAX" or "TMIN") into
	// respective objects and emits those objects hence decreases the work of
	// reducer
	public static class StationCombiner extends Reducer<Text, Station, Text, Station> {
		public void reduce(Text key, Iterable<Station> records, Context context)
				throws IOException, InterruptedException {

			// Accumulators for Combiner
			// maintains count of record type TMIN
			Integer tminCount = 0;
			// maintains total temperature value for record type TMIN
			Double tminVal = 0.0;
			// maintains count of record type TMAX
			Integer tmaxCount = 0;
			// maintains total temperature value for record type TMAX
			Double tmaxVal = 0.0;

			// Iterate over the list of records to gather data
			// on accumulators
			for (Station temp : records) {
				if (temp.getType().toString().equals("TMAX")) {
					tmaxVal += temp.getVal().get();
					tmaxCount += 1;
				} else if (temp.getType().toString().equals("TMIN")) {
					tminVal += temp.getVal().get();
					tminCount += 1;
				}

			}

			// Feed the accumulated values for TMAX type and TMIN type on two
			// objects namely max and min of
			// the type Record
			Station max = new Station(key, new DoubleWritable(tmaxVal), new Text("TMAX"), new IntWritable(tmaxCount));
			Station min = new Station(key, new DoubleWritable(tminVal), new Text("TMIN"), new IntWritable(tminCount));

			// Emit both kinds of object i.e
			// Emit Accumulated values in record of type TMAX
			context.write(key, max);
			// Emit Accumulated values in record of type TMIN
			context.write(key, min);
		}
	}

	// Reducer determines the tminAverage and tmaxAverage
	public static class TempDeducter extends Reducer<Text, Station, Text, NullWritable> {
		public void reduce(Text key, Iterable<Station> records, Context context)
				throws IOException, InterruptedException {

			int minCount = 0;
			double minTempCount = 0.0;
			int maxTempCount = 0;
			double maxTempSum = 0.0;
			double maxtempAverage = 0.0;
			double minTempAverage = 0.0;

			for (Station temp : records) {
				if (temp.getType().toString().equals("TMAX")) {
					maxTempSum += temp.getVal().get();
					maxTempCount += temp.getCount().get();
				} else if (temp.getType().toString().equals("TMIN")) {
					minTempCount += temp.getVal().get();
					minCount += temp.getCount().get();
				}

			}

			// average calculator for minimum and maximum temprature.

			maxtempAverage = maxTempSum / maxTempCount;

			// average calculator for minimum and maximum temprature.
			minTempAverage = minTempCount / minCount;

			Text result = new Text();

			result.set(key.toString() + ", " + minTempAverage + ", " + maxtempAverage);

			// Emits the result
			context.write(result, NullWritable.get());
		}
	}
}
