package lv.nixx.poc.common.config.db.v2.alpha;

import lv.nixx.poc.common.config.db.v2.GenericDBImportSelector;

public class AlphaDBImportSelector extends GenericDBImportSelector {

    public AlphaDBImportSelector() {
        super(AlphaDB_V2.class, AlphaJPAConfig.class, AlphaDBConfig.class, AlphaDSActuatorConfig.class);
    }

    @Override
    public void setPackagesToConfig(String[] packagesToScan) {
        AlphaJPAConfig.setAnnotationParameters(packagesToScan);
    }

}
