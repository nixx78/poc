package lv.nixx.poc.gleif.data;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lv.nixx.poc.gleif.model.LeiData;

@Component
@Transactional
public class GleifDAO {
	
	@Autowired
	private GleifDataRawRepository repository;
	
	public void saveAndFlush(Collection<LeiData> leiData) {
		repository.save(leiData);
		repository.flush();
	}	

	public void save(LeiData leiData) {
		repository.save(leiData);
	}
	
	public int getUniqueLeiCount() {
		return repository.getUniqueLeiCount();
	}

}
