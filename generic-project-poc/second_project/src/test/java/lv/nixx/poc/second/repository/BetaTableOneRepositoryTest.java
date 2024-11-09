package lv.nixx.poc.second.repository;

import lv.nixx.poc.second.orm.beta.BetaEntity;
import lv.nixx.poc.second.repository.beta.BetaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BetaTableOneRepositoryTest {

    @Autowired
    BetaRepository betaRepository;

    @BeforeEach
    void cleanup() {
        betaRepository.deleteAll();
    }

    @Test
    void findAllTest() {

        betaRepository.saveAll(
                List.of(
                        new BetaEntity().setMessage("Message1"),
                        new BetaEntity().setMessage("Message2"),
                        new BetaEntity().setMessage("Message3"),
                        new BetaEntity().setMessage("Message4")
                )
        );

        List<BetaEntity> all = betaRepository.findAll();
        assertEquals(4, all.size());
    }

}