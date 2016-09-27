package lv.nixx.poc.gleif.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "GLEIF_DATA_RAW")
public class LeiData {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @Column(name="LEI")
	private String lei;
	
    @Column(name="LEGAL_NAME")
	private String legalName;
	
    @Column(name="LOU_LEI")
	private String louLei;
	
	@Column(name="LAST_UPDATE_DATE")
	private Date lastUpdateDate;
	
}
