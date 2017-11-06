package PageRank;


import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import Parser.PageParser;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

//PreProcessor Mapper implementation
public class PreProcessorMapper  extends Mapper<Object,Text,Text,NodePageRank> {

    private static Pattern regex;
    static{
        // checks for tilde condition
    	// if it exists, discards the page
        regex = Pattern.compile("^([^~|\\?]+)$");
    }
    @Override
    public void map(Object key, Text value, Context context ){
        String line = value.toString();
        int delimLoc = line.indexOf(':');
        String pageName = line.substring(0, delimLoc);
        String html = line.substring(delimLoc + 1);
        //regex matcher
        Matcher matcher = regex.matcher(pageName);
        // regex checker for matches
        if (!matcher.find()) {
            // Skip this html file if the name contains tilde
            return;
        }
        ArrayList<String> linkPageNames = new ArrayList<String>();

        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            SAXParser saxParser = spf.newSAXParser();
            
            //implements XML reader
            XMLReader xmlReader = saxParser.getXMLReader();
            //parses the page.
            xmlReader.setContentHandler(new PageParser(linkPageNames));
            xmlReader.parse(new InputSource(new StringReader(html)));
            // new node.
            NodePageRank node = new NodePageRank();

            node.pageRanker = 0.0;
            node.adjacencyList = linkPageNames;

            node.danglingNodeFlag = false;
            context.write(new Text(pageName), node);
            for(String nodeName : linkPageNames){
                NodePageRank intermediateData = new NodePageRank();
                // compares intermediate data with the adhacency list data.
                intermediateData.adjacencyList = new ArrayList<String>();
                intermediateData.danglingNodeFlag = true;
                context.write(new Text(nodeName), intermediateData);
            }

        } catch (Exception e) {
            return;
        }



    }


}
