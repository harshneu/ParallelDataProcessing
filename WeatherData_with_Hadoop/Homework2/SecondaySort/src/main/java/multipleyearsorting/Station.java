package multipleyearsorting;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
//Class accumulates weather data records as Station for single StationId
// It implements Writable since it is emitted as value from Map

// The class gathers the tupples in the weather data for a
// given station and emits the value from the map.
public class Station implements Writable {
	// Unique StationId of a Station
	private Text stationId;
	// Maintains the total value of TMIN temperatures
	private DoubleWritable minTempTotal;
	// Maintains the total value of TMAX temperatures
	private DoubleWritable maxTemptotal;
	// Maintains the record count of type TMIN
	private IntWritable minTempCount;
	// Maintains the record count of type TMAX
	private IntWritable maxTempCount;

	public Station() {
		stationId = new Text();
		minTempTotal = new DoubleWritable();
		maxTemptotal = new DoubleWritable();
		maxTempCount = new IntWritable();
		minTempCount = new IntWritable();
	}

	public Station(Text stationId, DoubleWritable tminTotal, DoubleWritable tmaxTotal, IntWritable recordCountTMIN,
			IntWritable recordCountTMAX) {
		this.stationId = stationId;
		this.minTempTotal = tminTotal;
		this.maxTemptotal = tmaxTotal;
		this.minTempCount = recordCountTMIN;
		this.maxTempCount = recordCountTMAX;
	}

	public Text getStationId() {
		return stationId;
	}

	public void setStationId(Text stationId) {
		this.stationId = stationId;
	}

	public DoubleWritable getTminTotal() {
		return minTempTotal;
	}

	public void setTminTotal(DoubleWritable tminTotal) {
		this.minTempTotal = tminTotal;
	}

	public DoubleWritable getTmaxTotal() {
		return maxTemptotal;
	}

	public void setTmaxTotal(DoubleWritable tmaxTotal) {
		this.maxTemptotal = tmaxTotal;
	}

	public IntWritable getRecordCountTMIN() {
		return minTempCount;
	}

	public void setRecordCountTMIN(IntWritable recordCountTMIN) {
		this.minTempCount = recordCountTMIN;
	}

	public IntWritable getRecordCountTMAX() {
		return maxTempCount;
	}

	public void setRecordCountTMAX(IntWritable recordCountTMAX) {
		this.maxTempCount = recordCountTMAX;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		stationId.readFields(in);
		minTempTotal.readFields(in);
		maxTemptotal.readFields(in);
		minTempCount.readFields(in);
		maxTempCount.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		stationId.write(out);
		minTempTotal.write(out);
		maxTemptotal.write(out);
		minTempCount.write(out);
		maxTempCount.write(out);
	}

	@Override
	public int hashCode() {
		return stationId.hashCode();
	}

	@Override
	public String toString() {
		return "Station [stationId=" + stationId + ", minTempTotal=" + minTempTotal + ", maxTemptotal=" + maxTemptotal
				+ ", minTempCount=" + minTempCount + ", maxTempCount=" + maxTempCount + "]";
	}

}
