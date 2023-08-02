package group16.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import group16.GUI.GameModelListener;

public final class  GameModel {

    private static final GameModel INSTANCE = new GameModel();
     private  ArrayList<CardStack> card_Stacks ;//all card on the interface
    private  ArrayList<CardStack> regret_stack ;
    private Stack<ArrayList> listStack = new Stack<ArrayList>();
    private int fromIndex;
    private StockStack deck_Stack;//0
    private TalonStack disCard_Stack;//1
    private TableauStack[] table_Stacks;//2-8
    private FoundationStack[] suit_Stacks;//9-12
    private StockStack regret_deck_Stack;//0
    private TalonStack regret_disCard_Stack;//1
    private TableauStack[] regret_table_Stacks;//2-8
    private FoundationStack[] regret_suit_Stacks;//9-12
    private static final String SEPARATOR = ";";
    private String level = "low";
    private final List<GameModelListener> aListeners = new ArrayList<GameModelListener>();
    public GameModel()
    {
        init();
    }
    public void init()
    {
        this.regret_stack = new ArrayList<CardStack>();
        this.table_Stacks = new  TableauStack[7];
        for(int i = 0 ; i < table_Stacks.length ; i++)
    {
        this.table_Stacks[i] = new TableauStack();
    }
        this.deck_Stack = new StockStack();
        this.disCard_Stack = new TalonStack();
        this.suit_Stacks = new FoundationStack[4];
        for(int i = 0 ; i< suit_Stacks.length ; i++)
    {
        this.suit_Stacks[i] = new FoundationStack();
    }
        this.card_Stacks = new ArrayList<CardStack>();
        this.card_Stacks.add(this.deck_Stack);//0
        this.card_Stacks.add(this.disCard_Stack);//1
        for(int i = 0 ; i < table_Stacks.length ; i ++)
    {
        this.card_Stacks.add(this.table_Stacks[i]);//2-8
    }
        for(int i = 0 ; i < suit_Stacks.length ; i ++)
    {
        this.card_Stacks.add(this.suit_Stacks[i]);//9-12
    }
    Random random = new Random();
    ArrayList<Card> normal_Rank = new ArrayList();
    Card temp_card = null;
    // use normal_Rank save 52 cards information
        for( Foundation suit : Foundation.values() )
    {
        for( Rank rank : Rank.values() )
        {
            temp_card = new Card(rank, suit);
            normal_Rank.add(temp_card);
        }
    }
    int i;
    //Random cards assigned to tableau pile
    int index = 0;
        for( i = 0; i < 7; ++i) {
        for(int j = 0; j <= i; ++j) {
            while (true)
            {
                index = random.nextInt(normal_Rank.size());
                temp_card = (Card)normal_Rank.get(index);
                if( !this.table_Stacks[i].isEmpty() && temp_card.isRed() != this.table_Stacks[i].peek().isRed())
                {
                    this.table_Stacks[i].init(temp_card);
                    normal_Rank.remove(index);
                    break;
                }
                if(this.table_Stacks[i].isEmpty() )
                {
                    this.table_Stacks[i].init(temp_card);
                    normal_Rank.remove(index);
                    break;
                }
            }
        }
    }
    //set top card face up on each tableau subpile
        for(i = 0; i < 7; ++i) {
        this.table_Stacks[i].peek().setFaceUp(true);
    }
    //set rest card to stock pile
        for(i = 0; i < 24; ++i) {
        index = random.nextInt(normal_Rank.size());
        temp_card = (Card)normal_Rank.get(index);
        this.deck_Stack.init(temp_card);
        normal_Rank.remove(index);
    }
}
    //get cardStack
    public CardStack getStack(int index) {
        return this.card_Stacks.get(index);
    }
    //get instance
    public static GameModel instance()
    {
        return INSTANCE;
    }
    //listener to each move action
    public void addListener(GameModelListener pListener)
    {
       if(pListener != null);
        aListeners.add(pListener);
    }
    //update desktop after removing one card
    private void notifyListeners()
    {


        for( GameModelListener listener : aListeners )
        {
            listener.gameStateChanged();
        }

    }
    public void store()
    {
        this.regret_stack = new ArrayList<CardStack>();
        this.regret_table_Stacks = new  TableauStack[7];
        for(int i = 0 ; i < table_Stacks.length ; i++)
        {
            this.regret_table_Stacks[i] = new TableauStack();
        }
        this.regret_deck_Stack = new StockStack();
        this.regret_disCard_Stack = new TalonStack();
        this.regret_suit_Stacks = new FoundationStack[4];
        for(int i = 0 ; i< regret_suit_Stacks.length ; i++)
        {
            this.regret_suit_Stacks[i] = new FoundationStack();
        }
        this.regret_stack.add(this.regret_deck_Stack);//0
        this.regret_stack.add(this.regret_disCard_Stack);//1
        for(int i = 0 ; i < regret_table_Stacks.length ; i ++)
        {
            this.regret_stack.add(this.regret_table_Stacks[i]);//2-8
        }
        for(int i = 0 ; i < regret_suit_Stacks.length ; i ++)
        {
            this.regret_stack.add(this.regret_suit_Stacks[i]);//9-12
        }
        for(int i = 0 ; i< 13 ; i++)
        {
            for(int j = 0 ; j <this.card_Stacks.get(i).size() ; j++)
            {
                this.regret_stack.get(i).init(this.card_Stacks.get(i).peek(j));
            }
        }
        listStack.push(regret_stack);

    }
    //get substack of tableau pile stack
    public CardStack getSubStack(Card pCard, int aIndex)
    {
        TableauStack stack = (TableauStack) GameModel.instance().getStack(aIndex);
        TableauStack temp_stack = stack.getSubStack(pCard,stack);

            return temp_stack;

    }
    //is legalmove
    //index: pile stack index
    public boolean isLegalMove(Card pCard,int aIndex )
    {
        if(aIndex >=1 && aIndex<= 8) //card in talon pile and tableau pile can move to table stack
        {
                return canMoveToTableStack(pCard,aIndex);
        }
        else if(aIndex>= 9 && aIndex <=12)
        {
                return canMoveToSuitStack(pCard,aIndex);
        }

        return false;
    }
    //can move tableau pile
    public boolean  canMoveToTableStack(Card pCard,int aIndex)
    {
        if(level == "high")
        {
            if(pCard!=null)
            {
                CardStack temp_stack = getStack(aIndex);
                if( temp_stack.isEmpty() )
                {

                    return pCard.getRank() == Rank.KING;
                }
                else
                {
                    return pCard.getRank().ordinal() == temp_stack.peek().getRank().ordinal()-1 &&
                            pCard.isRed() != temp_stack.peek().isRed();
                }

            }
        }
        else if(level == "low")
        {

            if(pCard!=null)
            {
                CardStack temp_stack = getStack(aIndex);
                if( temp_stack.isEmpty() )
                {
                    return pCard.getRank() == Rank.KING; //lin assignment 1
                  //  return true;
                }
                else
                {
                	  return pCard.isRed() != temp_stack.peek().isRed();
                	//lin for assignment 1
                	/*
                    return pCard.getRank().ordinal() == temp_stack.peek().getRank().ordinal()-1 &&
                            pCard.isRed() != temp_stack.peek().isRed();
                            */
                }

            }
        }

        return false;
    }
    //foundations pile  index is from 9 -12
    public boolean  canMoveToSuitStack(Card pCard,int aIndex)
    {
        assert pCard != null ;
        CardStack temp_stack = getStack(aIndex);
        if(temp_stack.isEmpty())
        {
            return  pCard.getRank() == Rank.ACE ;
        }
        
        else
        {
            /*  Lin for assignment 1
             return pCard.getRank().ordinal() == temp_stack.peek().getRank().ordinal()+1 &&
                    pCard.getSuit()==temp_stack.peek().getSuit();
                    */
        	return true;
        }


    }

