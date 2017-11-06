package PageRank;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.hadoop.io.Writable;

// Node PAge rank implements the writable interface.
public class NodePageRank implements Writable {
	public static final int zero = 0;

	// Variable declaration.
	public double pageRanker;
	public ArrayList<String> adjacencyList;
	public boolean participantFlag;
	public boolean danglingNodeFlag;

	// default constructor .
	public NodePageRank() {

		pageRanker = zero;
		adjacencyList = new ArrayList<String>();
		danglingNodeFlag = true;

	}

	// write the data to the output.
	public void write(DataOutput output) throws IOException {
		output.writeDouble(pageRanker);
		output.writeInt(adjacencyList.size());
		for (int i = zero; i < adjacencyList.size(); i++) {
			output.writeUTF(adjacencyList.get(i));
		}
		output.writeBoolean(participantFlag);
		output.writeBoolean(danglingNodeFlag);
	}

	public void readFields(DataInput in) throws IOException {
		pageRanker = in.readDouble();
		// file reader reads the file till EOF.
		int len = in.readInt();
		adjacencyList = new ArrayList<String>();
		for (int i = zero; i < len; i++) {
			adjacencyList.add(in.readUTF());
		}
		participantFlag = in.readBoolean();
		danglingNodeFlag = in.readBoolean();

	}
}
