package multipleyearsorting;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

// Class takes station and data from  multiple years for the mapper
public class YearStation implements WritableComparable {
	private Text stationId;
	private IntWritable year;

	public YearStation() {
		this.stationId = new Text();
		this.year = new IntWritable();
	}

	public YearStation(Text stationId, IntWritable year) {
		super();
		this.stationId = stationId;
		this.year = year;
	}

	public Text getStationId() {
		return stationId;
	}

	public void setStationId(Text stationId) {
		this.stationId = stationId;
	}

	public IntWritable getYear() {
		return year;
	}

	public void setYear(IntWritable year) {
		this.year = year;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		stationId.readFields(in);
		year.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		stationId.write(out);
		year.write(out);
	}

	@Override
	public int hashCode() {
		return stationId.hashCode() + year.get();
	}

	// yearwise sorting.
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		YearStation o1 = this;
		YearStation o2 = (YearStation) o;
		// Get the difference of stationId
		int diffStationId = o1.getStationId().toString().compareTo(o2.getStationId().toString());
		// Get the difference of Year
		int diffYear = o1.getYear().get() - o2.getYear().get();
		if (diffStationId == 0)
			return diffYear;
		else
			return diffStationId;
	}

}
