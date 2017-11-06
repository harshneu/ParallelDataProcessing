package PageRank;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import main.Driver;

import java.io.IOException;
import java.util.ArrayList;

//Page rank Mapper ,works with keys and value.
public class PageRankDetermMapper extends Mapper<Text, NodePageRank, Text, NodePageRank> {
	public static final int zero = 0;
	public static final double pageFactor = 0.85;
	public static final double one = 1.0;

	@Override
	public void map(Text key, NodePageRank value, Context context) throws IOException, InterruptedException {

		// initialize variables
		int pageCounter = context.getConfiguration().getInt(Driver.pageCounter, 1);
		boolean iterator = context.getConfiguration().getBoolean(Driver.iterator, false);
		double increment = context.getConfiguration().getDouble(Driver.change, zero);
		double pageRankForm = zero;

		// page rank value for first iteration.
		if (iterator) {
			pageRankForm = one / (pageCounter);
		} else {
			// computing dangling node adjustment
			pageRankForm = (value.pageRanker + pageFactor * (increment / pageCounter));
		}
		value.pageRanker = pageRankForm;
		context.write(key, value);

		// IUses adjacency list and iterates over it to determine the page rank
		for (String name : value.adjacencyList) {
			NodePageRank nodeCount = new NodePageRank();
			nodeCount.participantFlag = true;
			nodeCount.adjacencyList = new ArrayList<String>();
			nodeCount.pageRanker = pageRankForm / value.adjacencyList.size();
			context.write(new Text(name), nodeCount);
		}

		// Handling dangling nodes
		if (value.adjacencyList.size() == zero) {
			NodePageRank nodeCounter = new NodePageRank();
			nodeCounter.adjacencyList = new ArrayList<String>();
			if (iterator) {
				// calculates page ranks
				nodeCounter.pageRanker = one / pageCounter;
			} else {
				nodeCounter.pageRanker = value.pageRanker + pageFactor * (increment / pageCounter);
			}
			// false data "@@@@"
			context.write(new Text("@@@@"), nodeCounter);
		}

	}
}
