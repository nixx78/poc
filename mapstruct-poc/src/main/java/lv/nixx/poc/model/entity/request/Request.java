package lv.nixx.poc.model.entity.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Request {
    private String id;
    private String body;

    private final String type;
}
