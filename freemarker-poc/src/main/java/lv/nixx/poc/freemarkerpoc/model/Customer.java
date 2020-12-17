package lv.nixx.poc.freemarkerpoc.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class Customer {

    private int id;
    private String name;
    private String surname;
    private Date dateOfBirth;

}
