package group16.Card;

import java.util.ArrayList;

public interface CardStack_Interface {
    // store card

    ArrayList<Card> pokers_card = null;

    //get list size

     int size();
    ArrayList<Card> getPokers_card();
    void init(Card pCard);
    //get top card
     Card peek();

    //get card according the index
     Card peek(int index);

    //list is empty?
     boolean isEmpty();
     //push an array card
//    void push(CardStack pStack, int index);

    //delete an array card
    void pop(Card pCard);


    //remove top card
    void pop();
  void clear();
}
