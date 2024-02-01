package lv.nixx.poc.first.repository.sigma;

import lv.nixx.poc.first.orm.SigmaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SigmaRepository extends JpaRepository<SigmaEntity, Long> {
}
