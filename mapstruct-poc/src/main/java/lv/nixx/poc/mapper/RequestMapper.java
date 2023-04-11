package lv.nixx.poc.mapper;

import lv.nixx.poc.model.entity.request.*;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

@Mapper
public interface RequestMapper {

    Request map(RequestEntity source);

    @ObjectFactory
    default Request createRequest(RequestEntity entity) {
        if (entity instanceof RequestEntityAlpha) {
            return new AlphaRequest("AlphaRequestType");
        } else if (entity instanceof RequestEntityBeta) {
            return new BetaRequest("BetaRequestType");
        }
        return null;
    }

}
