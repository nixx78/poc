package lv.nixx.poc.first.rest;

import lv.nixx.poc.first.model.DataDTO;
import lv.nixx.poc.first.service.DBDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class DBDataController {

    private final DBDataService DBDataService;

    public DBDataController(DBDataService DBDataService) {
        this.DBDataService = DBDataService;
    }

    @GetMapping("/allFromOne")
    public Collection<DataDTO> getAllFromOne() {
        return DBDataService.getAllFromOne();
    }

    @GetMapping("/allFromTwo")
    public Collection<DataDTO> getAllFromTwo() {
        return DBDataService.getAllFromTwo();
    }

}
