package lv.nixx.poc.mapper;

import lv.nixx.poc.model.entity.request.*;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;

@Mapper
public interface RequestMapperSubclasses {

    @SubclassMapping(source = RequestEntityAlpha.class, target = AlphaRequest.class)
    @SubclassMapping(source = RequestEntityBeta.class, target = BetaRequest.class)
    Request map(RequestEntity source);

}
