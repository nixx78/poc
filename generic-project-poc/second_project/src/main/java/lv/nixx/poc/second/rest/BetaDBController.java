package lv.nixx.poc.second.rest;

import lv.nixx.poc.second.orm.beta.BetaEntity;
import lv.nixx.poc.second.repository.beta.BetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BetaDBController {

    private final BetaRepository betaRepository;

    @Autowired
    public BetaDBController(BetaRepository betaRepository) {
        this.betaRepository = betaRepository;
    }

    @GetMapping("/beta")
    public List<BetaEntity> getAllFromBeta() {
        return betaRepository.findAll();
    }
}
