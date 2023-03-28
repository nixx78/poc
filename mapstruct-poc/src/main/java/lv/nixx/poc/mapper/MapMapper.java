package lv.nixx.poc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper
public interface MapMapper {

    @Mapping(target = "t1", source = "s1")
    @Mapping(target = "t2", source = "s2")
    @Mapping(target = "t3", source = "s3" )
    Map<String, String> map(Map<String, String> source);

}
