package handlers.collectibles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.collectibles.Rum;
import helpers.GameAttributeHelper;
import helpers.RandomNumberGenerator;
import loaders.ImageLoader;
import maps.MapHandler;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class RumHandler extends CollectibleHandler {

	public static int rumCount;

	private final int MAX_AMOUNT_RUM_ALLOWED = 50;

	private ArrayList <Rum> rum = new ArrayList<Rum>();

	public void init() {
		for (int i = 0; i < MAX_AMOUNT_RUM_ALLOWED; i++) {
			addRandomlyPlacedRum();
		}
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public void updateRum(MyGame myGame, MapHandler mapHandler) {
		for (int i = 0; i < rum.size(); i++) {
			rum.get(i).updateObject(myGame, mapHandler);
			if (rum.get(i).getY() + rum.get(i).getHeight() < 0) {
				rum.remove(i);
			}
			if (rum.size() < MAX_AMOUNT_RUM_ALLOWED) {
				addRandomlyPlacedRum();
			}
		}
	}

	private void addRandomlyPlacedRum() {
		int startX = RandomNumberGenerator.generateRandomInteger(GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 60);
		int startY = RandomNumberGenerator.generateRandomInteger(GameAttributeHelper.CHUNK_EIGHT_Y_POSITION_START + 50);
		rum.add(new Rum(
				startX, 
				startY
				));
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderRum(SpriteBatch batch, ImageLoader imageLoader) {
		for (int i = 0; i < rum.size(); i++) {
			if (gameObjectIsWithinScreenBounds(rum.get(i))) {
				rum.get(i).renderObject(batch, imageLoader);
			}
		}
	}
}
