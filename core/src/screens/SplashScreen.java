package screens;

import com.mygdx.mygame.MyGame;

import controllers.GameStateController;
import helpers.GameAttributeHelper;
import helpers.ImageHelper;

/**
 * Fabulous Fellini logo screen.
 * 
 * @author Fabulous Fellini
 *
 */
public class SplashScreen extends Screens {

	private int displayTimer;

	private int SPLASH_SCREEN_DISPLAY_VALUE = 50;

	/**
	 * 
	 * @param MyGame myGame
	 */
	public SplashScreen(final MyGame myGame) {
		super(myGame);
		GameAttributeHelper.gameState = Screens.SPLASH_SCREEN;
		displayTimer = 0;
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
		ImageHelper.drawSplashScreen(
				myGame.renderer.batch, 
				myGame.imageLoader.splashScreenLogo, 
				GameAttributeHelper.SCREEN_WIDTH, 
				GameAttributeHelper.SCREEN_HEIGHT,
				myGame
				);
		displayTimer++;
		if (displayTimer > SPLASH_SCREEN_DISPLAY_VALUE) {
			GameStateController.switchGameStates(myGame, Screens.TITLE_SCREEN);
		}
		myGame.renderer.batch.end();
	}
}
