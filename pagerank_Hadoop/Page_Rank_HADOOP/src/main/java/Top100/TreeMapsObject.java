package Top100;


public class TreeMapsObject {

	//initialize variables.
    public double pageRanks;
    public String word;

    TreeMapsObject(){
        pageRanks = 0;
        word ="";
    }

    //assign values to the treemap object.
    TreeMapsObject(double pageRank, String word){

        this.pageRanks = pageRank;
        this.word = word;

    }
}
