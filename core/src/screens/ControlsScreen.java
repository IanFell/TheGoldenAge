package screens;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.mygame.MyGame;

import handlers.InputHandler;
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
		Texture texture = myGame.imageLoader.controlsUi;
		if (InputHandler.inputType == InputHandler.INPUT_COMPUTER) {
			texture = myGame.imageLoader.computerControls;
		}
		ImageHelper.drawControlsScreen(
				myGame.renderer.batch, 
				texture, 
				myGame
				);
		myGame.renderer.batch.end();
	}
}
