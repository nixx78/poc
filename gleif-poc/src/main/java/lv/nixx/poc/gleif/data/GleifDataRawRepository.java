package lv.nixx.poc.gleif.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import lv.nixx.poc.gleif.model.LeiData;

@Repository
public interface GleifDataRawRepository extends JpaRepository<LeiData, String>{
	
	@Query(value="select count (distinct lei) from GLEIF_DATA_RAW", nativeQuery=true)
	public int getUniqueLeiCount();
}
