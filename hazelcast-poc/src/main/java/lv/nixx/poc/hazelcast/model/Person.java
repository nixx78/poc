package lv.nixx.poc.hazelcast.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import lombok.*;
import lombok.experimental.Accessors;

@SuppressWarnings("serial")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of="id")
@Builder(toBuilder=true)
public class Person implements Serializable {
	
	@NonNull
	private Integer id;
	
	@NonNull
	private String name;
	
	@NonNull
	private Date age;

	private Collection<String> state;

}
