package loaders.marketloader;

import com.mygdx.mygame.MyGame;

import helpers.GameAttributeHelper;
import loaders.BuildingLoader;
import loaders.GameObjectLoader;
import store.Market;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class MarketLoader extends BuildingLoader {

	public static Market market;

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void loadMarket(MyGame myGame) {
		market = new Market(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 33, 
				16,
				BUILDING_WIDTH * 2,
				BUILDING_HEIGHT / 2,
				myGame.imageLoader.market
				);
		GameObjectLoader.gameObjectList.add(market);
	}
}
