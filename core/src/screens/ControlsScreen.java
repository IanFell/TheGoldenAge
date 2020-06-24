package screens;

import com.mygdx.mygame.MyGame;

import helpers.GameAttributeHelper;
import helpers.ImageHelper;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class ControlsScreen extends Screens {

	/**
	 * Constructor.
	 * 
	 * @param MyGame myGame
	 */
	public ControlsScreen(final MyGame myGame) {
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
		ImageHelper.drawControlsScreen(
				myGame.renderer.batch, 
				myGame.imageLoader.controlsUi, 
				myGame
				);
		myGame.renderer.batch.end();
	}
}
