package lv.nixx.poc.second.service;

import lv.nixx.poc.first.model.DataDTO;
import lv.nixx.poc.first.service.DBDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@SpringBootTest
class DBDataServiceTest {

    @Autowired
    DBDataService dbDataService;

    @Test
    void getDataTest() {

        Collection<DataDTO> allFromOne = dbDataService.getAllFromOne();
        Collection<DataDTO> allFromTwo = dbDataService.getAllFromTwo();

        assertAll(
                () -> assertThat(allFromOne.stream().map(DataDTO::getValue).toList()).containsExactly(
                        "A1_ONE_TEST", "A2_ONE_TEST", "A3_ONE_TEST", "A4_ONE_TEST"
                ),
                () -> assertThat(allFromTwo.stream().map(DataDTO::getValue).toList()).containsExactly(
                        "A1_TWO_TEST", "A2_TWO_TEST", "A3_TWO_TEST"
                )
        );

    }

}