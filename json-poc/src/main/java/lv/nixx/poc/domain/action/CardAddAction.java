package lv.nixx.poc.domain.action;



public class CardAddAction extends GenericAction<String> {

    public CardAddAction() {
        super(Action.ADD, Entity.CARD);
    }
}
