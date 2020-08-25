package cutscenes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.mygame.MyGame;

import gameobjects.gamecharacters.players.Player;
import loaders.ImageLoader;
import screens.GameScreen;
import transitions.Transition;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class CutScene {

	protected Transition transition;

	protected boolean initializeTransition = true;

	protected Rectangle[] coveringRow = new Rectangle[11];

	protected final int COVER_ROW_ONE    = 0;
	protected final int COVER_ROW_TWO    = 1;
	protected final int COVER_ROW_THREE  = 2;
	protected final int COVER_ROW_FOUR   = 3;
	protected final int COVER_ROW_FIVE   = 4;
	protected final int COVER_ROW_SIX    = 5;
	protected final int COVER_ROW_SEVEN  = 6;
	protected final int COVER_ROW_EIGHT  = 7;
	protected final int COVER_ROW_NINE   = 8;
	protected final int COVER_ROW_TEN    = 9;
	protected final int COVER_ROW_ELEVEN = 10;

	protected float coverRowHeight = 0.7f;

	protected float startXPosition;
	protected float startYPosition;

	protected int width;
	protected int height;

	// Change this value to render cutscene intro or not.
	private boolean cutSceneShouldRender;

	protected String name;
	protected boolean cutSceneConcluded;
	protected boolean selectedCutSceneIsInProgress;

	public static boolean anyCutSceneIsInProgress = false;

	// Pause game during cutscene.
	public static boolean gameShouldPause = true;

	public static boolean shouldPlayIntroJingle = false;
	private boolean hasPlayedIntroJingle        = false;

	protected boolean textBoxIsSet = false;

	/**
	 * Constructor.
	 * 
	 * @param String name
	 */
	public CutScene(String name) {
		this.name    = name;
		textBoxIsSet = false;
		/**
		 * This has to be false to just start the game with no intro.
		 * It must be true if you start the game with intro.
		 */
		anyCutSceneIsInProgress = true;
		// Change this to render cutscene or not.
		cutSceneShouldRender = true;
		if (cutSceneShouldRender) {
			setCutSceneValues(false, true);
		} else {
			setCutSceneValues(true, false);
		}
		width                   = 10;
		height                  = 7;
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	protected void handleCutSceneTransition(MyGame myGame) {
		if (initializeTransition) {
			transition           = new Transition(myGame);
			initializeTransition = false;
			transition.setX(GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset() + 20);
			transition.setY((GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - 14);
		}
		if (transition != null) {
			transition.updateTransition();
		}

		if (!hasPlayedIntroJingle) {
			shouldPlayIntroJingle = true;
			hasPlayedIntroJingle  = true;
		}
	}

	/**
	 * Use this method to see where the rows line up in regards to the image. 
	 * It will render the specified row in white, offset to it's original x position.
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param int         row
	 */
	protected void debugRow(SpriteBatch batch, ImageLoader imageLoader, int row) {
		batch.draw(
				imageLoader.whiteSquare, 
				coveringRow[row].getX() - 1, 
				coveringRow[row].getY(), 
				coveringRow[row].getWidth(), 
				-coveringRow[row].getHeight()
				);
	}

	/**
	 * 
	 * @return float
	 */
	public float getStartXPosition() {
		return startXPosition;
	}

	/**
	 * 
	 * @return float
	 */
	public float getStartYPosition() {
		return startYPosition;
	}

	/**
	 * 
	 * @param boolean isConcluded
	 * @param boolean isInProgress
	 */
	private void setCutSceneValues(boolean isConcluded, boolean isInProgress) {
		cutSceneConcluded            = isConcluded;
		selectedCutSceneIsInProgress = isInProgress;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isCutSceneConcluded() {
		return cutSceneConcluded;
	}

	public void updateCutScene(MyGame myGame) {
		if (initializeTransition) {
			transition           = new Transition(myGame);
			initializeTransition = false;
			transition.setX(GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset() - 7);
			transition.setY((GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - 15);
			Player.isJumping      = false;
			Player.playerIsMoving = false;
		}
		transition.updateTransition();
	}

	/**
	 * Renders background image full screen.
	 * 
	 * @param SpriteBatch batch
	 * @param MyGame      myGame
	 * @param Texture     texture
	 */
	protected void renderBackgroundImage(SpriteBatch batch, MyGame myGame, Texture texture) {
		batch.draw(
				texture,
				GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset(),
				(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight,
				GameScreen.camera.viewportWidth - myGame.getGameScreen().getBorderShrinkOffset() + myGame.getGameScreen().getBorderShrinkOffset(), 
				-GameScreen.camera.viewportHeight
				);
	}

	/**
	 * Renders black border around cutscene background image.
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param MyGame      myGame
	 */
	protected void renderBorder(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		batch.draw(
				imageLoader.border,
				GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset(),
				(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight,
				GameScreen.camera.viewportWidth - myGame.getGameScreen().getBorderShrinkOffset() + + myGame.getGameScreen().getBorderShrinkOffset(), 
				-GameScreen.camera.viewportHeight
				);
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 * @param MyGame        myGame
	 */
	public void renderCutScene(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isSelectedCutSceneInProgress() {
		return selectedCutSceneIsInProgress;
	}

	/**
	 * 
	 * @param boolean selectedCutSceneIsInProgress
	 */
	public void setSelectedCutSceneIsInProgress(boolean selectedCutSceneIsInProgress) {
		this.selectedCutSceneIsInProgress = selectedCutSceneIsInProgress;
	}

	protected void endCutScene() {
		cutSceneConcluded            = true;
		selectedCutSceneIsInProgress = false;
		anyCutSceneIsInProgress      = false;
		gameShouldPause              = false;
	}
}
