package PageRank;


import org.apache.hadoop.mapreduce.Reducer;

import main.Driver;

import org.apache.hadoop.io.Text;
import java.io.IOException;
import java.util.ArrayList;

//PreProcessor reducer 
public class PreProcessorReducer extends Reducer<Text, NodePageRank, Text, NodePageRank> {

	//reducer implementation
    public void reduce(Text key, Iterable<NodePageRank> value, Context context) throws IOException, InterruptedException{

    	// Dangling Flag initialize
        boolean danglingFlag = true;
        int count = 0;
        NodePageRank node = new NodePageRank();
        node.pageRanker = 0.0;
        node.adjacencyList = new ArrayList<String>();
        node.danglingNodeFlag = false;

        //iterating over the value list and performing actions based on whether node is dangling or not
        for(NodePageRank node1: value){

        	// compares adjacency list node with node1 adjacency list.
            if(!(node1.danglingNodeFlag)){
                node.adjacencyList = node1.adjacencyList;
                danglingFlag = false;
            }
            
        	// compares adjacency list node with node1 adjacency list.
            if((!danglingFlag && count > 1 ) || node.adjacencyList.size()>0){
                context.getCounter(Driver.globalCounter.pageCount).increment(1);
                context.write(key, node);
                return ;
            }
        }

        // emits (key ,node)
        if(danglingFlag){
            node.danglingNodeFlag = true;
            context.write(key, node);
            context.getCounter(Driver.globalCounter.pageCount).increment(1);
        }
    }

}
