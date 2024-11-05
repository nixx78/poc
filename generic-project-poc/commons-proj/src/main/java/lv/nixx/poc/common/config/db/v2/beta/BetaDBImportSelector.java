package lv.nixx.poc.common.config.db.v2.beta;

import lv.nixx.poc.common.config.db.v2.GenericDBImportSelector;

public class BetaDBImportSelector extends GenericDBImportSelector {

    public BetaDBImportSelector() {
        super(BetaDB_V2.class, BetaJPAConfig.class, BetaDBConfig.class, BetaDSActuatorConfig.class);
    }

    @Override
    public void setPackagesToConfig(String[] packagesToScan) {
        BetaJPAConfig.setAnnotationParameters(packagesToScan);
    }

}
