package lv.nixx.poc.second.orm.beta;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "BETA_TABLE", schema = "BETA")
@Data
public class BetaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BETA_ID")
    private Long id;

    @Column(name = "BETA_STRING")
    private String betaString;
}