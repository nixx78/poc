package lv.nixx.poc.second.repository;

import lv.nixx.poc.second.orm.alpha.AlphaTableOneEntity;
import lv.nixx.poc.second.repository.alpha.AlphaTableOneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AlphaTableOneRepositoryTest {

    @Autowired
    AlphaTableOneRepository alphaTableOneRepository;

    @BeforeEach
    void cleanup() {
        alphaTableOneRepository.deleteAll();
    }

    @Test
    void findAllTest() {

        alphaTableOneRepository.saveAll(
                List.of(
                        new AlphaTableOneEntity().setMessage("Message1"),
                        new AlphaTableOneEntity().setMessage("Message2"),
                        new AlphaTableOneEntity().setMessage("Message3")
                )
        );

        List<AlphaTableOneEntity> all = alphaTableOneRepository.findAll();
        assertEquals(3, all.size());
    }



}