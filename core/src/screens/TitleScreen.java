package screens;

import com.mygdx.mygame.MyGame;

import handlers.InputHandler;
import helpers.GameAttributeHelper;
import helpers.ImageHelper;

/**
 * Title screen.
 * 
 * @author Fabulous Fellini
 *
 */
public class TitleScreen extends Screens {

	private float transparentChangeSpeed = 0.05f;

	private float alphaPressStart           = 0;
	private boolean alphaIsRisingPressStart = true;

	private float alphaControls           = 0.3f;
	private boolean alphaIsRisingControls = true;

	private float alphaCredits           = 0.6f;
	private boolean alphaIsRisingCredits = true;

	public static int titleScreenHover  = 0;
	public static final int PRESS_START = 0;
	public static final int CONTROLS    = 1;
	public static final int CREDITS     = 2;

	public static int titleScreenOptionsMax = 3;

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

		if (InputHandler.inputType == InputHandler.INPUT_CONTROLLER) {
			if (alphaPressStart < 1) {
				myGame.renderer.batch.setColor(1.0f, 1.0f, 1.0f, alphaPressStart);
			}
			myGame.renderer.batch.draw(
					myGame.imageLoader.playGame,
					GameScreen.camera.position.x - 460,
					GameScreen.camera.position.y + 180,
					920,
					240
					);
			myGame.renderer.batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

			if (alphaControls < 1) {
				myGame.renderer.batch.setColor(1.0f, 1.0f, 1.0f, alphaControls);
			}
			myGame.renderer.batch.draw(
					myGame.imageLoader.controls,
					GameScreen.camera.position.x - 450,
					GameScreen.camera.position.y,
					900,
					200
					);
			myGame.renderer.batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

			if (alphaCredits < 1) {
				myGame.renderer.batch.setColor(1.0f, 1.0f, 1.0f, alphaCredits);
			}
			myGame.renderer.batch.draw(
					myGame.imageLoader.credits,
					GameScreen.camera.position.x - 450,
					GameScreen.camera.position.y - 200,
					900,
					200
					);
			myGame.renderer.batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

			float yPos = 0;
			switch(titleScreenHover) {
			case PRESS_START:
				yPos = GameScreen.camera.position.y + 200;
				break;
			case CONTROLS:
				yPos = GameScreen.camera.position.y;
				break;
			case CREDITS:
				yPos = GameScreen.camera.position.y - 200;
				break;
			}
			myGame.renderer.batch.draw(
					myGame.imageLoader.transparentSquare,
					GameScreen.camera.position.x - 450,
					yPos,
					900,
					200
					);
		}
		else if (InputHandler.inputType == InputHandler.INPUT_ARCADE) {
			if (alphaPressStart < 1) {
				myGame.renderer.batch.setColor(1.0f, 1.0f, 1.0f, alphaPressStart);
			}
			myGame.renderer.batch.draw(
					myGame.imageLoader.pressStart,
					GameScreen.camera.position.x - 450,
					GameScreen.camera.position.y + 200,
					900,
					200
					);
			myGame.renderer.batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		}
		myGame.renderer.batch.end();

		handleAlphaPressStart();
		handleAlphaControls();
		handleAlphaCredits();

		// Skip the cutscene.
		//GameStateController.switchGameStates(myGame, Screens.GAME_SCREEN);
	}

	private void handleAlphaCredits() {
		if (alphaIsRisingCredits) {
			if (alphaCredits < 1) {
				alphaCredits += transparentChangeSpeed;
			} else {
				alphaIsRisingCredits = false;
			}
		} else {
			if (alphaCredits > 0) {
				alphaCredits -= transparentChangeSpeed;
			} else {
				alphaIsRisingCredits = true;
			}
		}
	}

	private void handleAlphaControls() {
		if (alphaIsRisingControls) {
			if (alphaControls < 1) {
				alphaControls += transparentChangeSpeed;
			} else {
				alphaIsRisingControls = false;
			}
		} else {
			if (alphaControls > 0) {
				alphaControls -= transparentChangeSpeed;
			} else {
				alphaIsRisingControls = true;
			}
		}
	}

	private void handleAlphaPressStart() {
		if (alphaIsRisingPressStart) {
			if (alphaPressStart < 1) {
				alphaPressStart += transparentChangeSpeed;
			} else {
				alphaIsRisingPressStart = false;
			}
		} else {
			if (alphaPressStart > 0) {
				alphaPressStart -= transparentChangeSpeed;
			} else {
				alphaIsRisingPressStart = true;
			}
		}
	}
}
