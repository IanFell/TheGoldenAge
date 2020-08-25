package loaders.hangingloader;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.gamecharacters.Hanging;
import helpers.GameAttributeHelper;
import loaders.GameObjectLoader;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class HangingLoader {

	private Hanging[] hanging = new Hanging[3];

	public void loadHangingLoader() {
		// Mexico Beach.
		hanging[0] = new Hanging(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 43,
				GameAttributeHelper.CHUNK_ONE_Y_POSITION_START + 23
				);
		// St. George Island.
		hanging[1] = new Hanging(
				GameAttributeHelper.CHUNK_SEVEN_X_POSITION_START + 15,
				GameAttributeHelper.CHUNK_EIGHT_Y_POSITION_START + 7
				);
		// Wewa.
		hanging[2] = new Hanging(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 45,
				10
				);
		GameObjectLoader.gameObjectList.add(hanging[0]);
		GameObjectLoader.gameObjectList.add(hanging[1]);
		GameObjectLoader.gameObjectList.add(hanging[2]);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderHanging(SpriteBatch batch, ImageLoader imageLoader) {
		for (int i = 0; i < hanging.length; i++) {
			hanging[i].renderObject(batch, imageLoader);
		}
	}
}
