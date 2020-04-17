package physics.Lighting;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.stationarygameobjects.buildings.shadows.LightHouseShadow;
import gameobjects.stationarygameobjects.buildings.shadows.RawBarShadow;
import gameobjects.stationarygameobjects.buildings.shadows.TradingPostShadow;
import helpers.GameAttributeHelper;
import loaders.BuildingLoader;
import loaders.ImageLoader;
import loaders.lighthouseloader.LightHouseLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class StructureShadowHandler {

	private LightHouseShadow lightHouseShadow;
	private TradingPostShadow tradingPostShadow;
	private RawBarShadow rawBarShadow;

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
		tradingPostShadow = new TradingPostShadow(
				GameAttributeHelper.CHUNK_THREE_X_POSITION_START - 48.5f, 
				GameAttributeHelper.CHUNK_SIX_Y_POSITION_START - 65.5f, 
				BuildingLoader.BUILDING_WIDTH - 0.6f,
				BuildingLoader.BUILDING_HEIGHT, 
				imageLoader.tradingPostShadow
				);
		rawBarShadow = new RawBarShadow(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 34.6f, 
				GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 37.5f, 
				BuildingLoader.BUILDING_WIDTH,
				BuildingLoader.BUILDING_HEIGHT, 
				imageLoader.rawBarShadow
				);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderStructureShadows(SpriteBatch batch, ImageLoader imageLoader) {
		lightHouseShadow.renderObject(batch, imageLoader);
		tradingPostShadow.renderObject(batch, imageLoader);
		rawBarShadow.renderObject(batch, imageLoader);
	}
}
