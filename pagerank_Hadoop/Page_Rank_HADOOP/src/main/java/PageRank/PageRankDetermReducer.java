package PageRank;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import main.Driver;

import java.io.IOException;

// Page Rank reducer takes adjacency list.
public class PageRankDetermReducer extends Reducer<Text, NodePageRank, Text, NodePageRank> {

	private static final double PageRankModifier = 0.15;
	public double pageRankSum;

	public void setup(Context context) {
		pageRankSum = 0;
	}

	// reducer implementation
	public void reduce(Text key, Iterable<NodePageRank> values, Context context)
			throws IOException, InterruptedException {

		long pageCount = context.getConfiguration().getLong(Driver.pageCounter, 1);

		// checks the value with the false data and works in accordance to the
		// if else condition
		if (key.toString().equals("@@@@")) {
			double changeSum = 0;
			for (NodePageRank value : values) {
				changeSum += value.pageRanker;
			}

			long changeSum1 = Double.doubleToLongBits(changeSum);
			context.getCounter(Driver.globalCounter.changer).setValue(changeSum1);
			return;
		}

		NodePageRank node = new NodePageRank();
		node.danglingNodeFlag = false;
		double contributionAccumulator = 0.0;

		for (NodePageRank value : values) {
			// Page Meta data for Adjacency list
			if (!value.danglingNodeFlag) {
				node.adjacencyList = value.adjacencyList;
				continue;
			}
			// Accumulate Contributions
			contributionAccumulator += value.pageRanker;
		}
		double pageRank = (PageRankModifier / pageCount) + ((1.0 - PageRankModifier) * (contributionAccumulator));
		// Update Page rank and emit
		node.pageRanker = pageRank;
		context.write(key, node);

		pageRankSum += pageRank;

	}

	// CleanUp code.
	public void cleanup(Context context) {

		System.out.println("Total page count : " + pageRankSum);
	}

}
