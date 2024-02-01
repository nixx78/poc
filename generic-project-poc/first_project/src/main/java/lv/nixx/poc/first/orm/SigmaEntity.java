package lv.nixx.poc.first.orm;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "SIGMA_TABLE", schema = "ALPHA")
@Data
public class SigmaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SIGMA_ID")
    private Long id;

    @Column(name = "SIGMA_STRING")
    private String sigmaString;
}
