package lv.nixx.poc.common.config.db.v2.beta;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class BetaDSActuatorConfig {

    //TODO Make this configuration more generic
    @Bean(name = BetaDB_V2.healthIndicator)
    public HealthIndicator dataSourceHealthIndicator(@Qualifier(BetaDB_V2.dataSource) DataSource dataSource) {
        return new DataSourceHealthIndicator(dataSource, "select now()");
    }

}
