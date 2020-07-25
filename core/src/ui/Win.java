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
public class Win extends CutScene {

	public static boolean triggerWin = false;

	private int timer = 0;

	private final int DISPLAY_TIME = 500;

	/**
	 * Constructor.
	 * 
	 * @param String name
	 */
	public Win(String name) {
		super(name);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param MyGame      myGame
	 */
	public void renderWinningScreen(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		if (triggerWin) {
			renderBackgroundImage(myGame.renderer.batch, myGame, myGame.imageLoader.win);
		}
	}	

	public void updateWinningScreen() {
		if (triggerWin) {
			timer++;
			if (timer > DISPLAY_TIME) {
				// This just acts as a restart of the game.
				GamePlayHelper.gameOver = true;
			}
		}
	}
}
