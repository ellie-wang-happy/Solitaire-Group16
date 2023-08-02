package group16.Card;

public interface Card_Interface {

      Rank aRank = null;//rank
      Foundation aSuit = null;//foundation
      boolean faceUp = false;//face up?
      boolean isRed = false;
 //get face up
     boolean isFaceUp();
     //set faceup
     void setFaceUp(boolean faceUp);
     //is red
     boolean isRed();
     //get foundation
     Foundation getSuit();
     Rank getRank();
     String getIDString();


}
