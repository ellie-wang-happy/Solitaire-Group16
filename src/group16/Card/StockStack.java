package group16.Card;


//stock stack
public class StockStack extends CardStack {
   public void push(CardStack pCStack, int index) {
      if(pCStack != null)
      {
           for(int i = index; i >= 0; --i) {
               this.getPokers_card().add(pCStack.peek(i));
              }
      }
      pCStack.peek().setFaceUp(false);       
    }
}
