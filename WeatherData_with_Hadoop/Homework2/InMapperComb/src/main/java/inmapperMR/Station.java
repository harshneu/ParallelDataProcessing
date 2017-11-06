package inmapperMR;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class Station implements Writable {
	private Text stationId;
	private DoubleWritable minTempSum;
	private DoubleWritable maxTempSum;
	private IntWritable minCount;
	private IntWritable maxCount;

	public Station() {
		stationId = new Text();
		minTempSum = new DoubleWritable();
		maxTempSum = new DoubleWritable();
		maxCount = new IntWritable();
		minCount = new IntWritable();
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		stationId.readFields(in);
		minTempSum.readFields(in);
		maxTempSum.readFields(in);
		minCount.readFields(in);
		maxCount.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		stationId.write(out);
		minTempSum.write(out);
		maxTempSum.write(out);
		minCount.write(out);
		maxCount.write(out);
	}

	public Station(Text stationId, DoubleWritable tminTotal, DoubleWritable tmaxTotal, IntWritable recordCountTMIN,
			IntWritable recordCountTMAX) {
		this.stationId = stationId;
		this.minTempSum = tminTotal;
		this.maxTempSum = tmaxTotal;
		this.minCount = recordCountTMIN;
		this.maxCount = recordCountTMAX;
	}

	public Text getStationId() {
		return stationId;
	}

	public void setStationId(Text stationId) {
		this.stationId = stationId;
	}

	public DoubleWritable getTminTotal() {
		return minTempSum;
	}

	public void setTminTotal(DoubleWritable tminTotal) {
		this.minTempSum = tminTotal;
	}

	public DoubleWritable getTmaxTotal() {
		return maxTempSum;
	}

	public void setTmaxTotal(DoubleWritable tmaxTotal) {
		this.maxTempSum = tmaxTotal;
	}

	public IntWritable getRecordCountTMIN() {
		return minCount;
	}

	public void setRecordCountTMIN(IntWritable recordCountTMIN) {
		this.minCount = recordCountTMIN;
	}

	public IntWritable getRecordCountTMAX() {
		return maxCount;
	}

	public void setRecordCountTMAX(IntWritable recordCountTMAX) {
		this.maxCount = recordCountTMAX;
	}

	@Override
	public int hashCode() {
		return stationId.hashCode();
	}

	@Override
	public String toString() {
		return "Station [stationId=" + stationId + ", minTempSum=" + minTempSum + ", maxTempSum=" + maxTempSum
				+ ", minCount=" + minCount + ", maxCount=" + maxCount + "]";
	}
}
