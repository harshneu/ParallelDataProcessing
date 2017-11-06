package Top100;

import org.apache.hadoop.mapreduce.Mapper;

import PageRank.NodePageRank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;


public class Top100Mapper extends Mapper<Text, NodePageRank, NullWritable, Text> {

    private TreeMap<TreeMapsObject, String> top100Nodes;

    public void setup(Context con){
        //initializing Tree map . the key is an object that contains page name and page rank
        this.top100Nodes = new TreeMap<TreeMapsObject, String>(new MapComparators());
    }


    public void map(Text key, NodePageRank value, Context context)
            throws IOException, InterruptedException {
        //adding pageranks values to tree-map
        top100Nodes.put(new TreeMapsObject(value.pageRanker,key.toString()),key.toString());
        //updates records,removes extra records.
        if (top100Nodes.size() > 100) {
            top100Nodes.remove(top100Nodes.firstKey());
        }
        //takes in size for the top nodes.
        int x = top100Nodes.size();
    }

    @Override
    protected void cleanup(Context context) throws IOException,
            InterruptedException {
        for (Map.Entry<TreeMapsObject, String> node : top100Nodes.entrySet()) {
            context.write(NullWritable.get(),  new Text(node.getKey().pageRanks + "," + node.getValue()));
        }
    }

}
