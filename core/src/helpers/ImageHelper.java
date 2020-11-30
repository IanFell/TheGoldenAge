package helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import handlers.InputHandler;
import loaders.ImageLoader;
import screens.GameScreen;
import screens.Screens;
import ui.TextBasedUiParent;

/**
 * Contains helper methods for images.
 * 
 * @author Fabulous Fellini
 *
 */
public class ImageHelper {

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param Texture     texture
	 * @param int         width
	 * @param int         height
	 */
	public static void drawImageInMiddleOfScreen(SpriteBatch batch, Texture texture, int width, int height, MyGame myGame) {
		batch.draw(
				texture, 
				GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset() - 10,
				(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight,
				GameScreen.camera.viewportWidth - myGame.getGameScreen().getBorderShrinkOffset() + myGame.getGameScreen().getBorderShrinkOffset(), 
				-GameScreen.camera.viewportHeight
				);
	}
	
	/**
	 * 
	 * @param SpriteBatch batch
	 * @param Texture     texture
	 * @param int         width
	 * @param int         height
	 */
	public static void drawSplashScreen(SpriteBatch batch, Texture texture, int width, int height, MyGame myGame) {
		batch.draw(
				texture, 
				GameScreen.camera.position.x - GameScreen.camera.viewportWidth / 2,
				GameScreen.camera.position.y - GameScreen.camera.viewportHeight / 2,
				GameScreen.camera.viewportWidth - myGame.getGameScreen().getBorderShrinkOffset() + myGame.getGameScreen().getBorderShrinkOffset(), 
				GameScreen.camera.viewportHeight
				);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param Texture     texture
	 * @param MyGame      myGame
	 */
	public static void drawTitleScreen(SpriteBatch batch, Texture texture, MyGame myGame) {
		batch.draw(
				texture, 
				0,
				0,
				GameScreen.camera.viewportWidth - myGame.getGameScreen().getBorderShrinkOffset() + myGame.getGameScreen().getBorderShrinkOffset(), 
				GameScreen.camera.viewportHeight
				);
	}
	
	/**
	 * 
	 * @param SpriteBatch batch
	 * @param Texture     texture
	 * @param MyGame      myGame
	 */
	public static void drawTitleScreenOverlay(SpriteBatch batch, Texture texture, MyGame myGame) {
		float wOffset = GameScreen.camera.viewportWidth / 2;
		float hOffset = GameScreen.camera.viewportHeight / 2;
		batch.draw(
				texture, 
				0,
				0,
				GameScreen.camera.viewportWidth - myGame.getGameScreen().getBorderShrinkOffset() + myGame.getGameScreen().getBorderShrinkOffset() - wOffset, 
				GameScreen.camera.viewportHeight - hOffset
				);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param Texture     texture
	 * @param MyGame      myGame
	 */
	public static void drawControlsScreen(SpriteBatch batch, Texture texture, MyGame myGame) {
		batch.draw(
				myGame.imageLoader.titleScreen, 
				0,
				0,
				GameScreen.camera.viewportWidth - myGame.getGameScreen().getBorderShrinkOffset() + myGame.getGameScreen().getBorderShrinkOffset(), 
				GameScreen.camera.viewportHeight
				);
		float width  = GameScreen.camera.viewportWidth;
		float height = GameScreen.camera.viewportHeight;
		batch.draw(texture, GameScreen.camera.position.x - width / 2, GameScreen.camera.position.y - height / 2, width, height);
		
		if (GameAttributeHelper.playerHasStartedGame && InputHandler.inputType == InputHandler.INPUT_COMPUTER) {
			batch.draw(texture, GameScreen.camera.position.x - width / 2, GameScreen.camera.position.y + height / 2, width, -height);
		}
	}
	
	/**
	 * 
	 * @param SpriteBatch batch
	 * @param Texture     texture
	 * @param MyGame      myGame
	 */
	public static void drawCreditsScreen(SpriteBatch batch, Texture texture, MyGame myGame) {
		float width  = GameScreen.camera.viewportWidth;
		float height = GameScreen.camera.viewportHeight;
		batch.draw(texture, GameScreen.camera.position.x - width / 2, GameScreen.camera.position.y - height / 2, width, height);	
	}

	/**
	 * Gets the current value of the digit and sets the corresponding texture.
	 * 
	 * @param int         number
	 * @param ImageLoader imageLoader
	 * @param int         digit
	 * @return texture
	 */
	public static Texture getConvertedTextureForDigit(int number, ImageLoader imageLoader, int digit) {
		Texture texture   = imageLoader.numberBlack[0];
		int digitToRender = 0;
		// If number is bigger than 10, split the digits up so we can handle them seperately.
		if (number > 9) {
			if (digit == TextBasedUiParent.DIGIT_ONE) {
				digitToRender = number / 10;
			} else {
				digitToRender = number % 10;
			}

		} else {
			digitToRender = number;
		}
		texture = setTextureNumber(digitToRender, imageLoader);
		return texture;
	}

	/**
	 * Returns the texture of the actual number of the digit.
	 * For instance, if the number is 15, either 1 or 5 will be returned.
	 * 
	 * Change color of digits if player has store open, since both the default digits and store are black.
	 * 
	 * @param int         digit
	 * @param ImageLoader imageLoader
	 * @return texture
	 */
	private static Texture setTextureNumber(int digit, ImageLoader imageLoader) {
		Texture texture = imageLoader.numberBlack[0];
		for (int i = 0; i < 10; i++) {
			if (digit == i) {
				texture = imageLoader.numberBlack[digit];
			}
		}
		return texture;
	}
}
