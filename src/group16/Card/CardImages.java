package group16.Card;

import javafx.scene.image.Image;

public class CardImages {
    //get card image

    public static Image getCard(Card pCard)
    {
        if(pCard != null)
        {
            Image image = new Image(CardImages.class.getResourceAsStream("../resources/"+pCard.getSuit()+pCard.getRank()+".gif"));
            return image;
        }

        return null;


    }

    public static Image getBack()
    {
        Image image = new Image(CardImages.class.getResourceAsStream("../resources/back.jpg"));
        return image;
    }



}
