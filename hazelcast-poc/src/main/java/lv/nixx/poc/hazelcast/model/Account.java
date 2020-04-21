package lv.nixx.poc.hazelcast.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Account implements Serializable {

    private String accountId;
    private String name;

}
