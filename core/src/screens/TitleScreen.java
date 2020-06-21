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

	private float alpha           = 0;
	private boolean alphaIsRising = true;

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
		if (alpha < 1) {
			myGame.renderer.batch.setColor(1.0f, 1.0f, 1.0f, alpha);
		}
		myGame.renderer.batch.draw(
				myGame.imageLoader.pressStart,
				GameScreen.camera.position.x - 450,
				GameScreen.camera.position.y + 200,
				900,
				200
				);
		myGame.renderer.batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		myGame.renderer.batch.end();

		handleAlpha();

		// Skip the cutscene.
		//GameStateController.switchGameStates(myGame, Screens.GAME_SCREEN);
	}

	/**
	 * Handles flashing of the "press start" image.
	 */
	private void handleAlpha() {
		if (alphaIsRising) {
			if (alpha < 1) {
				alpha += 0.05f;
			} else {
				alphaIsRising = false;
			}
		} else {
			if (alpha > 0) {
				alpha -= 0.05f;
			} else {
				alphaIsRising = true;
			}
		}
	}
}
