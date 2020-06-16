package cutscenes;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.mygame.MyGame;

import helpers.GameAttributeHelper;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class CutSceneFarzenplank extends CutScene {

	/**
	 * 
	 * @param String name
	 */
	public CutSceneFarzenplank(String name) {
		super(name);
		startXPosition          = GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 40; 
		startYPosition          = GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 45 - height; 
		anyCutSceneIsInProgress = true;

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
			myGame.renderer.batch.draw(
					myGame.imageLoader.cutsceneFarzenplank,
					GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 40, 
					GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 45,
					width,
					-height
					);

			for (int i = 0; i < coveringRow.length; i++) {
				myGame.renderer.batch.draw(
						myGame.imageLoader.blackSquare, 
						coveringRow[i].getX(), 
						coveringRow[i].getY(), 
						coveringRow[i].getWidth(), 
						-coveringRow[i].getHeight()
						);
			}
			//debugRow(myGame.renderer.batch, myGame.imageLoader, COVER_ROW_SIX);
		}
	}

	@Override
	public void updateCutScene() {
		super.updateCutScene();
		gameShouldPause = true;
		updateCoveringRows();
	}

	/**
	 * This method takes care of shrinking the cover rows to make the text appear to render one letter at a time.
	 */
	private void updateCoveringRows() {
		float shrinkValue = 0.1f;
		if (coveringRow[COVER_ROW_ONE].getWidth() > 0) {
			coveringRow[COVER_ROW_ONE].setX(coveringRow[COVER_ROW_ONE].getX() + shrinkValue);
			coveringRow[COVER_ROW_ONE].setWidth(coveringRow[COVER_ROW_ONE].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_TWO].getWidth() > 0) {
			coveringRow[COVER_ROW_TWO].setX(coveringRow[COVER_ROW_TWO].getX() + shrinkValue);
			coveringRow[COVER_ROW_TWO].setWidth(coveringRow[COVER_ROW_TWO].getWidth() - shrinkValue);
			// Skip row 3 because it's a blank line.
		} else if (coveringRow[COVER_ROW_FOUR].getWidth() > 0) {
			coveringRow[COVER_ROW_FOUR].setX(coveringRow[COVER_ROW_FOUR].getX() + shrinkValue);
			coveringRow[COVER_ROW_FOUR].setWidth(coveringRow[COVER_ROW_FOUR].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_FIVE].getWidth() > 0) {
			coveringRow[COVER_ROW_FIVE].setX(coveringRow[COVER_ROW_FIVE].getX() + shrinkValue);
			coveringRow[COVER_ROW_FIVE].setWidth(coveringRow[COVER_ROW_FIVE].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_SIX].getWidth() > 0) {
			coveringRow[COVER_ROW_SIX].setX(coveringRow[COVER_ROW_SIX].getX() + shrinkValue);
			coveringRow[COVER_ROW_SIX].setWidth(coveringRow[COVER_ROW_SIX].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_EIGHT].getWidth() > 0) {
			coveringRow[COVER_ROW_EIGHT].setX(coveringRow[COVER_ROW_EIGHT].getX() + shrinkValue);
			coveringRow[COVER_ROW_EIGHT].setWidth(coveringRow[COVER_ROW_EIGHT].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_NINE].getWidth() > 0) {
			coveringRow[COVER_ROW_NINE].setX(coveringRow[COVER_ROW_NINE].getX() + shrinkValue);
			coveringRow[COVER_ROW_NINE].setWidth(coveringRow[COVER_ROW_NINE].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_TEN].getWidth() > 0) {
			coveringRow[COVER_ROW_TEN].setX(coveringRow[COVER_ROW_TEN].getX() + shrinkValue);
			coveringRow[COVER_ROW_TEN].setWidth(coveringRow[COVER_ROW_TEN].getWidth() - shrinkValue); 
		} else {
			endCutScene();
		}
	}
}
