package cutscenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.mygame.MyGame;

import debugging.Debugger;
import loaders.ImageLoader;

/**
 * This is the intro cutscene, when Jolly Roger explains the point of the game.
 * 
 * @author Fabulous Fellini
 *
 */
public class CutSceneJollyRoger extends CutScene {

	private float boatSpeed;
	private float boatStartXPosition;
	private int boatSize;

	/**
	 * Constructor.
	 * 
	 * @param String name
	 */
	public CutSceneJollyRoger(String name) {
		super(name);
		startXPosition          = 90;
		startYPosition          = 10;
		boatSpeed               = 0.0075f;
		boatStartXPosition      = startXPosition - 2;
		boatSize                = 3;
		anyCutSceneIsInProgress = true;

		float coverRowYPosition       = startYPosition - 2.3f;
		float coverRowHeight          = 0.7f;
		coveringRow[COVER_ROW_ONE]    = new Rectangle(startXPosition, coverRowYPosition, width, coverRowHeight);
		coveringRow[COVER_ROW_TWO]    = new Rectangle(startXPosition, coverRowYPosition + coverRowHeight, width, coverRowHeight);
		coveringRow[COVER_ROW_THREE]  = new Rectangle(startXPosition, coverRowYPosition + coverRowHeight * 2, width, coverRowHeight);
		coveringRow[COVER_ROW_FOUR]   = new Rectangle(startXPosition, coverRowYPosition + coverRowHeight * 2.6f, width, coverRowHeight);
		coveringRow[COVER_ROW_FIVE]   = new Rectangle(startXPosition, coverRowYPosition + coverRowHeight * 3.4f, width, coverRowHeight);
		coveringRow[COVER_ROW_SIX]    = new Rectangle(startXPosition, coverRowYPosition + coverRowHeight * 4.2f, width, coverRowHeight);
		coveringRow[COVER_ROW_SEVEN]  = new Rectangle(startXPosition, coverRowYPosition + coverRowHeight * 5f, width, coverRowHeight);
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
	@Override
	public void renderCutScene(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		batch.draw(
				imageLoader.boatSide,
				boatStartXPosition, 
				startYPosition - 2.5f,
				boatSize,
				-boatSize
				);
		batch.draw(
				imageLoader.playerRight,
				boatStartXPosition + 0.4f, 
				startYPosition - 3.7f,
				1,
				-1
				);
		if (!cutSceneConcluded) {
			batch.draw(
					imageLoader.cutsceneJollyRoger,
					startXPosition, 
					startYPosition + 4,
					width + 0.2f,
					-height
					);
			renderBorder(batch, imageLoader, myGame);
		}
		for (int i = 0; i < coveringRow.length; i++) {
			batch.draw(
					imageLoader.blackSquare, 
					coveringRow[i].getX(), 
					coveringRow[i].getY(), 
					coveringRow[i].getWidth(), 
					-coveringRow[i].getHeight()
					);
		}
		//debugRow(batch, imageLoader, COVER_ROW_ONE);

		if (transition != null) {
			transition.renderTransition(batch, imageLoader);
		}
	}

	/**
	 * This method takes care of shrinking the cover rows to make the text appear to render one letter at a time.
	 */
	private void updateCoveringRows() {
		float shrinkValue = 0.15f;
		if (coveringRow[COVER_ROW_ONE].getWidth() > 0) {
			coveringRow[COVER_ROW_ONE].setX(coveringRow[COVER_ROW_ONE].getX() + shrinkValue);
			coveringRow[COVER_ROW_ONE].setWidth(coveringRow[COVER_ROW_ONE].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_TWO].getWidth() > 0) {
			coveringRow[COVER_ROW_TWO].setX(coveringRow[COVER_ROW_TWO].getX() + shrinkValue);
			coveringRow[COVER_ROW_TWO].setWidth(coveringRow[COVER_ROW_TWO].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_THREE].getWidth() > 0) {
			coveringRow[COVER_ROW_THREE].setX(coveringRow[COVER_ROW_THREE].getX() + shrinkValue);
			coveringRow[COVER_ROW_THREE].setWidth(coveringRow[COVER_ROW_THREE].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_FOUR].getWidth() > 0) {
			coveringRow[COVER_ROW_FOUR].setX(coveringRow[COVER_ROW_FOUR].getX() + shrinkValue);
			coveringRow[COVER_ROW_FOUR].setWidth(coveringRow[COVER_ROW_FOUR].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_FIVE].getWidth() > 0) {
			coveringRow[COVER_ROW_FIVE].setX(coveringRow[COVER_ROW_FIVE].getX() + shrinkValue);
			coveringRow[COVER_ROW_FIVE].setWidth(coveringRow[COVER_ROW_FIVE].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_SIX].getWidth() > 0) {
			coveringRow[COVER_ROW_SIX].setX(coveringRow[COVER_ROW_SIX].getX() + shrinkValue);
			coveringRow[COVER_ROW_SIX].setWidth(coveringRow[COVER_ROW_SIX].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_SEVEN].getWidth() > 0) {
			coveringRow[COVER_ROW_SEVEN].setX(coveringRow[COVER_ROW_SEVEN].getX() + shrinkValue);
			coveringRow[COVER_ROW_SEVEN].setWidth(coveringRow[COVER_ROW_SEVEN].getWidth() - shrinkValue);
		} else if (coveringRow[COVER_ROW_EIGHT].getWidth() > 0) {
			coveringRow[COVER_ROW_EIGHT].setX(coveringRow[COVER_ROW_EIGHT].getX() + shrinkValue);
			coveringRow[COVER_ROW_EIGHT].setWidth(coveringRow[COVER_ROW_EIGHT].getWidth() - shrinkValue);
		} else {
			endCutScene();
		}
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	@Override
	public void updateCutScene(MyGame myGame) {
		super.updateCutScene(myGame);
		boatStartXPosition += boatSpeed;
		gameShouldPause = true;
		updateCoveringRows();

		// TODO Maybe change this to actual controls for player to use.
		if (Debugger.skipIntroCutscene) {
			endCutScene();
		}

		handleCutSceneTransition(myGame);
	}
}
