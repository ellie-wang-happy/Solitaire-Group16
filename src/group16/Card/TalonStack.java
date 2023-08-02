package group16.Card;

//弃牌堆
public class TalonStack extends CardStack{

    public void push(CardStack pStack, int index) {
        super.push(pStack, index);
        //add one group card,and set top card face up
      if(!this.isEmpty())
          this.peek().setFaceUp(true);
      }

}
