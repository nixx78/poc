package lv.nixx.poc.mapper;

import lv.nixx.poc.model.SourceEnum;
import lv.nixx.poc.model.TargetEnum;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

import java.util.Collection;

@Mapper
public interface EnumMapper {

    @ValueMappings({
            @ValueMapping( source = MappingConstants.NULL, target = "DEFAULT"),
            @ValueMapping(source = "SE1", target = "TE1"),
            @ValueMapping(source = "SE2", target = "TE2"),
            @ValueMapping( source = MappingConstants.ANY_REMAINING, target = "SPECIAL")
    })
    TargetEnum map(SourceEnum sourceEnum);

    Collection<TargetEnum> map(Collection<SourceEnum> sourceEnums);
}
