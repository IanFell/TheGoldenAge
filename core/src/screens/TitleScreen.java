package screens;

import com.mygdx.mygame.MyGame;

import helpers.GameAttributeHelper;
import helpers.ImageHelper;

/**
 * Title screen.
 * 
 * @author Fabulous Fellini
 *
 */
public class TitleScreen extends Screens {

	/**
	 * 
	 * @param MyGame myGame
	 */
	public TitleScreen(final MyGame myGame) {
		super(myGame);
		GameAttributeHelper.gameState = Screens.TITLE_SCREEN;
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
		ImageHelper.drawTitleScreen(
				myGame.renderer.batch, 
				myGame.imageLoader.titleScreen, 
				myGame
				);
		myGame.renderer.batch.end();

		// Skip the cutscene.
		//GameStateController.switchGameStates(myGame, Screens.GAME_SCREEN);
	}
}
