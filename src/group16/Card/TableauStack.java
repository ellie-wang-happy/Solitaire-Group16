package group16.Card;

//tableau stack
public class TableauStack extends CardStack {


    public int  getCardIndex(Rank pRank, Foundation pSuit) {

        if(!this.isEmpty())
        {
            for(int i = this.size() -1 ; i >= 0 ; i--)
            {
                if(this.peek(i).getRank() == pRank && this.peek(i).getSuit() == pSuit)
                        return i;
            }
        }

        return -1;
    }
    public TableauStack getSubStack(Card pCard, TableauStack stack)
    {
        if(pCard != null)
        {
            int index = stack.getCardIndex(pCard.getRank(),pCard.getSuit());
            TableauStack temp_stack = new TableauStack();
            for(int i = index ; i< stack.size() ; i++)
            {
                temp_stack.init(stack.peek(i));

            }
            return temp_stack;

        }
        return null;
    }



}
