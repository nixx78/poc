package lv.nixx.poc.second.rest;

import lv.nixx.poc.second.orm.alpha.AlphaEntity;
import lv.nixx.poc.second.orm.beta.BetaEntity;
import lv.nixx.poc.second.repository.alpha.AlphaRepository;
import lv.nixx.poc.second.repository.beta.BetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataController {

    private final AlphaRepository alphaRepository;
    private final BetaRepository betaRepository;

    @Autowired
    public DataController(AlphaRepository alphaRepository, BetaRepository betaRepository) {
        this.alphaRepository = alphaRepository;
        this.betaRepository = betaRepository;
    }

    @GetMapping("/service")
    public String getHello() {
        return "Hello: " + System.currentTimeMillis();
    }

    @GetMapping("/allAlpha")
    public List<AlphaEntity> getAllAlpha() {
        return alphaRepository.findAll();
    }

    @GetMapping("/allBeta")
    public List<BetaEntity> getAllBeta() {
        return betaRepository.findAll();
    }
}
