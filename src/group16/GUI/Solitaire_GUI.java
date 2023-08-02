package group16.GUI;

//import com.sun.java_cup.internal.runtime.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

import group16.Card.GameModel;
import group16.Card.Foundation;

public class Solitaire_GUI extends Application {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;
    private static final int MARGIN_OUTER = 15;
    private static final String TITLE = "Solitaire";
    private StcokPileView aDeckView = new StcokPileView();// stock pile 
    private Restart restart = new Restart();
   // private GameMode mode_selection = new GameMode();
    private Regret_game regret_game = new Regret_game();
    private TalonPileView aDiscardPileView = new TalonPileView();// talon pile
    private FoundationsPileView[] aSuitStacks = new FoundationsPileView[Foundation.values().length];
    private TableauPileView[] aStacks = new TableauPileView[TableauPile.values().length];
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage pPrimaryStage)
    {
        pPrimaryStage.setTitle(TITLE );//title
        GridPane grid = new GridPane();//layout
        grid.setId("pane");
        grid.setHgap(MARGIN_OUTER);
        grid.setVgap(MARGIN_OUTER);
        grid.setPadding(new Insets(MARGIN_OUTER));
        grid.add(aDeckView, 1, 0);
        grid.add(aDiscardPileView, 2, 0);
        grid.add(restart,8,9);//reset
        grid.add(regret_game,6,9);//regret

        for( FoundationsPile index : FoundationsPile.values() )
        {
            aSuitStacks[index.ordinal()] = new FoundationsPileView(index);
            grid.add(aSuitStacks[index.ordinal()], 4+index.ordinal(), 0);
        }
        for( TableauPile index : TableauPile.values() )
        {
            aStacks[index.ordinal()] = new TableauPileView(index);
            grid.add(aStacks[index.ordinal()], index.ordinal()+1, 1);

        }
        if(GameModel.instance().getStack(9).size() == 13 &&GameModel.instance().getStack(10).size() == 13&&GameModel.instance().getStack(11).size() == 13&&GameModel.instance().getStack(12).size() == 13)
        {
            Text t = new Text();
            t.setText("Game Pass!");
            t.setFont(Font.font ("Verdana"));
            t.setFill(Color.BLACK);

            grid.add(t,4,9);
        }
        pPrimaryStage.setResizable(false);

        Scene scene = new Scene(grid, WIDTH, HEIGHT);
        scene.getStylesheets().addAll(this.getClass().getResource("back.css").toExternalForm());

        pPrimaryStage.setScene( scene);

        pPrimaryStage.show();
    }
}
