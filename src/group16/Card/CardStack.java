package group16.Card;

import java.util.ArrayList;

/*
 The super class of thirteen card piles uses an ArrayList array to maintain the cards in each pile.
 Define common operations in all piles, such as judging whether the pile is empty, 
 adding cards, removing cards, getting the top card of the pile, etc.

 */
public   class CardStack implements CardStack_Interface{
    private ArrayList<Card> pokers_card;
    public CardStack()
    {
        pokers_card = new ArrayList<Card>();
    }
    //get list size
    public int size() {
        return this.pokers_card.size();
    }
    //get top card
    public ArrayList<Card> getPokers_card()
    {
        return this.pokers_card;
    }
    public Card peek() {
    //如果牌堆有牌则获得顶部卡牌，否则获得一张红桃A
       if(this.pokers_card.size() >0)
       {
           return this.pokers_card.get(this.pokers_card.size() - 1);
       }
       else
       {
           return new Card(Rank.ACE,Foundation.HEARTS);
       }

       }
       //add one card to top
    public void init(Card pCard)
    {
        this.pokers_card.add(pCard);
    }
    //get card
    public Card peek(int index)
    {

        if(index >= 0 && index < size())
        {
            return this.pokers_card.get(index);
        }

            return null;


    }
    //list is empty?
    public boolean isEmpty() {

        return pokers_card.size() == 0;

    }
    //push an array card
    public void push(CardStack pStack, int index) {

        for(int i = index; i < pStack.size(); i++) {
            this.pokers_card.add(pStack.peek(i));
        }

    }

    public void push(CardStack pStack) {
        for(int i = 0; i < pStack.size(); i++) {
            this.pokers_card.add(pStack.peek(i));

        }

    }
    //delete an array cards
    public void pop(Card pCard) {
        if(!isEmpty())
        {
            for(int i = 0 ; i <this.pokers_card.size() ; i++)
            {
                if(pCard.getSuit() == this.pokers_card.get(i).getSuit() &&pCard.getRank() == this.pokers_card.get(i).getRank() )
                    this.pokers_card.remove(i);
                }
        }

    }
    //delete top card

    public void pop() {
        if(!isEmpty())
            this.pokers_card.remove(pokers_card.size()-1);
        }


    public void clear() {
        this.pokers_card.clear();
    }


}
