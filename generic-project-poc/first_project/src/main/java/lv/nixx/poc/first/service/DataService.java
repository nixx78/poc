package lv.nixx.poc.first.service;

import lv.nixx.poc.common.config.db.AlphaDB;
import lv.nixx.poc.first.model.SigmaDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Collection;

@Service
public class DataService {

    private final JdbcTemplate template;

    public DataService(@Qualifier(AlphaDB.dataSource) DataSource alphaDataSource) {
        this.template = new JdbcTemplate(alphaDataSource);
    }

    public Collection<SigmaDTO> findAll() {
        return template.query("select * from SIGMA_TABLE",
                (rs, rowNum) -> new SigmaDTO(rs.getLong("SIGMA_ID"), rs.getString("SIGMA_STRING"))
        );
    }

}
