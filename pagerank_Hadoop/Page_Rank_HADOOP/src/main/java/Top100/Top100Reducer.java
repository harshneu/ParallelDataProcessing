package Top100;

import java.io.IOException;
import java.util.TreeMap;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class Top100Reducer extends
        Reducer<NullWritable, Text, NullWritable, Text> {

    private TreeMap<TreeMapsObject, Text> topKRecords = new TreeMap<TreeMapsObject, Text>(new MapComparators());

    @Override

    public void reduce(NullWritable key, Iterable<Text> values,
                       Context context) throws IOException, InterruptedException {
        for (Text value : values) {
            String line = value.toString();
            int delimiterIndex = line.indexOf(',');
            String record = line.substring(0, delimiterIndex);
            double ranking = Double.parseDouble(record);

            //updates  records for top 100 pages..
            topKRecords.put(new TreeMapsObject(ranking,line), new Text(line));

            if (topKRecords.size() > 100) {
                // removes extra records.
                topKRecords.remove(topKRecords.firstKey());
            }
        }

        // Give the contents of the treemap tothe output.
        for (Text t : topKRecords.descendingMap().values()) {
            context.write(NullWritable.get(), t);
        }
    }
}
