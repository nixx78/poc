package lv.nixx.poc.second.repository.beta;

import lv.nixx.poc.second.orm.beta.BetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetaRepository extends JpaRepository<BetaEntity, Long> {
}
