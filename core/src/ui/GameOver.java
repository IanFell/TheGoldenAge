package ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import cutscenes.CutScene;
import helpers.GamePlayHelper;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class GameOver extends CutScene {

	/**
	 * Constructor.
	 * 
	 * @param String name
	 */
	public GameOver(String name) {
		super(name);
	}

	public static boolean triggerGameOver = false;

	private int timer = 0;

	private final int DISPLAY_TIME = 300;

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param MyGame      myGame
	 */
	public void renderGameOverScreen(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		if (triggerGameOver) {
			renderBackgroundImage(myGame.renderer.batch, myGame, myGame.imageLoader.gameOver);
		}
	}	

	public void updateGameOverScreen() {
		if (triggerGameOver) {
			timer++;
			if (timer > DISPLAY_TIME) {
				GamePlayHelper.gameOver = true;
			}
		}
	}
}
