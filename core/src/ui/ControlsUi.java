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
public class ControlsUi extends Screens {

	public static boolean controlsShouldBeRendered;

	/**
	 * Constructor.
	 * 
	 * @param MyGame myGame
	 */
	public ControlsUi(final MyGame myGame) {
		super(myGame);
		controlsShouldBeRendered = false;
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 * @param MyGame        myGame
	 */
	public void renderControlsUi(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		if (controlsShouldBeRendered) {
			/*
			batch.draw(
					imageLoader.blackSquare,
					camera.position.x - getViewportWidth() / denominatorOffset,
					(camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight,
					camera.viewportWidth, 
					-camera.viewportHeight
					);
			//renderScrolls(batch, imageLoader, myGame);
			batch.draw(
					imageLoader.controlsUiBackground,
					camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset,
					(camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight,
					camera.viewportWidth - borderShrinkOffset * 2, 
					-camera.viewportHeight
					);
					*/
			batch.draw(
					imageLoader.controlsUi,
					camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset,
					(camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight,
					camera.viewportWidth - borderShrinkOffset * 2, 
					-camera.viewportHeight
					);
			batch.draw(
					imageLoader.controlsUiBackground,
					camera.position.x - getViewportWidth() / denominatorOffset + borderShrinkOffset,
					(camera.position.y - verticalHeight / denominatorOffset) + camera.viewportHeight + 12.5f,
					camera.viewportWidth - borderShrinkOffset * 2, 
					-camera.viewportHeight
					);
		}
	}
}
