package lv.nixx.poc.first.rest;

import lv.nixx.poc.first.model.SigmaDTO;
import lv.nixx.poc.first.service.DataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/allSigma")
    public Collection<SigmaDTO> getAllASigma() {
        return dataService.findAll();
    }

}
