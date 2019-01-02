package lv.nixx.poc.hazelcast;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import lombok.*;
import lombok.experimental.Accessors;

@SuppressWarnings("serial")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of="id")
public class Person implements Serializable {
	
	@NonNull
	private Integer id;
	
	@NonNull
	private String name;
	
	@NonNull
	private Date age;
	 
	private Collection<String> state;
	
}
