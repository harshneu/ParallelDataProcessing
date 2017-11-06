package patterncombiner;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

//Class accumulates Weather data as a Record
//It implements Writable since it s used as value emitted out of Mapper
public class Station implements Writable{

	private Text stationId;
	private DoubleWritable value;
	private Text type;
	private IntWritable numbers;
	
	public Station(){
		this.stationId = new Text();
		this.value = new DoubleWritable();
		this.type = new Text();
		this.numbers = new IntWritable();
	}

	
	public Station(Text stationId, DoubleWritable val, Text type, IntWritable count) {
		this.stationId = stationId;
		this.value = val;
		this.type = type;
		this.numbers = count;
	}

	public Text getStationId() {
		return stationId;
	}

	public void setStationId(Text stationId) {
		this.stationId = stationId;
	}

	public DoubleWritable getVal() {
		return value;
	}

	public void setVal(DoubleWritable val) {
		this.value = val;
	}

	public Text getType() {
		return type;
	}

	public void setType(Text type) {
		this.type = type;
	}
	
	

	public IntWritable getCount() {
		return numbers;
	}

	public void setCount(IntWritable count) {
		this.numbers = count;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		stationId.readFields(in);
		value.readFields(in);
		type.readFields(in);
		numbers.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		stationId.write(out);
		value.write(out);
		type.write(out);
		numbers.write(out);
	}
	
	@Override
	public int hashCode(){
		return stationId.hashCode();
	}

	@Override
	public String toString() {
		return "Record [stationId=" + stationId + ", value=" + value + ", type=" + type + ", numbers=" + numbers + "]";
	}

	
}

