/*******************************************************************************
 * Solitaire
 *
 * Copyright (C) 2016 by Martin P. Robillard
 *
 * See: https://github.com/prmr/Solitaire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package group16.GUI;

import group16.Card.CardImages;
import group16.Card.GameModel;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
  *stock pile view
 */
class StcokPileView extends HBox implements GameModelListener
{
	private static final String BUTTON_STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";
    private static final String BUTTON_STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 6 4 4 6;";
    private static final int IMAGE_NEW_LINE_WIDTH = 10;
    private static final int IMAGE_FONT_SIZE = 15;
//	private static CardImages cardImages = new CardImages();
	StcokPileView()
	{
		final Button button = new Button();
		button.setGraphic(new ImageView(CardImages.getBack()));
//		button.setGraphic(new ImageView(new Image(CardImages.class.getResourceAsStream("../resources/777.jpg"))));
		button.setStyle(BUTTON_STYLE_NORMAL);

		button.setOnMousePressed(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent pEvent)
			{
				((Button)pEvent.getSource()).setStyle(BUTTON_STYLE_PRESSED);
			}
		});

		button.setOnMouseReleased(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent pEvent)
			{

				((Button)pEvent.getSource()).setStyle(BUTTON_STYLE_NORMAL);
				if( GameModel.instance().getStack(0).isEmpty() )
				{
					if ( GameModel.instance().getStack(1).isEmpty())
					{
						GameModel.instance().reset_all();
					}
					else
					{
						GameModel.instance().reset();
					}

				}
				else
				{
					GameModel.instance().store();

					if(GameModel.instance().getLevel() == "high")
					{

						GameModel.instance().Desc_to_DisCard();
					}
					if(GameModel.instance().getLevel() == "low")
					{
						GameModel.instance().Desc_to_DisCard_3();
					}

				}
			}
		});

		getChildren().add(button);
		GameModel.instance().addListener(this);
	}
	
	private Canvas createNewGameImage()
	{
		double width = CardImages.getBack().getWidth();
		double height = CardImages.getBack().getHeight();
		Canvas canvas = new Canvas( width, height );
		GraphicsContext context = canvas.getGraphicsContext2D();
		context.setFill(Color.DARKKHAKI);
		context.setLineWidth(IMAGE_NEW_LINE_WIDTH);
		context.strokeOval(width/4, height/2-width/4 + IMAGE_FONT_SIZE, width/2, width/2);
		context.setTextAlign(TextAlignment.CENTER);
		context.setTextBaseline(VPos.CENTER);
		context.setFill(Color.DARKKHAKI);
		context.setFont(Font.font(Font.getDefault().getName(), IMAGE_FONT_SIZE));
		context.setTextAlign(TextAlignment.CENTER);
		return canvas;
	}



	@Override
	public void gameStateChanged() {
		
		if( GameModel.instance().getStack(0).isEmpty() )
		{
			((Button)getChildren().get(0)).setGraphic(createNewGameImage());
		}
		else
		{
			((Button)getChildren().get(0)).setGraphic(new ImageView(CardImages.getBack()));
//			((Button)getChildren().get(0)).setGraphic(new ImageView(new Image(CardImages.class.getResourceAsStream("../resources/777.jpg"))));
		}
		
	}
}