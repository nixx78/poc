package lv.nixx.poc.domain.action;

import lombok.Data;

@Data
public class GenericAction<T> {

    private Action action;
    private Entity entity;
    private T data;

    public GenericAction() {
    }

    protected GenericAction(Action action, Entity entity) {
        this.action = action;
        this.entity = entity;
    }


}
