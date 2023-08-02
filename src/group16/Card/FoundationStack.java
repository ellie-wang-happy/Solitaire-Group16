package group16.Card;

public class FoundationStack  extends CardStack{


    @Override
    public void push(CardStack pStack, int index) {
        super.push(pStack, index);
        if (!this.isEmpty()) {
            this.peek().setFaceUp(true);
        }
    }










}
