package physics.Lighting;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.stationarygameobjects.buildings.shadows.BarShadow;
import gameobjects.stationarygameobjects.buildings.shadows.LightHouseShadow;
import gameobjects.stationarygameobjects.buildings.shadows.PigglyWigglyShadow;
import gameobjects.stationarygameobjects.buildings.shadows.RawBarShadow;
import gameobjects.stationarygameobjects.buildings.shadows.ScallopCoveShadow;
import gameobjects.stationarygameobjects.buildings.shadows.TradingPostShadow;
import helpers.GameAttributeHelper;
import helpers.GamePlayHelper;
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
	private BarShadow barShadow;
	private PigglyWigglyShadow pigglyWigglyShadow;
	private ScallopCoveShadow scallopCoveShadow;

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
		barShadow = new BarShadow(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START + 43.5f, 
				GameAttributeHelper.CHUNK_THREE_Y_POSITION_START + 9.5f, 
				BuildingLoader.BUILDING_WIDTH,
				BuildingLoader.BUILDING_HEIGHT, 
				imageLoader.barShadow
				);
		pigglyWigglyShadow = new PigglyWigglyShadow(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START + 0.5f, 
				GameAttributeHelper.CHUNK_THREE_Y_POSITION_START - 6.0f, 
				BuildingLoader.BUILDING_WIDTH - 1.0f,
				BuildingLoader.BUILDING_HEIGHT + 0.5f, 
				imageLoader.pigglyWigglyShadow
				);
		scallopCoveShadow = new ScallopCoveShadow(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 9.5f, 
				GameAttributeHelper.CHUNK_FIVE_Y_POSITION_START - 90.5f, 
				BuildingLoader.BUILDING_WIDTH,
				BuildingLoader.BUILDING_HEIGHT, 
				imageLoader.tradingPostShadow
				);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderStructureShadows(SpriteBatch batch, ImageLoader imageLoader) {
		if (GamePlayHelper.gameObjectIsWithinScreenBounds(lightHouseShadow)) {
			lightHouseShadow.renderObject(batch, imageLoader);
		}
		if (GamePlayHelper.gameObjectIsWithinScreenBounds(tradingPostShadow)) {
			tradingPostShadow.renderObject(batch, imageLoader);
		}
		if (GamePlayHelper.gameObjectIsWithinScreenBounds(rawBarShadow)) {
			rawBarShadow.renderObject(batch, imageLoader);
		}
		if (GamePlayHelper.gameObjectIsWithinScreenBounds(barShadow)) {
			barShadow.renderObject(batch, imageLoader);
		}
		if (GamePlayHelper.gameObjectIsWithinScreenBounds(pigglyWigglyShadow)) {
			pigglyWigglyShadow.renderObject(batch, imageLoader);
		}
	}
}
