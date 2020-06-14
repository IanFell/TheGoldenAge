package cutscenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class CutScene {

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

	protected int startXPosition;
	protected int startYPosition;

	protected int width;
	protected int height;

	// Change this value to render cutscene intro or not.
	private boolean cutSceneShouldRender;

	protected String name;
	protected boolean cutSceneConcluded;
	protected boolean selectedCutSceneIsInProgress;
	protected int timer;

	public static boolean anyCutSceneIsInProgress = false;

	/**
	 * Constructor.
	 * 
	 * @param String name
	 */
	public CutScene(String name) {
		this.name = name;
		timer     = 0;
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
	 * @return int
	 */
	public int getStartXPosition() {
		return startXPosition;
	}

	/**
	 * 
	 * @return int
	 */
	public int getStartYPosition() {
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

	public void updateCutScene() {
		timer++;
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 */
	public void renderCutScene(SpriteBatch batch, ImageLoader imageLoader) {}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isSelectedCutSceneInProgress() {
		return selectedCutSceneIsInProgress;
	}

	protected void endCutScene() {
		cutSceneConcluded            = true;
		selectedCutSceneIsInProgress = false;
		anyCutSceneIsInProgress      = false;
	}
}
