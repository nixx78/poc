package lv.nixx.poc.second.repository.alpha;

import lv.nixx.poc.second.orm.alpha.AlphaTableTwoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlphaTableTwoRepository extends JpaRepository<AlphaTableTwoEntity, Long> {
}
