package lv.nixx.poc.second.orm.beta;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "BETA_TABLE", schema = "BETA")
@Data
@Accessors(chain = true)
public class BetaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MESSAGE")
    private String message;
}