package lv.nixx.poc.first.service;

import lv.nixx.poc.common.config.db.v1.AlphaDB;
import lv.nixx.poc.first.model.DataDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class DBDataService {

    private final JdbcTemplate template;

    public DBDataService(@Qualifier(AlphaDB.dataSource) DataSource alphaDataSource) {
        this.template = new JdbcTemplate(alphaDataSource);
    }

    public Collection<DataDTO> getAllFromOne() {
        return template.query("select * from ALPHA_TABLE_ONE",
                (rs, rowNum) -> new DataDTO(rs.getLong("ID"), rs.getString("MESSAGE"), rs.getObject("CREATED_AT", LocalDateTime.class))
        );
    }

    public Collection<DataDTO> getAllFromTwo() {
        return template.query("select * from ALPHA_TABLE_TWO",
                (rs, rowNum) -> new DataDTO(rs.getLong("ID"), rs.getString("MESSAGE"), rs.getObject("CREATED_AT", LocalDateTime.class))
        );
    }

}
