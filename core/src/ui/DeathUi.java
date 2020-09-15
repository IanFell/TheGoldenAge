package ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.gamecharacters.players.PlayerOne;
import loaders.ImageLoader;
import screens.Screens;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class DeathUi extends Screens {

	public static boolean deathUiShouldBeRendered = false;

	private int displayTimer            = 0;
	private final int DISPLAY_MAX_VALUE = 30;

	public static void resetGame() {
		deathUiShouldBeRendered = false;
	}

	/**
	 * Constructor.
	 * 
	 * @param MyGame myGame
	 */
	public DeathUi(final MyGame myGame) {
		super(myGame);
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 * @param MyGame        myGame
	 */
	public void renderDeathUi(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		if (deathUiShouldBeRendered) {
			Texture texture = null;
			if (PlayerOne.lives == 1) {
				texture = imageLoader.livesLeftTwo;
			} else if (PlayerOne.lives == 2) {
				texture = imageLoader.livesLeftOne;
			}
			batch.draw(
					texture,
					camera.position.x - getViewportWidth() / denominatorOffset,
					(camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight,
					camera.viewportWidth, 
					-camera.viewportHeight
					);
		}
	}

	public void updateDeathUi() {
		if (deathUiShouldBeRendered) {
			displayTimer++;
			if (displayTimer > DISPLAY_MAX_VALUE) {
				displayTimer            = 0;
				deathUiShouldBeRendered = false;
			}
		}
	}
}
