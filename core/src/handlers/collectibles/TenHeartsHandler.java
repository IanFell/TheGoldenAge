package handlers.collectibles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.collectibles.TenHearts;
import helpers.GameAttributeHelper;
import helpers.RandomNumberGenerator;
import loaders.GameObjectLoader;
import loaders.ImageLoader;
import maps.MapHandler;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class TenHeartsHandler extends CollectibleHandler {

	public static final int MAX_AMOUNT_TEN_HEARTS_ALLOWED = 10;

	public static ArrayList <TenHearts> tenHearts = new ArrayList<TenHearts>();

	public void init() {
		for (int i = 0; i < MAX_AMOUNT_TEN_HEARTS_ALLOWED; i++) {
			addRandomlyPlacedTenHeart();
		}
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public void updateTenHearts(MyGame myGame, MapHandler mapHandler) {
		for (int i = 0; i < tenHearts.size(); i++) {
			tenHearts.get(i).updateObject(myGame, mapHandler);
			if (tenHearts.get(i).getY() + tenHearts.get(i).getHeight() < 0) {
				tenHearts.remove(i);
			}
			if (tenHearts.size() < MAX_AMOUNT_TEN_HEARTS_ALLOWED) {
				addRandomlyPlacedTenHeart();
			}
		}
	}

	private void addRandomlyPlacedTenHeart() {
		int startX = RandomNumberGenerator.generateRandomInteger(GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 60);
		int startY = RandomNumberGenerator.generateRandomInteger(GameAttributeHelper.CHUNK_EIGHT_Y_POSITION_START + 50);
		//tenHearts.add(new TenHearts(
		//		startX, 
		//		startY
		//		));
		TenHearts th = new TenHearts(startX, startY);
		tenHearts.add(th);
		GameObjectLoader.gameObjectList.add(th);
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 */
	public void renderHearts(SpriteBatch batch, ImageLoader imageLoader) {
		for (int i = 0; i < tenHearts.size(); i++) {
			if (gameObjectIsWithinScreenBounds(tenHearts.get(i))) {
				tenHearts.get(i).renderObject(batch, imageLoader);
			}
		}
	}
}
