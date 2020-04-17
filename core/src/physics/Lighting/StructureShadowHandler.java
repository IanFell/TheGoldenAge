package physics.Lighting;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.stationarygameobjects.buildings.shadows.LightHouseShadow;
import helpers.GameAttributeHelper;
import loaders.ImageLoader;
import loaders.lighthouseloader.LightHouseLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class StructureShadowHandler {

	private LightHouseShadow lightHouseShadow;

	/**
	 * Constructor.
	 * 
	 * @param ImageLoader imageLoader
	 */
	public StructureShadowHandler(ImageLoader imageLoader) {
		lightHouseShadow = new LightHouseShadow(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 37.5f, 
				GameAttributeHelper.CHUNK_FIVE_Y_POSITION_START + 9.8f, 
				LightHouseLoader.LIGHT_HOUSE_WIDTH + 2.0f, 
				LightHouseLoader.LIGHT_HOUSE_HEIGHT + 4.0f, 
				imageLoader.lightHouseShadow
				);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderStructureShadows(SpriteBatch batch, ImageLoader imageLoader) {
		lightHouseShadow.renderObject(batch, imageLoader);
	}
}
