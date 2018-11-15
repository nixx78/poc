package lv.nixx.poc.hazelcast;

import java.io.Serializable;
import java.util.Date;

import lombok.*;
import lombok.experimental.Accessors;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of="id")
public class Person implements Serializable {
	
	private int id;
	private String name;
	private Date age;
	
}
