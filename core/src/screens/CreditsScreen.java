package screens;

import com.mygdx.mygame.MyGame;

import helpers.GameAttributeHelper;
import helpers.ImageHelper;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class CreditsScreen extends Screens {

	/**
	 * Constructor.
	 * 
	 * @param MyGame myGame
	 */
	public CreditsScreen(final MyGame myGame) {
		super(myGame);
	}

	/**
	 * 
	 * @param float delta
	 */
	@Override
	public void render(float delta) {
		clearScreenAndSetScreenColor(GameAttributeHelper.gameState, null);
		updateCamera();
		myGame.renderer.batch.begin();
		ImageHelper.drawCreditsScreen(
				myGame.renderer.batch, 
				myGame.imageLoader.creditsScreenUi, 
				myGame
				);
		myGame.renderer.batch.end();
	}
}
