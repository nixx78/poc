package lv.nixx.poc.second.orm.alpha;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "ALPHA_TABLE_ONE", schema = "ALPHA")
@Data
@Accessors(chain = true)
public class AlphaTableOneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MESSAGE")
    private String message;
}