    public void Desc_to_DisCard()
    {
        Card temp_card = this.getStack(0).peek();
        this.getStack(1).init(temp_card);
        this.getStack(0).pop();
        notifyListeners();

    }
    public  void Desc_to_DisCard_3()
    {
        if(this.getStack(0).size()>=3)
        {
            for(int i = 0 ; i <3 ; i++)
            {
                Card temp_card = this.getStack(0).peek();
                this.getStack(1).init(temp_card);
                this.getStack(0).pop();

            }
        }
        else if(this.getStack(0).size() ==2)
        {
            for(int i = 0 ; i <2 ; i++)
            {
                Card temp_card = this.getStack(0).peek();
                this.getStack(1).init(temp_card);
                this.getStack(0).pop();
                System.out.println(temp_card .getRank());

            }
        }
        else
        {
            Card temp_card = this.getStack(0).peek();
            this.getStack(1).init(temp_card);
            this.getStack(0).pop();
        }

        notifyListeners();

    }

    //move card
        public boolean moveCard(CardStack from,int aIndex)
        {
            if
                (aIndex>=2 && aIndex<=8)
            {
                TableauStack to = (TableauStack)this.getStack(aIndex);
                if (!to.isEmpty())
                {
                    this.getStack(aIndex).push(from);
                    pop_from(from);
                    notifyListeners();
                        return true;


        } else if (from.isEmpty()) {

            return false;
        } else {

                        this.getStack(aIndex).push(from);
                        pop_from(from);
                        notifyListeners();
   }
            }
            else  if(aIndex>=9 && aIndex<= 12)
            {
                FoundationStack to = (FoundationStack) this.getStack(aIndex);
                if (to.isEmpty())
                {//get next card
                    this.getStack(aIndex).push(from);
                    pop_from(from);
                    notifyListeners();
                return true;

                 }
                 else
                     {
                         this.getStack(aIndex).push(from);
                         pop_from(from);
                         notifyListeners();
                     }
            }
            return true;
        }

