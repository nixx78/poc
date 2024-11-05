package lv.nixx.poc.common.config.db.v2.alpha;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class AlphaDSActuatorConfig {

    @Bean(name = AlphaDB_V2.dataSource + "HealthIndicator")
    public HealthIndicator dataSourceHealthIndicator(@Qualifier(AlphaDB_V2.dataSource) DataSource dataSource) {
        return new DataSourceHealthIndicator(dataSource, "select now()");
    }

}
