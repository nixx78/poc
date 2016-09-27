package lv.nixx.poc.gleif.processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GenericXMLParser extends DefaultHandler { 
    
	private Logger log = LoggerFactory.getLogger(GenericXMLParser.class);
	
	final StringBuilder currentValue = new StringBuilder();
	final Map<String, String> container = new HashMap<>();

	private Map<String, ElementHandler> handler = new HashMap<>();
	
	public void process(File file) throws Exception {

		long sTime = System.currentTimeMillis();
		log.info("Try to parse file [{}]", file.getName());
		
		try (InputStream is = new FileInputStream(file.getCanonicalFile());
				Reader reader = new InputStreamReader(is, "UTF-8")) {
			
			InputSource inputSource = new InputSource(reader);
			inputSource.setEncoding("UTF-8");
			
			SAXParserFactory fact = SAXParserFactory.newInstance();
			fact.setNamespaceAware(true);
			
			SAXParser parser = fact.newSAXParser();
			parser.parse(inputSource, this);
			log.info("Parse and save finished at [{}]", getFormattedDateTime(System.currentTimeMillis() - sTime));
		}
	}
	
	public void addHandlerForElement(String elementName, ElementHandler handler) {
		this.handler.put(elementName, handler);
	}
	
	private String getFormattedDateTime(long millis) {
		return String.format("%d min and %d sec", 
			    TimeUnit.MILLISECONDS.toMinutes(millis),
			    TimeUnit.MILLISECONDS.toSeconds(millis) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
			);
	}
	
	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		currentValue.append(new String(arg0, arg1, arg2));
	}

	@Override
	public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException {
		currentValue.setLength(0);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if ( handler.containsKey(localName) ) {
			ElementHandler elementProcessor = handler.get(localName);
			elementProcessor.process(container);
		}

		String v = currentValue.toString();
		if ( !v.isEmpty() ) {
			container.put(localName, v);
		}	
	}

}
