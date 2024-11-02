package lv.nixx.poc.second.orm.alpha;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ALPHA_TABLE_TWO", schema = "ALPHA")
@Data
public class AlphaTableTwoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MESSAGE")
    private String message;
}
