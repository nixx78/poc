package lv.nixx.poc.common.config.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;

public class SQLScriptUtil {

    private static final Logger log = LoggerFactory.getLogger(SQLScriptUtil.class);

    public static void execute(DataSource dataSource, String sql) {
        if (sql != null) {
            try {
                log.info("Execute SQL script from file [{}] data source", sql);
                ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource(sql));
            } catch (Exception ex) {
                throw new IllegalStateException(ex.getMessage(), ex);
            }
        }
    }
}
