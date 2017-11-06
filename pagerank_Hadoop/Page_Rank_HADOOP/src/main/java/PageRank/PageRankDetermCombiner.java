package PageRank;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import main.Driver;

import java.io.IOException;
import java.util.ArrayList;

// Page Rank combiner ,it is the intermediate step between the mapper and reducer.
public class PageRankDetermCombiner extends Reducer<Text, NodePageRank, Text, NodePageRank> {

	public static final int zero = 0;

	public void reduce(Text key, Iterable<NodePageRank> values, Context context)
			throws IOException, InterruptedException {

		// Page Counter.
		long pageCount = context.getConfiguration().getLong(Driver.pageCounter, 1);

		// handling dangling node.
		// "@@@@" acts as the signature for the dangling node.
		if (key.toString().equals("@@@@")) {
			double changeSum = zero;
			for (NodePageRank value : values) {
				changeSum += value.pageRanker;
			}

			NodePageRank changeAcc = new NodePageRank();
			changeAcc.adjacencyList = new ArrayList<String>();
			changeAcc.pageRanker = changeSum;
			changeAcc.participantFlag = false;
			// checks for changes.
			context.write(key, changeAcc);
			return;
		}

		NodePageRank node = new NodePageRank();
		node.danglingNodeFlag = true;
		double participantAcc = 0.0;

		for (NodePageRank value : values) {
			// adding up participants if the node is a contributer
			if (value.participantFlag) {
				participantAcc += value.pageRanker;
			} else {
				context.write(key, value);
			}

		}
		node.pageRanker = participantAcc;
		node.participantFlag = true;
		node.adjacencyList = new ArrayList<String>();
		context.write(key, node);

	}

}
