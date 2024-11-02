package lv.nixx.poc.second.rest;

import lv.nixx.poc.second.orm.alpha.AlphaTableOneEntity;
import lv.nixx.poc.second.orm.alpha.AlphaTableTwoEntity;
import lv.nixx.poc.second.repository.alpha.AlphaTableOneRepository;
import lv.nixx.poc.second.repository.alpha.AlphaTableTwoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlphaDBController {

    private final AlphaTableOneRepository alphaTableOneRepository;
    private final AlphaTableTwoRepository alphaTableTwoRepository;

    @Autowired
    public AlphaDBController(AlphaTableOneRepository alphaTableOneRepository, AlphaTableTwoRepository alphaTableTwoRepository) {
        this.alphaTableOneRepository = alphaTableOneRepository;
        this.alphaTableTwoRepository = alphaTableTwoRepository;
    }

    @GetMapping("/alpha_one")
    public List<AlphaTableOneEntity> getAllFromAlphaOne() {
        return alphaTableOneRepository.findAll();
    }

    @GetMapping("/alpha_two")
    public List<AlphaTableTwoEntity> getAllFromAlphaTwo() {
        return alphaTableTwoRepository.findAll();
    }

}