    public void pop_from(CardStack to)
    {
        for(int j = 0 ; j < to.size() ; j ++)
            {

                this.getStack(fromIndex).pop(to.peek(j));

            }
            if(!this.getStack(fromIndex).peek().isFaceUp())
       {
           this.getStack(fromIndex).peek().setFaceUp(true);
       }

    }
    //if stockstack is empty, and talonstack is not empty
    public void reset_all()
    {
        init();
        notifyListeners();
    }
   
    //if stockstack is empty, but talonstack is not empty
    public void reset()
    {
        for(int i = 0 ; i < this.getStack(1).size() ; i++)
        {
            this.getStack(0).init(this.getStack(1).peek(i));
        }

           this.getStack(1).clear();

        notifyListeners();
    }
    public CardStack StringToStack(String pString)
    {

       if(pString != null && pString.length() > 0)
       {
           String[] tokens = pString.split(SEPARATOR);
           CardStack aCards = new CardStack();
           for( int i = 0; i < tokens.length; i++ )
           {
               aCards.init(Card.get(tokens[i]));
           }
           for(int i = 0 ; i<aCards.size() ; i ++)
           {
               aCards.peek(i).setFaceUp(true);
           }
           return aCards;
       }
        return null;
    }
    public Card getTop(String result)
    {
        if( result != null && result.length() > 0)
        {
            String[] tokens = result.split(SEPARATOR);
            Card aCards [];
            aCards = new Card[tokens.length];
            for( int i = 0; i < tokens.length; i++ )
            {
                aCards[i] = Card.get(tokens[i]);
            }
            return aCards[0];
        }
        return null;
    }
    public String serialize(Card pCard, int aIndex)
    {

        CardStack temp_stack =  GameModel.instance().getSubStack(pCard, aIndex);
        String result = "";
        Card temp_card ;
        for(int i = 0 ; i< temp_stack.size() ; i++)
        {
            temp_card = temp_stack.peek(i);
            result += temp_card.getIDString()+SEPARATOR;
        }

        if( result.length() > 0)
        {
            result = result.substring(0, result.length()-1);
        }

        return result;
    }


    public void setFromIndex(int fromIndex) {
        this.fromIndex = fromIndex;
    }

