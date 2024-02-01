package lv.nixx.poc.second.orm.alpha;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ALPHA_TABLE", schema = "ALPHA")
@Data
public class AlphaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALPHA_ID")
    private Long id;

    @Column(name = "ALPHA_STRING")
    private String alphaString;
}
