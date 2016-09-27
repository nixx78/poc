package lv.nixx.poc.gleif.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.springframework.stereotype.Component;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

@Component
public class UnzipProcessor {

	public void process(@Header(Exchange.FILE_NAME) String fileName) throws Exception {
		String source = "/tmp/gleif/zip/" + fileName;
		String destination = "/tmp/gleif/unzip/";
		try {
			ZipFile zipFile = new ZipFile(source);
			zipFile.extractAll(destination);
		} catch (ZipException e) {
			throw new RuntimeException(e);
		}
	}

}
