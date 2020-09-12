package cutscenes;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.mygame.MyGame;

import helpers.GameAttributeHelper;
import screens.GameScreen;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class CutSceneCauldron extends CutScene {

	/**
	 * Constructor.
	 * 
	 * @param String name
	 */
	public CutSceneCauldron(String name) {
		super(name);
		startXPosition          = GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 62; 
		startYPosition          = 30 - height; 
		anyCutSceneIsInProgress = true;
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	private void createCoverRows(MyGame myGame) {
		startXPosition          = GameScreen.camera.position.x; 
		startYPosition          = GameScreen.camera.position.y - height; 

		float coverRowYPosition       = startYPosition + 0.7f;
		coveringRow[COVER_ROW_ONE]    = new Rectangle(startXPosition, coverRowYPosition, width, coverRowHeight);
		coveringRow[COVER_ROW_TWO]    = new Rectangle(startXPosition, coverRowYPosition + coverRowHeight, width, coverRowHeight);
		coveringRow[COVER_ROW_THREE]  = new Rectangle(startXPosition, coverRowYPosition + coverRowHeight * 1.8f, width, coverRowHeight);
		coveringRow[COVER_ROW_FOUR]   = new Rectangle(startXPosition, coverRowYPosition + coverRowHeight * 2.6f, width, coverRowHeight);
		coveringRow[COVER_ROW_FIVE]   = new Rectangle(startXPosition, coverRowYPosition + coverRowHeight * 3.4f, width, coverRowHeight);
		coveringRow[COVER_ROW_SIX]    = new Rectangle(startXPosition, coverRowYPosition + coverRowHeight * 4.2f, width, coverRowHeight);
		coveringRow[COVER_ROW_SEVEN]  = new Rectangle(startXPosition, coverRowYPosition + coverRowHeight * 5.0f, width, coverRowHeight);
		coveringRow[COVER_ROW_EIGHT]  = new Rectangle(startXPosition, coverRowYPosition + coverRowHeight * 5.8f, width, coverRowHeight);
		coveringRow[COVER_ROW_NINE]   = new Rectangle(startXPosition, coverRowYPosition + coverRowHeight * 6.6f, width, coverRowHeight);
		coveringRow[COVER_ROW_TEN]    = new Rectangle(startXPosition, coverRowYPosition + coverRowHeight * 7.4f, width, coverRowHeight);
		coveringRow[COVER_ROW_ELEVEN] = new Rectangle(startXPosition, coverRowYPosition + coverRowHeight * 8.2f, width, coverRowHeight);
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 */
	public void renderCutScene(MyGame myGame) {
		if (!cutSceneConcluded) {
			renderBackgroundImage(myGame.renderer.batch, myGame, myGame.imageLoader.cutSceneBackGroundImageCauldron);
			renderBorder(myGame.renderer.batch, myGame.imageLoader, myGame);
			myGame.renderer.batch.draw(
					myGame.imageLoader.cauldronMessage,
					GameScreen.camera.position.x, 
					GameScreen.camera.position.y,
					width + 0.2f,
					-height
					);

			for (int i = 0; i < coveringRow.length; i++) {
				if (coveringRow[i] != null) {
					myGame.renderer.batch.draw(
							myGame.imageLoader.blackSquare, 
							coveringRow[i].getX(), 
							coveringRow[i].getY(), 
							coveringRow[i].getWidth(), 
							-coveringRow[i].getHeight()
							);
				}
			}
			//debugRow(myGame.renderer.batch, myGame.imageLoader, COVER_ROW_SIX);
		}
		if (transition != null) {
			transition.renderTransition(myGame.renderer.batch, myGame.imageLoader);
		}
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	@Override
	public void updateCutScene(MyGame myGame) {
		super.updateCutScene(myGame);

		handleCutSceneTransition(myGame);

		// Cannot set cover rows in the constructor due to changes needed in position, so set it here.
		if (!textBoxIsSet) {
			createCoverRows(myGame);
			textBoxIsSet = true;
		}

		gameShouldPause = true;
		updateCoveringRows();
	}

	/**
	 * This method takes care of shrinking the cover rows to make the text appear to render one letter at a time.
	 */
	private void updateCoveringRows() {
		float shrinkValue = 0.2f;
		if (coveringRow[COVER_ROW_ONE].getWidth() > 0) {
			coveringRow[COVER_ROW_ONE].setX(coveringRow[COVER_ROW_ONE].getX() + shrinkValue);
			coveringRow[COVER_ROW_ONE].setWidth(coveringRow[COVER_ROW_ONE].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_TWO].getWidth() > 0) {
			coveringRow[COVER_ROW_TWO].setX(coveringRow[COVER_ROW_TWO].getX() + shrinkValue);
			coveringRow[COVER_ROW_TWO].setWidth(coveringRow[COVER_ROW_TWO].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_FOUR].getWidth() > 0) {
			coveringRow[COVER_ROW_FOUR].setX(coveringRow[COVER_ROW_FOUR].getX() + shrinkValue);
			coveringRow[COVER_ROW_FOUR].setWidth(coveringRow[COVER_ROW_FOUR].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_FIVE].getWidth() > 0) {
			coveringRow[COVER_ROW_FIVE].setX(coveringRow[COVER_ROW_FIVE].getX() + shrinkValue);
			coveringRow[COVER_ROW_FIVE].setWidth(coveringRow[COVER_ROW_FIVE].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_SEVEN].getWidth() > 0) {
			coveringRow[COVER_ROW_SEVEN].setX(coveringRow[COVER_ROW_SEVEN].getX() + shrinkValue);
			coveringRow[COVER_ROW_SEVEN].setWidth(coveringRow[COVER_ROW_SEVEN].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_EIGHT].getWidth() > 0) {
			coveringRow[COVER_ROW_EIGHT].setX(coveringRow[COVER_ROW_EIGHT].getX() + shrinkValue);
			coveringRow[COVER_ROW_EIGHT].setWidth(coveringRow[COVER_ROW_EIGHT].getWidth() - shrinkValue);
		}  else {
			endCutScene();
		}
	}
}
