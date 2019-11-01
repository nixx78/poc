package lv.nixx.poc.domain.action;

import lombok.Data;

@Data
public class PersonRemoveAction extends GenericAction<String> {

    public PersonRemoveAction() {
        super(Action.REMOVE, Entity.PERSON);
    }

}
