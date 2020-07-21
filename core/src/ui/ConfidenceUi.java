package ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import loaders.ImageLoader;
import screens.Screens;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class ConfidenceUi extends Screens {

	public static boolean confidenceUiShouldBeRendered = false;

	private int displayTimer            = 0;
	private final int DISPLAY_MAX_VALUE = 25;

	/**
	 * Constructor.
	 * 
	 * @param MyGame myGame
	 */
	public ConfidenceUi(final MyGame myGame) {
		super(myGame);
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 * @param MyGame        myGame
	 */
	public void renderConfidenceUi(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		if (confidenceUiShouldBeRendered) {
			batch.draw(
					imageLoader.cutsceneRum,
					camera.position.x - getViewportWidth() / denominatorOffset,
					(camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight,
					camera.viewportWidth, 
					-camera.viewportHeight
					);
		}
	}

	public void updateConfidenceUi() {
		if (confidenceUiShouldBeRendered) {
			displayTimer++;
			if (displayTimer > DISPLAY_MAX_VALUE) {
				displayTimer                 = 0;
				confidenceUiShouldBeRendered = false;
			}
		}
	}
}
