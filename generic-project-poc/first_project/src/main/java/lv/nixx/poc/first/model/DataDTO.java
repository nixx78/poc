package lv.nixx.poc.first.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
public class DataDTO {
    private Long id;
    private String value;
    private LocalDateTime timestamp;
}
