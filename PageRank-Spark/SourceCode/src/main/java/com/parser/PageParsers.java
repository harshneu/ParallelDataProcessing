package com.parser;

import java.io.StringReader;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class PageParsers {
    private static final String source = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
    private static Pattern namePattern;
    private static Pattern linkPattern;
    static {
        namePattern = Pattern.compile("^([^~?]+)$");
        linkPattern = Pattern.compile("^\\..*/([^~]+)\\.html$");
    }



        private static class WikiParser extends DefaultHandler {
        private List<String> linkPageNames;
        private int count = 0;

        public WikiParser(List<String> linkPageNames) {
            super();
            this.linkPageNames = linkPageNames;
        }




        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if ("div".equalsIgnoreCase(qName) && "bodyContent".equalsIgnoreCase(attributes.getValue("id")) && count == 0) {
                count = 1;
            } else if (count > 0 && "a".equalsIgnoreCase(qName)) {
                count++;
                String link = attributes.getValue("href");
                if (link == null) {
                    return;
                }
                try {
                    link = URLDecoder.decode(link, "UTF-8");
                } catch (Exception e) {
                }
                Matcher matcher = linkPattern.matcher(link);
                if (matcher.find()) {
                    linkPageNames.add(matcher.group(1));
                }
            } else if (count > 0) {
                count++;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if (count > 0) {
                count--;
            }
        }

    }
    public static String parseLine(String line) {
        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setFeature(source, false);
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            List<String> linkPageNames = new LinkedList<String>();
            xmlReader.setContentHandler(new WikiParser(linkPageNames));

            int delimLoc = line.indexOf(':');
            int newLocation = delimLoc +1;
            String pageName = line.substring(0, delimLoc);
            String html = line.substring(newLocation);
            Matcher matcher = namePattern.matcher(pageName);

            linkPageNames.clear();
            try {
                xmlReader.parse(new InputSource(new StringReader(html)));
            } catch (Exception e) {
                linkPageNames.clear();
                return "";
            }


            pageName = pageName;
            StringBuilder sb = new StringBuilder();

            {
                sb.append(pageName);
                sb.append("\t");
                sb.append("-1.0");
                sb.append("\t");
                for(String link : linkPageNames){
                    sb.append(link + "~");
                }
            }
            return sb.toString();
        } catch (Exception e) {
        }
        return "Error";
    }
}
