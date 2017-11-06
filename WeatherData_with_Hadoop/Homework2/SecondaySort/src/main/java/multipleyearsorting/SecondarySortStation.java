package multipleyearsorting;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import au.com.bytecode.opencsv.CSVParser;

// Program implements Secondary sort design pattern for Weather Data Analysis  for the given csv data.
public class SecondarySortStation {

	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();

		String otherArgs[] = new GenericOptionsParser(conf, args).getRemainingArgs();

		if (otherArgs.length < 2) {
			System.err.println("Please make sure you have added enough arguments to run the code");
			System.exit(2);
		}

		// Hadoop job configuration
		Job job = new Job(conf, "Please enter valid data");
		job.setJarByClass(SecondarySortStation.class);
		job.setMapperClass(MapperStationClass.class);
		job.setSortComparatorClass(DifferenceKey.class);
		job.setGroupingComparatorClass(DifferenceGroups.class);
		job.setReducerClass(TempratureReducer.class);
		job.setMapOutputKeyClass(YearStation.class);
		job.setMapOutputValueClass(Station.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		for (int i = 0; i < otherArgs.length - 1; ++i) {
			FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
		}
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

	// Inmapper design pattern
	public static class MapperStationClass extends Mapper<Object, Text, YearStation, Station> {
		// data stores StationId
		private Text data = new Text();
		// year will store the data year which is currently being accessed
		private IntWritable year = new IntWritable();
		private HashMap<String, Station> records;

		public void setup(Context context) {
			records = new HashMap<String, Station>();
		}

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			FileSplit fileSplit = (FileSplit) context.getInputSplit();
			String fileName = fileSplit.getPath().getName();
			fileName = fileName.replace(".csv", "");
			year = new IntWritable(Integer.parseInt(fileName));
			String stationDetail[] = new CSVParser().parseLine(value.toString());
			data.set(stationDetail[0]);

			// takes the Tmax and Tmin data
			if (stationDetail[2].equals("TMAX") || stationDetail[2].equals("TMIN")) {

				if (!records.containsKey(data.toString())) {
					// Create a new Station Object
					Station stn = new Station(data, new DoubleWritable(0.0), new DoubleWritable(0.0),
							new IntWritable(0), new IntWritable(0));

					if (stationDetail[2].equals("TMAX")) {
						stn.setTmaxTotal(new DoubleWritable(Double.parseDouble(stationDetail[3])));
						stn.setRecordCountTMAX(new IntWritable(1));
					} else if (stationDetail[2].equals("TMIN")) {
						stn.setTminTotal(new DoubleWritable(Double.parseDouble(stationDetail[3])));
						stn.setRecordCountTMIN(new IntWritable(1));
					}
					records.put(data.toString(), stn);
				}

				else {
					Station stn = records.get(data.toString());

					// Update the TMAX attributes for station object
					if (stationDetail[2].equals("TMAX")) {
						stn.setTmaxTotal(
								new DoubleWritable(stn.getTmaxTotal().get() + Double.parseDouble(stationDetail[3])));
						stn.setRecordCountTMAX(new IntWritable(stn.getRecordCountTMAX().get() + 1));
					}
					// Update the TMIN related attributes for station object
					else if (stationDetail[2].equals("TMIN")) {
						stn.setTminTotal(
								new DoubleWritable(stn.getTminTotal().get() + Double.parseDouble(stationDetail[3])));
						stn.setRecordCountTMIN(new IntWritable(stn.getRecordCountTMIN().get() + 1));
					}
					records.put(data.toString(), stn);
				}
			}

		}

		// Method just emits all the HashMap entries as output of the map with a
		// change i.e
		// (StationId,Year)
		public void cleanup(Context context) throws IOException, InterruptedException {
			for (Map.Entry<String, Station> obj : records.entrySet()) {
				context.write(new YearStation(new Text(obj.getKey()), year), obj.getValue());

			}
		}

	}

	public static class DifferenceKey extends WritableComparator {
		protected DifferenceKey() {
			super(YearStation.class, true);
		}

		@Override
		public int compare(WritableComparable w1, WritableComparable w2) {
			YearStation o1 = (YearStation) w1;
			YearStation o2 = (YearStation) w2;
			// emits difference of stationId
			int diffStationId = o1.getStationId().toString().compareTo(o2.getStationId().toString());
			// emits difference of Year
			int diffYear = o1.getYear().get() - o2.getYear().get();

			// Returns difference of stationId
			if (diffStationId == 0)
				return diffYear;
			else
				return diffStationId;
		}

	}

	public static class DifferenceGroups extends WritableComparator {
		protected DifferenceGroups() {
			super(YearStation.class, true);
		}

		@Override
		public int compare(WritableComparable w1, WritableComparable w2) {
			YearStation st1 = (YearStation) w1;
			YearStation st2 = (YearStation) w2;
			return st1.getStationId().toString().compareTo(st2.getStationId().toString());
		}

	}

	public static class TempratureReducer extends Reducer<YearStation, Station, Text, Text> {
		public void reduce(YearStation key, Iterable<Station> stationList, Context context)
				throws IOException, InterruptedException {
			int lastYear = 0;
			int thisYear = key.getYear().get();

			Double maxTempSum = 0.0;
			Double minTempSum = 0.0;
			Integer maxCount = 0;
			Integer minCount = 0;

			StringBuilder sb = new StringBuilder();
			sb.append("[");

			for (Station temp : stationList) {

				lastYear = thisYear;
				thisYear = key.getYear().get();
				// Average calculator
				if (thisYear != lastYear) {
					Double maxtempAverage = 0.0;
					Double minTempAverage = 0.0;

					if (maxCount > 0)
						maxtempAverage = maxTempSum / maxCount;
					else
						maxCount = -10;

					if (minCount > 0)
						minTempAverage = minTempSum / minCount;
					else
						minCount = -10;
					if (maxCount != -10 && minCount != -10)
						sb.append("(" + lastYear + ", " + minTempAverage + ", " + maxtempAverage + "),");

					else if (maxCount == -10)
						sb.append("(" + lastYear + ", " + minTempAverage + ", " + " No TMAX Record Found" + "),");

					else if (minCount == -10)
						sb.append("(" + lastYear + ", " + "No TMIN Record Found" + ", " + maxtempAverage + "),");

					maxTempSum = 0.0;
					minTempSum = 0.0;
					maxCount = 0;
					minCount = 0;

				}

				maxTempSum += temp.getTmaxTotal().get();
				minTempSum += temp.getTminTotal().get();
				minCount += temp.getRecordCountTMIN().get();
				maxCount += temp.getRecordCountTMAX().get();

			}

			if (thisYear == lastYear) {
				Double tmaxAverage = 0.0;
				Double tminAverage = 0.0;

				tmaxAverage = maxTempSum / maxCount;

				tminAverage = minTempSum / minCount;

				sb.append("(" + thisYear + ", " + tminAverage + ", " + tmaxAverage + ")");

			}
			sb.append("]");
			Text result = new Text();
			result.set(sb.toString());
			// Emit the StationId and result of StringBuilder;
			context.write(key.getStationId(), result);
		}
	}
}
