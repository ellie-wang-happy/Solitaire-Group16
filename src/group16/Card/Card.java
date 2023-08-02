package group16.Card;
/*
Define some attributes of the card, including suit, value, whether it is face up, etc.
 Define a constant to represent the subscript of the suit,
 and define two arrays to maintain the suit and value of the card respectively.
 */
public class Card implements Card_Interface{
 

    private  final Rank aRank;
    private  final Foundation aSuit;
    private boolean faceUp = false;
    private boolean isRed ;
    
    //Initialization of 52 cards
      private static final Card[][] CARDS = new Card[Foundation.values().length][];
      // Initialize the suit and size of each card, generate a number, 
      //so that the corresponding card can be obtained by number
    static
    {
        for( Foundation suit : Foundation.values() )
        {
            CARDS[suit.ordinal()] = new Card[Rank.values().length];
            for( Rank rank : Rank.values() )
            {
                CARDS[suit.ordinal()][rank.ordinal()] = new Card(rank, suit);
            }
        }
    }
  
    public Card(Rank pRank, Foundation pSuit)
    {
        aRank = pRank;
        aSuit = pSuit;
        if(pSuit == Foundation.HEARTS || pSuit == Foundation.DIAMONDS)
        {
            isRed = true;
        }
        else
        {
            isRed = false;
        }

    }
    //获取这张牌的编号
    public String getIDString()
    {
        return Integer.toString(getSuit().ordinal() * Rank.values().length + getRank().ordinal());
    }

    
    public boolean isFaceUp() {
        return faceUp;
    }
   
    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }
  
    public boolean isRed() {
        return isRed;
    }
   
    public Foundation getSuit() {
        return this.aSuit;
    }
    //get card
    public static Card get(String pId)
    { if(pId != null)
        {
            int id = Integer.parseInt(pId);
            return get(Rank.values()[id % Rank.values().length],
                    Foundation.values()[id / Rank.values().length]);
        }

            return null; }
    //get card according rank and foundation
    public static Card get(Rank pRank, Foundation pSuit)
    {
        if( pRank != null && pSuit != null)
        {
            return CARDS[pSuit.ordinal()][pRank.ordinal()];
        }

            return null;
    }
    //get rank
    public Rank getRank() {
        return this.aRank;
        }


}