    public void setLevel(String level)
    {
        this.level = level;
    }
    public String getLevel()
    {
        return this.level;
    }
    public void getRegret_stack()
    {
        if(!listStack.isEmpty())
        {
            ArrayList<CardStack> temp_list = listStack.peek();
            listStack.pop();
            ArrayList<Integer> flag = new ArrayList<Integer>();
            for (int i = 2 ; i<9 ; i++)
            {
                if(this.card_Stacks.get(i).size() != temp_list.get(i).size())
                {
                    flag.add(i);
                }
            }
            if(flag.size() == 1)
            {
                if(this.card_Stacks.get(flag.get(0)).size() <temp_list.get(flag.get(0)).size() &&this.card_Stacks.get(flag.get(0)).size() >0 && temp_list.get(flag.get(0)).size() > 1)
                {
                    if(temp_list.get(flag.get(0)).peek(temp_list.get(flag.get(0)).size() - 2).isFaceUp())
                    {
                        temp_list.get(flag.get(0)).peek(temp_list.get(flag.get(0)).size()-2).setFaceUp(false);
                    }
                }

            }
            if(flag.size() ==2)
            {
                if(this.card_Stacks.get(flag.get(0)).size() -temp_list.get(flag.get(0)).size() == 1 )
                {

                    temp_list.get(flag.get(1)).peek(temp_list.get(flag.get(1)).size()-2).setFaceUp(false);

                }
                if(this.card_Stacks.get(flag.get(1)).size() -temp_list.get(flag.get(1)).size()  ==1)
                {
                    temp_list.get(flag.get(0)).peek(temp_list.get(flag.get(0)).size()-2).setFaceUp(false);
                }
                if(this.card_Stacks.get(flag.get(0)).size() - temp_list.get(flag.get(0)).size() >=2)
                {
                    int i = this.card_Stacks.get(flag.get(0)).size() - temp_list.get(flag.get(0)).size();
                    temp_list.get(flag.get(1)).peek(temp_list.get(flag.get(1)).size()-i-1).setFaceUp(false);

                }
                if(this.card_Stacks.get(flag.get(1)).size() -temp_list.get(flag.get(1)).size() >=2)
                {
                    int i = this.card_Stacks.get(flag.get(1)).size() - temp_list.get(flag.get(1)).size();
                    temp_list.get(flag.get(0)).peek(temp_list.get(flag.get(0)).size()-i-1).setFaceUp(false);

                }

            }



            for(int i = 0 ; i< 13 ; i++)
            {
                this.card_Stacks.get(i).clear();
            }
            for(int i = 0 ; i< 13 ; i++)
            {
                for(int j = 0 ; j <temp_list.get(i).size() ; j++)
                {

                    this.card_Stacks.get(i).init(temp_list.get(i).peek(j));
                }
            }
            notifyListeners();
        }
        else
        {
            return;
        }


//        ArrayList<Integer> flag = new ArrayList<Integer>();
//        for (int i = 2 ; i<9 ; i++)
//        {
//            if(this.card_Stacks.get(i).size() != this.regret_stack.get(i).size())
//            {
//                flag.add(i);
//            }
//        }
//        if(flag.size() == 1)
//        {
//            if(this.card_Stacks.get(flag.get(0)).size() <this.regret_stack.get(flag.get(0)).size() &&this.card_Stacks.get(flag.get(0)).size() >0 && this.regret_stack.get(flag.get(0)).size() > 1)
//            {
//                if(this.regret_stack.get(flag.get(0)).peek(this.regret_stack.get(flag.get(0)).size() - 2).isFaceUp())
//                {
//                    this.regret_stack.get(flag.get(0)).peek(this.regret_stack.get(flag.get(0)).size()-2).setFaceUp(false);
//                }
//            }
//
//        }
//        if(flag.size() ==2)
//        {
//            System.out.println(this.card_Stacks.get(flag.get(0)).size());
//            System.out.println(this.regret_stack.get(flag.get(0)).size());
//            if(this.card_Stacks.get(flag.get(0)).size() -this.regret_stack.get(flag.get(0)).size() == 1 )
//            {
//
//                this.regret_stack.get(flag.get(1)).peek(this.regret_stack.get(flag.get(1)).size()-2).setFaceUp(false);
//                System.out.println("1111");
//            }
//            if(this.card_Stacks.get(flag.get(1)).size() -this.regret_stack.get(flag.get(1)).size()  ==1)
//            {
//                this.regret_stack.get(flag.get(0)).peek(this.regret_stack.get(flag.get(0)).size()-2).setFaceUp(false);
//                System.out.println("2222");
//            }
//            if(this.card_Stacks.get(flag.get(0)).size() - this.regret_stack.get(flag.get(0)).size() >=2)
//            {
//                int i = this.card_Stacks.get(flag.get(0)).size() - this.regret_stack.get(flag.get(0)).size();
//                this.regret_stack.get(flag.get(1)).peek(this.regret_stack.get(flag.get(1)).size()-i-1).setFaceUp(false);
//                System.out.println("3333");
//            }
//            if(this.card_Stacks.get(flag.get(1)).size() -this.regret_stack.get(flag.get(1)).size() >=2)
//            {
//                int i = this.card_Stacks.get(flag.get(1)).size() - this.regret_stack.get(flag.get(1)).size();
//                this.regret_stack.get(flag.get(0)).peek(this.regret_stack.get(flag.get(0)).size()-i-1).setFaceUp(false);
//                System.out.println("4444");
//            }
//
//        }
//
//
//
//        for(int i = 0 ; i< 13 ; i++)
//        {
//            this.card_Stacks.get(i).clear();
//        }
//        for(int i = 0 ; i< 13 ; i++)
//        {
//            for(int j = 0 ; j <this.regret_stack.get(i).size() ; j++)
//            {
//
//                this.card_Stacks.get(i).init(this.regret_stack.get(i).peek(j));
//            }
//        }


    }

}