package Top100;

import java.util.Comparator;


public class MapComparators implements Comparator<TreeMapsObject> {

    //Custom comparator
    public int compare(TreeMapsObject treemap1, TreeMapsObject treemap2) {
        if(treemap1.pageRanks>treemap2.pageRanks){
            return 1;
        }else if(treemap1.pageRanks<treemap2.pageRanks){
            return -1;
        }else{
           return  (treemap1.word.compareTo(treemap2.word));
        }

    }
}
