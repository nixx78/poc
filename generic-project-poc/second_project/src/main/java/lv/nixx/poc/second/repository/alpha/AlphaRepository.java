package lv.nixx.poc.second.repository.alpha;

import lv.nixx.poc.second.orm.alpha.AlphaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlphaRepository extends JpaRepository<AlphaEntity, Long> {
}
