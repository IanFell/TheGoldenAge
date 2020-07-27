package loaders.flyingbirdloader;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.gamecharacters.FlyingBird;
import helpers.GameAttributeHelper;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class FlyingBirdLoader {
	
	private static FlyingBird[] flyingBird = new FlyingBird[8];
	
	/**
	 * Constructor.
	 */
	public FlyingBirdLoader() {
		flyingBird[0] = new FlyingBird(GameAttributeHelper.CHUNK_ONE_X_POSITION_START, 0);
		flyingBird[1] = new FlyingBird(GameAttributeHelper.CHUNK_TWO_X_POSITION_START, 0);
		flyingBird[2] = new FlyingBird(GameAttributeHelper.CHUNK_THREE_X_POSITION_START, 0);
		flyingBird[3] = new FlyingBird(GameAttributeHelper.CHUNK_FOUR_X_POSITION_START, 0);
		flyingBird[4] = new FlyingBird(GameAttributeHelper.CHUNK_FIVE_X_POSITION_START, 0);
		flyingBird[5] = new FlyingBird(GameAttributeHelper.CHUNK_SIX_X_POSITION_START, 0);
		flyingBird[6] = new FlyingBird(GameAttributeHelper.CHUNK_SEVEN_X_POSITION_START, 0);
		flyingBird[7] = new FlyingBird(GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START, 0);
	}
	
	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public static void renderFlyingBirds(SpriteBatch batch, ImageLoader imageLoader) {
		for (int i = 0; i < flyingBird.length; i++) {
			flyingBird[i].renderObject(batch, imageLoader);
		}
	}
	
	public void updateFlyingBirds() {
		for (int i = 0; i < flyingBird.length; i++) {
			flyingBird[i].updateBird();
		}
	}
}
