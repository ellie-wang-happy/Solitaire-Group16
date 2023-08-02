package group16.GUI;

import group16.Card.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
public class TableauPileView extends StackPane implements GameModelListener
{
    private static final int PADDING = 5;
    private static final int Y_OFFSET = 17;
    private static final String SEPARATOR = ";";
    private int aIndex;
    private static final String BORDER_STYLE = "-fx-border-color: lightgray;"
            + "-fx-border-width: 2.8;" + " -fx-border-radius: 10.0";
    TableauPileView(TableauPile pIndex)
    {
        aIndex = pIndex.ordinal()+2;//返回枚举类的序数  2-8
        setPadding(new Insets(PADDING));
        setStyle(BORDER_STYLE);
        setAlignment(Pos.TOP_CENTER);
        final ImageView image = new ImageView(CardImages.getBack());
        image.setVisible(false);
        getChildren().add(image);
        buildLayout();
        GameModel.instance().addListener(this);

    }
    private void buildLayout()
    {
        getChildren().clear();
        TableauStack stack = (TableauStack) GameModel.instance().getStack(aIndex);
        if( stack.isEmpty() )
        {
            ImageView image = new ImageView(CardImages.getBack());
            image.setVisible(false);
            getChildren().add(image);
            return;
        }
        for(int i = 0 ; i< stack.size() ; i ++)
        {
            Card cardView = stack.peek(i);
            if(cardView.isFaceUp() == true)
            { final ImageView image = new ImageView(CardImages.getCard(cardView));
                image.setTranslateY(Y_OFFSET * i);
                getChildren().add(image);
                setOnDragOver(createDragOverHandler(image, cardView));//当你拖动到目标上方的时候，会不停的执行。
                setOnDragEntered(createDragEnteredHandler(image, cardView));// 当你拖动到目标控件的时候，会执行这个事件回调。
                setOnDragExited(createDragExitedHandler(image, cardView));//当你拖动移出目标控件的时候，执行这个操作。
                setOnDragDropped(createDragDroppedHandler(image, cardView));//当你拖动到目标并松开鼠标的时候，执行这个DragDropped事件。
                image.setOnDragDetected(createDragDetectedHandler(image, cardView));//当你从一个Node上进行拖动的时候，会检测到拖动操作，将会执行这个EventHandler。
            }
            else

            {
                final ImageView image = new ImageView(CardImages.getBack());
                image.setTranslateY(Y_OFFSET * i);
                getChildren().add(image);
            }


        }
    }

    private EventHandler<MouseEvent> createDragDetectedHandler(final ImageView pImageView, final Card pCard)
    {//当你从一个Node上进行拖动的时候，会检测到拖动操作，将会执行这个EventHandler。
        return new EventHandler<MouseEvent>()

        {
            @Override
            public void handle(MouseEvent pMouseEvent)
            {
                Dragboard db = pImageView.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(GameModel.instance().serialize(pCard,aIndex));
                db.setContent(content);
                pMouseEvent.consume();
                GameModel.instance().setFromIndex(aIndex);

            }
        };
    }

    private EventHandler<DragEvent> createDragOverHandler(final ImageView pImageView, final Card pCard)
    {
        //当你拖动到目标上方的时候，会不停的执行。
        return new EventHandler<DragEvent>()
        {
            @Override
            public void handle(DragEvent pEvent)
            {
                if(pEvent.getGestureSource() != pImageView && pEvent.getDragboard().hasString())
                {

                    if( GameModel.instance().isLegalMove(GameModel.instance().getTop(pEvent.getDragboard().getString()), aIndex) )
                    {
                        pEvent.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                pEvent.consume();
            }
        };
    }

    private EventHandler<DragEvent> createDragEnteredHandler(final ImageView pImageView, final Card pCard)
    {
        // 当你拖动到目标控件的时候，会执行这个事件回调。
        return new EventHandler<DragEvent>()
        {
            @Override
            public void handle(DragEvent pEvent)
            {
                if( GameModel.instance().isLegalMove(GameModel.instance().getTop(pEvent.getDragboard().getString()), aIndex) )
                {
                    pImageView.setEffect(new DropShadow());


                }
                pEvent.consume();
            }
        };
    }

    private EventHandler<DragEvent> createDragExitedHandler(final ImageView pImageView, final Card pCard)
    {
        //当你拖动移出目标控件的时候，执行这个操作。
        return new EventHandler<DragEvent>()
        {
            @Override
            public void handle(DragEvent pEvent)
            {
                pImageView.setEffect(null);
                pEvent.consume();
            }
        };
    }

    private EventHandler<DragEvent> createDragDroppedHandler(final ImageView pImageView, final Card pCard)
    {
        //当你拖动到目标并松开鼠标的时候，执行这个DragDropped事件。
        return new EventHandler<DragEvent>()
        {
            @Override
            public void handle(DragEvent pEvent)
            {
                Dragboard db = pEvent.getDragboard();
                boolean success = false;
                if(db.hasString())
                {
                    GameModel.instance().store();
                   boolean a =  GameModel.instance().moveCard(GameModel.instance().StringToStack(db.getString()), aIndex);
                    System.out.println(a);
                    success = true;
                    ClipboardContent content = new ClipboardContent();
                    content.putString(null);
                    db.setContent(content);
                    }
                pEvent.setDropCompleted(success);
                pEvent.consume();
            }
        };
    }
    //获取移动时最顶端的卡片

    public  void gameStateChanged()
    {
       buildLayout();
    }
}