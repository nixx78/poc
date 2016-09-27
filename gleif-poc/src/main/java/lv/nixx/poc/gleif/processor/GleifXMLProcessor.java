package lv.nixx.poc.gleif.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lv.nixx.poc.gleif.data.GleifDAO;
import lv.nixx.poc.gleif.model.GleifData;
import lv.nixx.poc.gleif.model.LeiData;

@Component("gleifModelCreator")
public class GleifXMLProcessor {

	private Logger log = org.slf4j.LoggerFactory.getLogger(GleifXMLProcessor.class);
	private static final int BATCH_SIZE = 10000;

	@Autowired
	private GleifDAO dao;

	public GleifData process(File file) throws Exception {

		final List<LeiData> leiDataContainer = new ArrayList<>(BATCH_SIZE);
		final GleifData model = new GleifData();

		GenericXMLParser parser = new GenericXMLParser();
		parser.addHandlerForElement("LEIRecord", new ElementHandler() {
			@Override
			public void process(Map<String, String> element) {
				LeiData leiData = new LeiData();
				leiData.setLei(element.get("LEI"));
				leiData.setLegalName(element.get("LegalName"));
				leiData.setLouLei(element.get("ManagingLOU"));

				leiDataContainer.add(leiData);
				if (leiDataContainer.size() % BATCH_SIZE == 0) {
					dao.saveAndFlush(leiDataContainer);
					leiDataContainer.clear();
				}
				model.addLeiCount();
			}
		});

		parser.addHandlerForElement("LOUSource", new ElementHandler() {
			@Override
			public void process(Map<String, String> element) {
				model.addLouCount();
			}
		});

		parser.process(file);
		
		dao.saveAndFlush(leiDataContainer);
		log.info("Unique LEI count [{}]", dao.getUniqueLeiCount());
		
		return model;
	}

}
