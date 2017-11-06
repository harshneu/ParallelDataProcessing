package PageRank;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;

// Page rank reducer.
public class PreProcessorCombiner extends Reducer<Text, NodePageRank, Text, NodePageRank> {

	// page rank reducer implementation.
	public void reduce(Text key, Iterable<NodePageRank> value, Context context)
			throws IOException, InterruptedException {

		// variable declaration
		boolean danglingFlag = true;
		NodePageRank node = new NodePageRank();
		node.pageRanker = 0.0;
		node.adjacencyList = new ArrayList<String>();
		node.danglingNodeFlag = false;
		// Iterating over value list and performing appropriate action based on
		// nature of node

		// checks for all values in node
		// if ode nadjacency list == the node1 in adjacency list
		for (NodePageRank node1 : value) {
			if (!(node1.danglingNodeFlag)) {
				node.adjacencyList = node1.adjacencyList;
				danglingFlag = false;
			}
			if (!danglingFlag || node.adjacencyList.size() > 0) {
				context.write(key, node);
				return;
			}
		}
		// emits(key and node)
		if (danglingFlag) {
			node.danglingNodeFlag = true;
			context.write(key, node);
		}
	}

}
