package lv.nixx.poc.mapper;

import lv.nixx.poc.model.entity.request.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RequestMapperTest {

    private final RequestMapper requestMapper = Mappers.getMapper(RequestMapper.class);
    private final RequestMapperSubclasses requestMapperSubclasses = Mappers.getMapper(RequestMapperSubclasses.class);

    @Test
    void alphaRequestMappingTest() {

        RequestEntityAlpha requestEntityAlpha = new RequestEntityAlpha();
        requestEntityAlpha.setId("id1");
        requestEntityAlpha.setBody("BodyAlpha");

        Request r = requestMapper.map(requestEntityAlpha);

        AlphaRequest alphaRequest = new AlphaRequest("AlphaRequestType");
        alphaRequest.setId("id1");
        alphaRequest.setBody("BodyAlpha");

        assertThat(r).usingRecursiveComparison()
                .isEqualTo(alphaRequest);
    }

    @Test
    void betaRequestMappingTest() {

        RequestEntityBeta requestEntityBeta = new RequestEntityBeta();
        requestEntityBeta.setId("id2");
        requestEntityBeta.setBody("BodyBeta");

        Request r = requestMapper.map(requestEntityBeta);

        BetaRequest betaRequest = new BetaRequest("BetaRequestType");
        betaRequest.setId("id2");
        betaRequest.setBody("BodyBeta");

        assertThat(r).usingRecursiveComparison()
                .isEqualTo(betaRequest);
    }

    @Test
    void betaRequestMappingSubclassesTest() {

        RequestEntityBeta requestEntityBeta = new RequestEntityBeta();
        requestEntityBeta.setId("id2");
        requestEntityBeta.setBody("BodyBeta");

        Request r = requestMapperSubclasses.map(requestEntityBeta);

        BetaRequest betaRequest = new BetaRequest(null);
        betaRequest.setId("id2");
        betaRequest.setBody("BodyBeta");

        assertThat(r).usingRecursiveComparison()
                .isEqualTo(betaRequest);
    }

}
