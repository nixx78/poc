package lv.nixx.poc.first.rest;

import lv.nixx.poc.first.orm.SigmaEntity;
import lv.nixx.poc.first.repository.sigma.SigmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataController {

    private final SigmaRepository sigmaRepository;
    @Autowired
    public DataController(SigmaRepository sigmaRepository) {
        this.sigmaRepository = sigmaRepository;
    }
    @GetMapping("/allSigma")
    public List<SigmaEntity> getAllASigma() {
        return sigmaRepository.findAll();
    }

}
