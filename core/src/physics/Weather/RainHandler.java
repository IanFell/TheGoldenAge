package physics.Weather;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import helpers.RandomNumberGenerator;
import loaders.ImageLoader;
import maps.MapHandler;
import missions.MissionStumpHole;
import screens.GameScreen;

/**
 * Handles in game rain.
 * 
 * @author Fabulous Fellini
 *
 */
public class RainHandler extends GameObject {

	public static boolean isRaining = false;

	/**
	 * Minimum falling velocity.
	 */
	private float minDy = 0.1f;

	/**
	 * Maximum falling velocity.
	 */
	private float maxDy = 0.3f;

	/**
	 * Constructor.
	 * 
	 * @param GameScreen gameScreen
	 */
	public RainHandler(GameScreen gameScreen, GameObject player) {
		this.x      = RandomNumberGenerator.generateRandomInteger((int)gameScreen.getViewportWidth());
		this.y      = (float) RandomNumberGenerator.generateRandomDouble(player.getY() - 7, player.getY() - 10);
		float size  = 0.05f;
		this.width  = size;
		this.height = size * 5;
		this.dy     = (float) RandomNumberGenerator.generateRandomDouble(minDy, maxDy);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param GameScreen  gameScreen
	 */
	public void renderObject(
			SpriteBatch batch, 
			ImageLoader imageLoader, 
			GameScreen gameScreen
			) {
		if (isRaining && !MissionStumpHole.missionIsActive) {
			batch.setProjectionMatrix(GameScreen.camera.combined);
			batch.draw(imageLoader.rain, x, y, width, height);
		}
	}

	/**
	 * 
	 * @param GameScreen gameScreen
	 * @param MapEditor  mapEditor
	 */
	public void updateObject(GameScreen gameScreen, MapHandler mapHandler, MyGame myGame) {
		if (isRaining) {
			x += .05;
			y += dy;
			int rainYBoundary = 7;
			if (y > myGame.getGameObject(GameObject.PLAYER_ONE).getY() + rainYBoundary) {
				// Only make it rain around player.
				int rainXBoundary      = 15;
				float middleOfBoundary = myGame.getGameObject(GameObject.PLAYER_ONE).getX();
				x  = (float) RandomNumberGenerator.generateRandomDouble(
						middleOfBoundary - rainXBoundary, 
						middleOfBoundary + rainXBoundary
						);
				y  = myGame.getGameObject(GameObject.PLAYER_ONE).getY() - rainYBoundary;
				dy = (float) RandomNumberGenerator.generateRandomDouble(minDy, maxDy);
			}
			//System.out.println("It is raining!");
		}
	}
}
